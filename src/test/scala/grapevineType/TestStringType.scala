package grapevineType

import org.scalatest.{BeforeAndAfter, FunSuite}
import util.conversion.ByteArrayTool
import BottomType._

/**
 * Created by smcho on 8/13/14.
 */
class TestStringType extends FunSuite with BeforeAndAfter {
  var t: StringType = _

  before {
    t = new StringType
  }

  test("Simple test") {
    t.set("hello world");
    assert(t.get == "hello world")
  }

  test("Printable") {
    assert(StringType.isPrintable('a'))
    assert(t.check("Hello, world"))

    assert(StringType.isPrintable(0x00) == false)
  }

  test("to/from bytearray") {
    t.set("Hello")
    //println(t.toByteArray().mkString(":"))
    assert(t.toByteArray().mkString(":") == "5:72:101:108:108:111")
    assert(t.toByteArray(10).mkString(":") == "5:72:101:108:108:111:0:0:0:0")

    val st = "Hello, World"
    val b = ByteArrayTool.stringToByteArray(st, (st.size + 1))
    t.fromByteArray(b)
    assert(t.get == st)
  }

  test("Bottom_c test") {
    // upper bits contain 1
    var b = Array[Byte](2, 33, 1)
    assert(t.fromByteArray(b) == Computational)
    b = Array[Byte](2, 33, 33)
    assert(t.fromByteArray(b) == NoError)
  }
}