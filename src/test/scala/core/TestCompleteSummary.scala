package core

import grapevineType.BottomType
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 1/5/15.
 */
class TestCompleteSummary extends FunSuite with BeforeAndAfter {
  var t: CompleteSummary = _
  before {
    t = new CompleteSummary
  }
  test("serialize") {
    val m = Map("A count" -> 10, "B count"->20, "C"->"test")
    t.create(m)
    val s = t.serialize()
    println(s)
  }
}
