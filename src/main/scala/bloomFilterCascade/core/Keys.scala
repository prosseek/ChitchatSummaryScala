package core

import scala.collection.mutable.{StringBuilder, Map => MMap, Set => MSet}

/**
  Keys is a data structure to store the keys that causes false positives

  bit : 0 or 1
  level : 0 is the A, 1 is B, and so on
 */

object Keys {
  def apply() = {
    new Keys()
  }
}

class Keys extends OneZero[MSet[String]]  {
  // Make ready for the level 0
  oneBuffer(0) = MSet[String]()
  zeroBuffer(0) = MSet[String]()

  /************************************
    * Internal methods
    ************************************/

  /************************************
    * APIs
    ************************************/
  /**
   * Prints out the inner information
   */
  override
  def debug(print:Boolean = false) : String = {
    def printContents(buffer:MMap[Int, MSet[String]], sb:StringBuilder, comment:String) = {
      sb.append(comment + " -------------\n")
      buffer.keys.toList.sorted.foreach { level =>
        sb.append(s"${level} : ${buffer(level)}\n")
      }
      sb.append("--------------------\n")
    }

    val sb = new StringBuilder()
    // 1. print out the level
    check()
    var r = getMaxLevel()
    sb.append(s"MaxLevel (0 - ${r})\n")

    printContents(oneBuffer, sb, "1 keys")
    printContents(zeroBuffer, sb, "0 keys")

    if (print)
      println(sb.toString)
    sb.toString
  }

  def add(bit:Int, level:Int, key:String) : Unit = {
    def addToTable(buf:MMap[Int, MSet[String]]) = {
      if (!buf.contains(level))
        buf(level) = MSet[String]()

      // When we don't want to add keys, but just make a set
      // We do: add(B, L, "")
      // this is the code to store key only when it has contents
      if (key.size > 0)
        buf(level) += key
    }
    if (bit == 0) addToTable(zeroBuffer) else addToTable(oneBuffer)
  }

  def add(bit:Int, level:Int, keys:Seq[String]) : Unit = {
    keys.foreach {key =>
      add(bit, level, key)
    }
  }

  def get(bit:Int, level:Int) :Set[String] = {
    if (bit == 0) zeroBuffer(level).toSet else oneBuffer(level).toSet
  }

  def makeReady(level:Int) = {
    oneBuffer(level) = MSet[String]()
    zeroBuffer(level) = MSet[String]()
  }

  /**
   * the tuple (of two array of keys that survive the filtering of m
   * @param debug
   * @return (3:4:5, 5:2:3)
   */
  def getSize(debug:Boolean = false) = {
    def getFalsePositiveSize(buffer:MMap[Int, MSet[String]]) = {
      buffer.keys.toList.sorted.map(buffer(_).size)
    }

    if (debug) {
      println(s"One:${getFalsePositiveSize(oneBuffer)}")
      println(s"Zero:${getFalsePositiveSize(zeroBuffer)}")
    }

    (getFalsePositiveSize(oneBuffer), getFalsePositiveSize(zeroBuffer))
  }
}
