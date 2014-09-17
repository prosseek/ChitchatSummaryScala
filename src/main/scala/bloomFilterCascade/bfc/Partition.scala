package bfc

//import scala.collection.immutable.Set
import scala.collection.BitSet
import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 7/1/14.
 */
case class Partition(dictionary:Map[String, BitSet]) {
  /**
   * Given dictionary and index
   * returns two groups of keys that has 1 in ith bit and not
   * @param i
   * @return
   */
  def partition(i:Int) = {
    // dictionary(_) -> BitSet, so dictionary(_)(i) returns the BitSet
    // In other words, the data is split if the bit at ith BitSet is true(0) or not(1)
    dictionary.keySet.partition(dictionary(_)(i) == true)
  }

  /**
   * From the dictionary values (bitSet), find the largest number
   * @return
   */
  def getMaxIndex() = {
    //println(dictionary.values)
    (0 /: dictionary.values) ((m, bs) => math.max(m, if (bs.size > 0) bs.last else 0))
  }

  /**
   * @return index -> tuple(true set, and false set)
   */
  def get() = {
    val m = MMap[Int, (Set[String], Set[String])]()
    for (i <- Range(0, getMaxIndex() + 1)) {
      m(i) = partition(i)
    }
    m
  }
}
