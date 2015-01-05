package core

import grapevineType.BottomType
import grapevineType.BottomType._
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 8/16/14.
 */
class TestBloomierFilterSummary extends FunSuite with BeforeAndAfter {
  var message = "hello, world?"
  var t: BloomierFilterSummary = _
  var map1: Map[String, Any] = Map("a_f" -> 10.0, "message" -> "hi", "c count" -> 20, "d count" -> 30)

  def getMap(message:String) = {
    var map: Map[String, Any] = Map("latitude" ->(10, 10, 10, 10),
      "message" -> message, // , world?",
      "time" ->(11, 11),
      "date" ->(2014, 10, 01))
    map
  }

  before {
    t = new BloomierFilterSummary
  }
  test ("Size test") {
    // map1 size
    t.create(map = map1, m = 8, k = 3, q = 8*4)
    val expectedSize = t.getM()/8 + 4 * 4
    assert(t.getSize()._1 == expectedSize)
  }
  test ("Simple") {
    t.create(map = map1, m = 6, k = 3, q = 8*4)
    if (t.check("a_f") == NoError) {
      assert(t.get("a_f") == 10.0)
    }
  }
  test ("test 1") {
    def test(width:Int, map:Map[String, Any]) = {
      t.create(map = map, m = 8, k = 3, q = 8*width)
      if (t.check("latitude") == NoError)
        assert(t.get("latitude") == (10,10,10,10))
      if (t.check("time") == NoError)
        assert(t.get("time") == (11,11))
      if (t.check("date") == NoError)
        assert(t.get("date") == (2014,10,01))
//      if (t.check("message") == NoError)
//        assert(t.get("message") == message)
      println(s"width = ${width}, ${t.getSize}")
    }

    message = "Hello, world"
    test(1, getMap(message))
    test(2, getMap(message))
    test(3, getMap(message))
    test(4, getMap(message))
    test(5, getMap(message))
    test(6, getMap(message))
    test(7, getMap(message))
    test(8, getMap(message))

    val ls = new LabeledSummary
    ls.create(dict = getMap(message))
    println(s"Labeled - ${ls.getSize}")
  }
  test ("load") {
    val ls = t
    ls.load("experiment/data/sample_context.txt")
    assert(ls.check("abc") == BottomType.NoError)
    assert(ls.get("abc") == "Hello, world")
    assert(ls.check("recommendation") == BottomType.NoError)
    assert(ls.get("recommendation") == "Chef")
    assert(ls.check("level of recommendation") == BottomType.NoError)
    assert(ls.get("level of recommendation") == 5)
    assert(ls.check("level of recommendations") != BottomType.NoError)
  }
  test ("store") {
    val filePath = "experiment/data/save_test_context.txt"
    val ls = t
    val file = new java.io.File(filePath)
    if (file.exists()) {
      file.delete()
    }
    val m = Map[String, Any]("a" -> "helloa", "level of a" -> 3)
    ls.create(m)
    ls.save(filePath)

    ls.load(filePath)
    assert(ls.check("a") == BottomType.NoError)
    assert(ls.get("a") == m("a"))
    assert(ls.check("level of a") == BottomType.NoError)
    assert(ls.get("level of a") == m("level of a"))
  }
}
