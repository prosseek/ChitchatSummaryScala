package util.conversion

/**
 * Created by smcho on 8/17/14.
 */

import bloomierFilter.ByteArrayBloomierFilter
import grapevineType.StringType
import org.scalatest._

class TestJoiner extends FunSuite with BeforeAndAfter {
  var t: Joiner = _
  before {
    t = new Joiner
  }

  test ("getInterpretableString") {
    val str = "Hello"
    val ba = ByteArrayTool.stringToByteArray(str, str.size + 10)
    assert(t.getInterpretableString(ba) == str)
  }

  test ("isInterpretableString") {
    val str = "Hello"
    val ba10 = ByteArrayTool.stringToByteArray(str, str.size + 10)
    val ba = ByteArrayTool.stringToByteArray(str, str.size + 0)
    assert(t.isInterpretableString(ba10) == false)
    assert(t.isInterpretableString(ba))
  }

  test ("joinString") {
    val s = new Splitter
    val input = new StringType
    val sampleString = "Hello, world"
    input.set(sampleString)
    val map = s.split("message", input, 2)
    val bfs = new ByteArrayBloomierFilter(map, initialM = 6, k = 3, q = 2*8)

    assert(t.joinString(bfs, "message").get == sampleString)
  }
}