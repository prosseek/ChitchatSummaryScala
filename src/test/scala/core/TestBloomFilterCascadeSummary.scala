package core

import grapevineType.BottomType
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 8/16/14.
 */
class TestBloomFilterCascadeSummary extends FunSuite with BeforeAndAfter {
  var message = "hello, world?"
  var t: BloomFilterCascadeSummary = _
  var map1: Map[String, Any] = Map(
    "sport" -> "football",
    "position" -> "goalkeeper",
    "gchat id" -> "john1988",
    "latitude" -> (30, 25, 38, 2),
    "longitude" -> (-17, 47, 11, 0),
    "available time" -> (12, 15),
    "date" -> (2014, 6, 23),
    "skill level" -> 5
  )

  var map2: Map[String, Any] = Map(
    "sport" -> "foo",
    "position" -> "goa",
    "gchat id" -> "joh",
    "latitude" -> (30, 25, 38, 2),
    "longitude" -> (-17, 47, 11, 0),
    "available time" -> (12, 15),
    "date" -> (2014, 6, 23),
    "skill level" -> 5
  )

  before {
    t = new BloomFilterCascadeSummary
  }

  test("Size test") {
    t.create(map = map1, r = 11*8, m = List[Int](3,3,3), k = List[Int](2,2,2))
    println(t.getSize()._1 / 8 + 1)

    assert(t.check("available time") == BottomType.NoError)
    assert(t.get("available time") == map1("available time"))
    assert(t.check("date") == BottomType.NoError)
    assert(t.get("date") == map1("date"))
    assert(t.check("sport") == BottomType.NoError)
    assert(t.get("sport") == map1("sport"))
      //assert(t.getSize() == 102)

    t.create(map = map1, r = 11*8, m = List[Int](4,3,2), k = List[Int](2,2,2))
    println(t.getSize()) //_1 / 8 + 1)

    t.create(map = map1, r = 11*8, m = List[Int](4,3,2), k = List[Int](3,3,3))
    println(t.getSize()) // ._1 / 8 + 1)
  }

  test("Size test 2") {
    t.create(map = map2, r = 4*8, m = List[Int](3,3,3), k = List[Int](2,2,2))
    println(t.getSize()) //  / 8 + 1)

    t.create(map = map2, r = 11*8, m = List[Int](4,3,2), k = List[Int](2,2,2))
    println(t.getSize()) // / 8 + 1)

    t.create(map = map2, r = 11*8, m = List[Int](4,3,2), k = List[Int](3,3,3))
    println(t.getSize()) // / 8 + 1)
  }

//  test("Simple") {
//    t.create(map = map1, m = 6, k = 3, q = 8 * 4)
//    if (t.check("a_f") == NoError) {
//      assert(t.get("a_f") == 10.0)
//    }
//  }
//  test("test 1") {
//    def test(width: Int, map: Map[String, Any]) = {
//      t.create(map = map, m = 8, k = 3, q = 8 * width)
//      if (t.check("latitude") == NoError)
//        assert(t.get("latitude") ==(10, 10, 10, 10))
//      if (t.check("time") == NoError)
//        assert(t.get("time") ==(11, 11))
//      if (t.check("date") == NoError)
//        assert(t.get("date") ==(2014, 10, 01))
//      //      if (t.check("message") == NoError)
//      //        assert(t.get("message") == message)
//      println(s"width = ${width}, ${t.getSize}")
//    }
//  }
}