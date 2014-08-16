package core

import grapevineType.BottomType._
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 8/16/14.
 */
class TestBloomierFilterSummary extends FunSuite with BeforeAndAfter {
  var t: BloomierFilterSummary = _
  var map1: Map[String, Any] = Map("a_f" -> 10.0, "message" -> "hi", "c" -> 20, "d" -> 30)

  before {
    t = new BloomierFilterSummary
  }
  test ("Size test") {
    // map1 size
    t.create(map = map1, m = 8, k = 3, q = 8*4)
    val expectedSize = t.getM() + 4 * 4
    assert(t.getSize() == expectedSize)
  }
  test ("Simple") {
    t.create(map = map1, m = 8, k = 3, q = 8*4)
    if (t.check("a_f") == NoError) {
      println(t.get("a_f"))
    }
  }
}
