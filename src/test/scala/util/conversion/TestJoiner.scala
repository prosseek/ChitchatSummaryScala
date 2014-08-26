package util.conversion

/**
 * Created by smcho on 8/17/14.
 */

import bloomierFilter.ByteArrayBloomierFilter
import grapevineType._
import org.scalatest._
import BottomType._

class TestJoiner extends FunSuite with BeforeAndAfter {
  var t: Joiner = _
  var s: Splitter = _

  before {
    s = new Splitter
    t = new Joiner
  }

  test ("getInterpretableString") {
    val str = "Hello"
    val ba = ByteArrayTool.stringToByteArray(str, str.size + 10)
    assert(t.getInterpretableString(ba.slice(1, ba.size)) == str)
    //assert(t.getInterpretableString(ba) == str)
  }

  test ("isInterpretableString") {
    val str = "Hello"
    val ba10 = ByteArrayTool.stringToByteArray(str, str.size + 10)
    val ba = ByteArrayTool.stringToByteArray(str, str.size + 1)
    assert(t.isInterpretableString(ba10.slice(1, ba10.size)) == false)
    assert(t.isInterpretableString(ba.slice(1, ba.size)))
  }

  test ("joinString") {
    val sampleString = "Hello, world!" // This will return "Hello, world4
    val map = s.split("message", StringType(sampleString), 2)
    val bfs = new ByteArrayBloomierFilter(map, initialM = 6, k = 3, q = 2*8)

    val resultString = t.joinString(bfs, "message").get
    assert(ByteArrayTool.byteArrayToString(resultString) == sampleString)
    assert(t.joinString(bfs, "hello").isEmpty)
  }

  test ("joinFromBloomierFilter latitude") {
    def test(width: Int, input: (Int, Int, Int, Int)) = {
      // table width 1
      println(s"${width}:")
      var map = s.split("latitude", LatitudeType(input), width)
      var bfs = new ByteArrayBloomierFilter(map, initialM = 6, k = 3, q = width * 8)
      var res = t.joinFromBloomierFilter(bfs, "latitude", LatitudeType.getSize).get
      var lat = new LatitudeType
      assert(lat.fromByteArray(res) == NoError)
      assert(lat.value == input)
    }
    val input = (10, 10, 10, 10)
    test(1, input)
    test(2, input)
    test(3, input)
    test(4, input)
    test(5, input)
  }
}