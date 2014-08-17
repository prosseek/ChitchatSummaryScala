package util.conversion

import util.print.Util._

/**
 * Created by smcho on 8/17/14.
 */

import grapevineType._
import org.scalatest._

class TestSplitter extends FunSuite with BeforeAndAfter {
  var t:Splitter = _
  before {
    t = new Splitter
  }
  test ("Under test") {
    // under
    val ba = Array[Byte](100, 110)
    val s = t.split("key", ba, 4)
    assert(s("key").mkString(":") == "100:110:0:0")
  }
  test ("Over test") {
    // over1
    var ba = Array[Byte](100, 101, 102, 103)
    var s = t.split("key", ba, 2)
    assert(s("key0").mkString(":") == "100:101")
    assert(s("key1").mkString(":") == "102:103")
    assert(s.contains("key2") == false)

    ba = Array[Byte](100, 101, 102, 103, 104)
    s = t.split("key", ba, 2)
    assert(s("key0").mkString(":") == "100:101")
    assert(s("key1").mkString(":") == "102:103")
    assert(s("key2").mkString(":") == "104:0")
    assert(s.contains("key3") == false)

    s = t.split("key", ba, 3)
    assert(s("key0").mkString(":") == "100:101:102")
    assert(s("key1").mkString(":") == "103:104:0")
    assert(s.contains("key2") == false)
  }
  test("Split with string") {
    var input = new StringType
    input.set("Hello, world?")
    var s = t.split("key", input, 2)
    println(getString(s, true))
  }
  test("Split with lattitude") {
    var input = new LatitudeType
    input.set(20,10,10,0)
    var s = t.split("latitude", input, 2)
    println(getString(s))
  }
}
