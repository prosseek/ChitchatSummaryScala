package bfc

import core.{Keys, Tables}
import util.conversion.BitSetTool._
import util.conversion.ByteArrayTool

import scala.collection.BitSet
import scala.collection.mutable.{BitSet => MBitSet, Map => MMap}

object BloomFilterCascade {
  /**
   * CHeck if all the members in indexes are in the BitSet
   * Ex1) indexes = [1,2,3]
   * m = [1,2,3,4,5]
   * --> returns True
   * @param m
   * @param indexes
   */
  def isIncluded(m: BitSet, indexes: Seq[Int]) = {
    indexes.map(m.contains(_)).forall(_ == true)
  }

  def data2bitArray(dictionary:Map[String, Any], r:Int) : Map[String, BitSet]= {
    /**
     * !!!TODO
     * This only uses Byte, but it should be more expressive
     * @param m
     * @param key
     * @param value
     * @param r
     */
    def setMapWithBitSet(m:MMap[String, BitSet], key:String, value:Any, r:Int) = {


      if (r == 8)
        m(key) = byteToBitSet(value.asInstanceOf[Byte])
      else if (r == 16)
        m(key) = shortToBitSet(value.asInstanceOf[Short])
      else if (r == 32)
        m(key) = intToBitSet(value.asInstanceOf[Int])
      else {
        throw new RuntimeException("Only 8,16,32 allowed for r")
      }
    }

    val m = MMap[String, BitSet]()
    dictionary.foreach { case (key, value) => setMapWithBitSet(m, key, value, r)}
    m.toMap
  }
}

/**
 * Created by smcho on 7/1/14.
 */
class BloomFilterCascade() {
  val keys = MMap[Int, Keys]() // Keys()
  val tables = MMap[Int, Tables]() // Tables(m=m, keys=k)
  var bitSetDictionary: Map[String, BitSet] = _ //BloomFilterCascade.data2bitArray(dictionary, r)
  var r:Int = _

  //create()

  /****************************
    Internal Functions
    **************************/

  /****************************
    UTILITY FUNCTIONS
    **************************/
  def debug(print:Boolean) :Unit = {
    Range(0, r).foreach { index =>
      debug(index, print)
    }
  }

  def debug(index:Int, print:Boolean) :Unit = {
    def printm(m:Array[core.BitArray], message:String) = {
      println(message + ": ")
      for ((b,i) <- m zipWithIndex) {
        println(i + "> " )
      }
    }

    val sb = new StringBuilder()
    sb.append(s"------ index ${index} DEBUG PRINT START ------\n")
    sb.append("KEYS **************\n")
    sb.append(keys(index).debug(false))
    sb.append("TABLES **************\n")
    sb.append(tables(index).debug(false))
    sb.append(s"------ index ${index} DEBUG PRINT END ------\n")

    if (print) println(sb.toString())
    sb.toString()
  }

  /****************************
    APIs
    **************************/
  def arrayByteToBitSet(dictionary:Map[String, Array[Byte]]) = {
    val res = MMap[String, BitSet]()

    dictionary.foreach { case (k, v) =>
      res(k) = ByteArrayTool.byteArrayToBitSet(v)
    }

    res.toMap
  }

  def create(dictionary:Map[String, Array[Byte]], r:Int, m:List[Int], k:List[Int], seed:Int=0) = {
    bitSetDictionary = arrayByteToBitSet(dictionary)
    val p = Partition(bitSetDictionary)
    this.r = r

    // process per each bit
    Range(0, r).foreach {index =>
      tables(index) = Tables(m=m, keys=k, startSeed = seed)
      keys(index) = Keys()

      // 1. initial partition (level = 0)
      var level = 0
      var (key1, key0) = p.partition(index)

      //println(s"r index ${index} = key1 ${key1} - key0 ${key0}")

      keys(index).add(bit = 1, level = level, keys = key1.toSeq)
      keys(index).add(bit = 0, level = level, keys = key0.toSeq)
      tables(index).set(bit = 1, level = level, keys = key1.toSeq)
      tables(index).set(bit = 0, level = level, keys = key0.toSeq)

      var bit = 1
      var direction = 1

      while (keys(index).get(bit=1, level=level).size > 0 && keys(index).get(bit=0, level=level).size > 0) {
        // as there are keys in the previous level, go to the next level
        level += 1
        tables(index).makeReady(level)
        keys(index).makeReady(level)

        Range(0,2).foreach { bit =>
          direction = (bit + level) % 2
          //println(s"bit:($bit) direction:($direction) level($level) ")

          keys(index).get(bit = bit, level = level - 1).foreach { key =>
            if (tables(index).checkMembership(direction, level - 1, key)) {
              tables(index).set(bit = direction, level = level, key = key)
              keys(index).add(bit = bit, level = level, key = key)

              //println(s"Keys - ${keys(index).debug(true)} : Tables - ${tables(index).debug(true)}")
            }
          }
        }
      }
      //debug(index, true)
    }
  }

  def get(r:Int, key:String) : (Int, Int) = {
    def get(r:Int, level:Int, key:String) : (Int, Int) = {
      val a1 = tables(r).checkMembership(bit = 1, level = level, key = key)
      val a0 = tables(r).checkMembership(bit = 0, level = level, key = key)
      if (a1 == false && a0 == false) return (-1,-1)
      if (a1 == true && a0 == false) return (1,0)
      if (a1 == false && a0 == true) return (0,1)
      else {
        val maxLevel = tables(r).getMaxLevel()

        if (maxLevel == level) return (-2,-2)
        val res = get(r, level+1, key)
        if (res._1 == -1 || res._1 == -2) return res
        return (res._1 ^ 1, res._2 ^ 1)
      }
    }

    get(r, 0, key)
  }

  def get(key:String) : Option[BitSet] = {
    val bs = MBitSet()
    Range(0, r).foreach {index =>
      if (get(index, key)._1 == -1 || get(index, key)._1 == -2)
        return None
      if (get(index, key) == (1, 0))
        bs += index
    }
    Some(bs)
  }

  /**
   * Returns -1 when None (both a0,a1 is zero, or a0,a1 and b0,b1 is one)
   * @param key
   * @return
   */
  def getAsValue(key:String, r:Int) : Int = {
    val value = get(key)

    if (value.isEmpty) { // None
      return -1
    }

    if (r == 8)
      bitSetToByte(value.get.asInstanceOf[BitSet]).toInt
    else if (r == 16)
      bitSetToShort(value.get.asInstanceOf[BitSet]).toInt
    else if (r == 32)
      bitSetToInt(value.get.asInstanceOf[BitSet], bitWidth=r)
    else
      throw new RuntimeException("Only 8/16/32 supported")
    //value.asInstanceOf[Int]
  }

  def getSize(debug:Boolean = false) = {
    var result = 0
    Range(0, r).foreach {position =>
      if (debug) print(s"Bit ${position}: ")
      result += tables(position).getSize(debug)
      keys(position).getSize(debug)
    }
    result
  }
}
