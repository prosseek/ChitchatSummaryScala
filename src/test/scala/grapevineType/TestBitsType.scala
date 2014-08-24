package grapevineType

import grapevineType.BottomType.BottomType
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.collection.BitSet

class TestClass extends BitsType {
  override def set(value: Any): Unit = {}
  override def getId(): Int = 0
  override def fromByteArray(b: Array[Byte]): BottomType = null
  override def toByteArray(goalSize: Int): Array[Byte] = null
  override def getTypeName(): String = "???"
}

/**
 * Created by smcho on 8/15/14.
 */
class TestBitsType extends FunSuite with BeforeAndAfter {
  var t:TestClass = _
  before {
    t = new TestClass
  }
  test("getValue test") {
    assert(t.getValue(10, 8, signed = true) == 10)
    assert(t.getValue(-10, 8, signed = true) == -10)
    assert(t.getValue(10, 8, signed = false) == 10)
    assert(t.getValue(-10, 8, signed = false) == 246) // 10 + 246 = 256
  }
  test("getValues test") {
    assert(t.getValues(List(-10, -1, -1), List(8,8,4), signed = false) == List(246, 255, 15))
  }
  test("shiftAndJoin") {
    // test the case when the value is negative.
    val values = List(-97,44,11, 5)
    val bits = List(9,6,6,7)
    assert(t.shiftAndJoin(values, bits) == BitSet(0, 2, 7, 8, 10, 15, 16, 18, 19, 20, 21, 22, 23, 26, 27))
  }
  // Debuging for [2014/08/23]
  test("debug check - splitBitSets") {
    // with 1 bit range, this test should return empty bitset
    val bs = BitSet(1,3)
    val bits:List[Int] = List(1)
    assert(t.splitBitSets(bs, bits) == List(BitSet(), BitSet(0,2)))
  }
}
