package bloomierFilter

/**
 * Created by smcho on 8/16/14.
 */

import org.scalatest.{BeforeAndAfter, FunSuite}
import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/15/14.
 */
class TestByteArrayBloomierFilter extends FunSuite with BeforeAndAfter  {
  var map1 : Map[String, Array[Byte]] = _
  var map2 : Map[String, Array[Byte]] = _
  before {
    map1 = Map("a" -> ByteArrayTool.floatToByteArray(10.0F, size = 4),
               "b" -> ByteArrayTool.byteToByteArray(10,  size = 4),
               "c" -> ByteArrayTool.byteToByteArray(20,  size = 4),
               "d" -> ByteArrayTool.byteToByteArray(30,  size = 4))
    map2 = Map("a" -> ByteArrayTool.floatToByteArray(10.0F, size = 4),
                 "b" -> ByteArrayTool.byteToByteArray(10,  size = 4),
                 "c" -> ByteArrayTool.byteToByteArray(20,  size = 4),
                 "d" -> ByteArrayTool.byteToByteArray(30,  size = 4),
                 "e" -> ByteArrayTool.floatToByteArray(10.0F, size = 4),
                 "f" -> ByteArrayTool.byteToByteArray(10,  size = 4),
                 "g" -> ByteArrayTool.byteToByteArray(20,  size = 4),
                 "h" -> ByteArrayTool.byteToByteArray(30,  size = 4))
  }
  test ("map1 find test - allow order = true") {
    val t = new ByteArrayBloomierFilter(map1, initialM = 4, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, allowOrder = true)
    val r = t.find()
    println(s"Found solution? ${r.isDefined}")
    println(s"Depth = ${t.getDepth()}")
    println(s"M = ${t.m}")
  }

  test ("map2 find test - allow order = true/false") {
    var t = new ByteArrayBloomierFilter(map2, initialM = 6, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, allowOrder = true)
    var r = t.find()
    println(s"Found solution? ${r.isDefined}")
    println(s"Depth = ${t.getDepth()}")
    println(s"M = ${t.m}")

    t = new ByteArrayBloomierFilter(map2, initialM = 6, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, allowOrder = false)
    r = t.find()
    println(s"Found solution? ${r.isDefined}")
    println(s"Depth = ${t.getDepth()}")
    println(s"M = ${t.m}")

  }
}
