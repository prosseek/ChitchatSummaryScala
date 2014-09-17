package core

import scala.collection.mutable.BitSet


/**
 * Created by smcho on 7/1/14.
 */
case class BitArray(val m:Int, seed: Int) {
  val bitSet = BitSet()
  //val hasher = Hasher(m, seed)

  def setUnique(bit:Boolean = true) = {
    Hasher.useUnique = bit
  }

  def checkRange(s:Int, m:Int, i:Int) = {
    if (i >= m || i < s) throw new java.lang.IndexOutOfBoundsException(s"i($i) should less than m($m)")
  }
  def get(i:Int) = {
    checkRange(0, m, i)
    bitSet(i)
  }
  def get() = bitSet

  def set(i:Int) : Unit = {
    checkRange(0, m, i)
    bitSet.add(i)
  }
  def set(key:String, k:Int) : Unit = {
    Hasher.get(key = key, m = m, k = k, seed = seed).foreach {set(_)}
    //hasher.get(key, k).foreach {set(_)}
  }
  def set(key:String, mp:Int, k:Int) : Unit = {
    Hasher.get(key = key, m = mp, k = k, seed = seed).foreach {set(_)}
    //val hasher = Hasher(m, seed)
    //hasher.get(key, k).foreach {set(_)}
  }

  def forall(p:(Int) => Boolean) = {
    bitSet.forall(p)
  }
  def debug() = {
    println(s"BitSet: ${bitSet}")
  }
}
