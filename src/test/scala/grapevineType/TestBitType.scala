package grapevineType

import org.scalatest.{BeforeAndAfter, FunSuite}
import util.conversion.ByteArrayTool
import BottomType._

/**
 * Created by smcho on 8/13/14.
 */
class TestBitType extends FunSuite with BeforeAndAfter {
  var t: BitType = _

  before {
    t = new BitType
  }

  test("Simple test") {
    t.set(1);
    assert(t.get == 1)
  }

  test("to/from bytearray") {
    // 55 in 1 byte array
    val b = ByteArrayTool.byteToByteArray(1, size = 1)
    t.fromByteArray(b)
    assert(t.get == 1)

    val c = t.toByteArray(1)
    assert(b.deep == c.deep)
  }

  test("Bottom_c test") {
    var b = Array[Byte](1)
    t.fromByteArray(b)
    assert(t.get == 1)

    b = Array[Byte](10)
    assert(t.fromByteArray(b) == Computational)

    // upper bits contain 1
    b = Array[Byte](1, 1)
    assert(t.fromByteArray(b) == Computational)
  }
}