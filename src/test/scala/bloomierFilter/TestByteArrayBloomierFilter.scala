package bloomierFilter

/**
 * Created by smcho on 8/16/14.
 */

import org.scalatest.{BeforeAndAfter, FunSuite}
import util.conversion.ByteArrayTool
import util.print.Util._

/**
 * Created by smcho on 8/15/14.
 */
class TestByteArrayBloomierFilter extends FunSuite with BeforeAndAfter  {
  var map1 : Map[String, Array[Byte]] = _
  var map2 : Map[String, Array[Byte]] = _
  var map3 : Map[String, Array[Byte]] = _
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
                 "h" -> null)
    map3 = Map("a" -> ByteArrayTool.byteToByteArray(0, size = 4))
  }
  test ("map1 find test - allow order = true") {
//    Found solution? true
//    Depth = 1
//    M = 6
val t = new ByteArrayBloomierFilter(map1, initialM = 4, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, complete = false)
    val r = t.find()
    println(s"Found solution? ${r.isDefined}")
    println(s"Depth = ${t.getDepth()}")
    println(s"M = ${t.getM()}")
  }

  test ("map2 find test - allow order = true/false") {
    //    Found solution? true
    //    Depth = 3
    //    M = 13
    var t = new ByteArrayBloomierFilter(map2, initialM = 6, k = 3, q = 4*8, initialSeed = 0, maxTry = 20, complete = false)
    var r = t.find()
    println(s"Found solution? ${r.isDefined}")
    println(s"Depth = ${t.getDepth()}")
    println(s"M = ${t.getM()}")

    //    Found solution? true
    //    Depth = 1
    //    M = 28
    t = new ByteArrayBloomierFilter(map2, initialM = 6, k = 3, q = 4*8, initialSeed = 0, maxTry = 20, complete = true)
    r = t.find()
    println(s"Found solution? ${r.isDefined}")
    println(s"Depth = ${t.getDepth()}")
    println(s"M = ${t.getM()}")
  }

  test ("map1/2 get table") {
    var t = new ByteArrayBloomierFilter(map1, initialM = 6, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, complete = false)
    printTable(t.getTable())
    t = new ByteArrayBloomierFilter(map2, initialM = 6, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, complete = false)
    printTable(t.getTable())
  }

  test ("map1 get/set test") {
    var t = new ByteArrayBloomierFilter(map1, initialM = 6, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, complete = false)
    var res = t.get("a").get
    println(s"ByteArray - ${res.mkString(":")}, = ${ByteArrayTool.byteArrayToFloat(res)}")
    res = t.get("b").get
    println(s"ByteArray - ${res.mkString(":")}, = ${ByteArrayTool.byteArrayToByte(res)}")
    res = t.get("ax").get
    println(s"Empty for ax? - ${t.get("ax").isEmpty}, value = ${res.mkString(":")}/${ByteArrayTool.byteArrayToByte(res)}")
  }
  test ("map2 get/set test") {
    var t = new ByteArrayBloomierFilter(map2, initialM = 6, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, complete = false)
    var res = t.get("a").get
    println(s"ByteArray - ${res.mkString(":")}, = ${ByteArrayTool.byteArrayToFloat(res)}")
    res = t.get("b").get
    println(s"ByteArray - ${res.mkString(":")}, = ${ByteArrayTool.byteArrayToByte(res)}")
    println(s"Empty for h? - ${t.get("h").isEmpty}")
  }

  test("Serialize") {
    var t = new ByteArrayBloomierFilter(map3, initialM = 1, k = 3, q = 4*8, initialSeed = 0, maxTry = 5, complete = false)
    val res = t.serialize()
    println(res)
    assert(res.size == (2 + 1 + 4)) // 2 for the count, 1 for header, 4 for data
  }
}
