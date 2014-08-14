package grapevineType

import org.scalatest.{BeforeAndAfter, FunSuite}
import util.conversion.ByteArrayTool
import BottomType._

/**
 * Created by smcho on 8/13/14.
 */
class TestSpeedType extends FunSuite with BeforeAndAfter {
  var t: SpeedType = _

  before {
    t = new SpeedType
  }

  test("Simple test") {
    t.set(10);
    assert(t.get == 10)

    // check exception
    intercept[RuntimeException] {
      t.set(200)
    }
  }

  test("to/from bytearray") {
    // 55 in 1 byte array
    val b = ByteArrayTool.byteToByteArray(55, size = 1)
    t.fromByteArray(b)
    assert(t.get == 55)

    val c = t.toByteArray(1)
    assert(b.deep == c.deep)
  }

  test("Bottom_c test") {
    var b = Array[Byte](10)
    t.fromByteArray(b)
    assert(t.get == 10)

    // upper bits contain 1
    b = Array[Byte](1, 1)
    assert(t.fromByteArray(b) == Computational)
  }

  test("Bottom_c test from check()") {
    // It's 1 byte data, but the range is over for this data type
    var b = Array[Byte](-85) // 85 + 170 = 255, so -85 is 170
    assert(t.fromByteArray(b) == Computational)

    b = Array[Byte](-106) // -105 -> 150
    assert(t.fromByteArray(b) == NoError)
  }
}