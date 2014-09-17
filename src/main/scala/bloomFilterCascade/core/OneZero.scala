package core

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 7/4/14.
 */
abstract class OneZero[T] {
  protected val oneBuffer = MMap[Int, T]()
  protected val zeroBuffer = MMap[Int, T]()
  protected val table = Array[MMap[Int, T]](oneBuffer, zeroBuffer)

  /************************************
   * Internal methods
   ************************************/

  /**
   * @param buffer (zero/one)
   * @return the maximum number of the key (it is the maximum level)
   */
  private def _getMaxLevel[T](buffer:MMap[Int, T]) = buffer.keySet.toList.sorted.last

  /************************************
    * API
    ************************************/
  def debug(print:Boolean = true) : String

  /**
   * WARNING: This method checks if the zero/one buffer has the same maximum level
   * It does not execute the check or any other check
   * @return the maximum level from the key set
   */
  def getMaxLevel() :Int = {
    val oneSize = _getMaxLevel(oneBuffer)
    val zeroSize = _getMaxLevel(zeroBuffer)
    assert(oneSize == zeroSize)
    oneSize
  }

  /**
   * Checks the integrity of OneZero data structure
   */
  def check() = {
    // 1. oneBuffer and zeroBuffer should have sequential (not missing) in between
    //    numbers (0,1,2,4,5) -> X, (0,1,2,3,4,5) -> 0
    def sequentialCheck(keys:Set[Int]) = {
      val l = keys.toList.sorted
      var before = l.head

      // the first level (first of the key in table map) should always be 1
      assert(before == 0, "The start value should be 0")
      // for the rest of the keys, it should increase only by 1
      l.tail.foreach { after =>
        if (before + 1 == after) {
          before = after
        }
        else {
          assert(false, "The keys are not sequential")
        }
      }
    }
    sequentialCheck(table(0).keySet.toSet)
    sequentialCheck(table(1).keySet.toSet)

    // 2. oneBuffer and zeroBuffer should have the same level
    val maxLevel = getMaxLevel()
    assert(maxLevel >= 0)
  }

}
