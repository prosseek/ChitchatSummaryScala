package grapevineType

import org.scalatest._
import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/13/14.
 */
class TestAgeType extends FunSuite {
  test("Simple test") {
    val t = new AgeType
    t.set(10); assert(t.get == 10)

    // check exception
    intercept[RuntimeException] {
      t.set(200)
    }
  }

  test("to/from bytearray") {
    val t = new AgeType
    val b = ByteArrayTool.byteToByteArray(55, 1)
    t.fromByteArray(b)
    assert(t.get == 55)

    val c = t.toByteArray(1)
    assert(b.deep == c.deep)
  }

}