package grapevineType

import grapevineType.BottomType.BottomType
import org.scalatest.{BeforeAndAfter, FunSuite}

class TestClass extends BitsType {
  override def set(value: Any): Unit = {}
  override def getId(): Int = 0
  override def fromByteArray(b: Array[Byte]): BottomType = null
  override def toByteArray(goalSize: Int): Array[Byte] = null
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
}
