package bloomierFilter

import bloomierFilter.BloomierFilter
import org.scalatest.{BeforeAndAfter, FunSuite}
import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/15/14.
 */
class TestBloomierFilter extends FunSuite with BeforeAndAfter  {
  var t: BloomierFilter = _
  test ("Simple") {
    val map = Map("a" -> ByteArrayTool.floatToByteArray(10.0F, size = 4), "b"->ByteArrayTool.byteToByteArray(10,  size = 4))
    val k = 3
    val q = 8*4
    val m = 3
    t = new BloomierFilter(map = map, k = k, q = q, m = m)

    println(t)
  }
}
