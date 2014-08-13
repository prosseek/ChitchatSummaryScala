package grapevineType

import org.scalatest._
import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/11/14.
 */
class TestCountType extends FunSuite {
  test("Simple test") {
    val t = new CountType
    t.set(111)
    assert(t.get == 111)

    // if the number is over 125, the value returned wil be negative
    // => 256 - 226 = 30
    t.set(226)
    assert(t.get == -30) // 226 + 30 = 256

    t.set(255)
    assert(t.get == -1) // 255 + 1 = 256

    assert(t.check(1000) == false)
    assert(t.check(255))
    assert(t.check(0))
    assert(t.check(-1) == false)
  }

  test("to/from byteArray") {
    val t = new CountType
    val b = ByteArrayTool.byteToByteArray(55, 1)
    t.fromByteArray(b)
    assert(t.get == 55)

    val c = t.toByteArray(1)
    assert(b.deep == c.deep)
  }
}
