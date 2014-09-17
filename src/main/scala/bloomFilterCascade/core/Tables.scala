package core

import scala.collection.BitSet
import scala.collection.mutable.{StringBuilder, Map => MMap}

object Tables {
  /**
   * check if points has all the positions in bits
   * @param bits
   * @param points
   * @return
   */
  def checkMembership(bits:BitSet, points:Seq[Int]) = points.forall(bits(_) == true)
}

/**
 * The Bloom Filter Cascade table
 * Waring: by default, all the bit information is stored in BitSet
 */
case class Tables(m:Seq[Int] = List(10,5,3), keys:Seq[Int] = List(3,3,2), startSeed:Int) extends OneZero[BitSet] {
  // m setup
  oneBuffer(0) = BitSet()
  zeroBuffer(0) = BitSet()

  /************************************
   * Internal methods
   ************************************/

  /**
   * Returns the size of m for the input level
   * When level is more than the maximum of Seq, it returns the last element
   *
   * @param level
   * @return m as the bit width of the array
   */
  private def _getElement(m:Seq[Int], level:Int) = {
    assert (level >= 0)
    val size = m.size
    if (level >= size) m.last
    else
      m(level)
  }

  private def _getHashPointers(level:Int, key:String) = {
    val m = getM(level) // the size of m
    val k = getK(level) // the number of hash functions
    Hasher.get(key=key, m=m, k=k, seed = level + startSeed)
  }

  /************************************
   * Debugging
   ************************************/
  override def debug(print:Boolean = false) : String = {
    def printContents(buffer:MMap[Int, BitSet], sb:StringBuilder, comment:String) = {
      sb.append(comment + " -------------\n")
      buffer.keys.toList.sorted.foreach { level =>
        sb.append(s"${level} : ${buffer(level)}\n")
      }
      sb.append("--------------------\n")
    }

    val sb = new StringBuilder()
    // 1. print out the level
    var r = getMaxLevel()
    sb.append(s"MaxLevel (0 - ${r})\n")
    sb.append(s"m sizes ${m}/k sizes${keys}\n")

    printContents(oneBuffer, sb, "1 keys")
    printContents(zeroBuffer, sb, "0 keys")

    if (print) println(sb.toString)
    sb.toString
  }

  /************************************
   * APIs
   ************************************/
  def getM(level:Int) = _getElement(m, level)

  def getK(level:Int) = _getElement(keys, level)

  def set(bit:Integer, level:Integer, key:String) : Unit = {
    val buf = if (bit == 1) oneBuffer else zeroBuffer
    val pointers = _getHashPointers(level, key)

    // when the level is more than that of max level,
    // the buffers should be filled with BitSet
    val maxLevel = getMaxLevel()
    if (level > maxLevel) {
      Range(maxLevel+1, level+1).foreach {i =>
        oneBuffer(i) = BitSet()
        zeroBuffer(i) = BitSet()
      }
    }
    buf(level) ++= pointers
  }

  def set(bit:Integer, level:Integer, keys:Seq[String]) : Unit = {
    keys.foreach { key =>
      set(bit, level, key)
    }
  }

  def checkMembership(bit:Int, level:Int, key:String) = {
    val keys = _getHashPointers(level, key)
    if (bit == 1) Tables.checkMembership(oneBuffer(level), keys)
    else Tables.checkMembership(zeroBuffer(level), keys)
  }

  def get(level:Int, key:String) = {
    val keys = _getHashPointers(level, key)
    (Tables.checkMembership(oneBuffer(level), keys), Tables.checkMembership(zeroBuffer(level), keys))
  }

  def makeReady(level:Int) = {
    oneBuffer(level) = BitSet()
    zeroBuffer(level) = BitSet()
  }

  /**
   * Returns the total size of bits
   *
   * m = List[Int] as the bit widths for all
   */
  def getSize(debug:Boolean = false) = {
    val res = 2*(0 /:  Range(0, getMaxLevel() + 1)) { (acc, level) =>
      acc + (if (level < m.size) m(level) else m.last)
    }
    if (debug) {
      val sb = new StringBuffer()
      val keys = oneBuffer.keySet.toList.sorted
      sb.append("ONE-ZERO\n")
      keys.foreach { level =>
        sb.append(s"[${level}]${getM(level)}(${oneBuffer(level).size}) ")
      }
      sb.append("\n")
      keys.foreach { level =>
        sb.append(s"[${level}]${getM(level)}(${zeroBuffer(level).size}) ")
      }
      println(s"size - ${res}(${oneBuffer.size}):${sb.toString}")
    }
    res
  }
}
