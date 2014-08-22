package core

import org.scalatest._

/**
 * Created by smcho on 8/10/14.
 */
class TestLabeledSummary extends FunSuite with BeforeAndAfter {
  var t: LabeledSummary = _
  before {
    t = new LabeledSummary
  }
  test ("Get size test") {
    val m = Map("A" -> 10, "B"->20, "C"->"test")
    t.create(m)
    val totalItem = m.size
    val keySize = (0 /: m.keys) {(acc, value) => acc + value.size}
    val valueSize = 1 + 1 + "test".size
    assert(t.getSize() == keySize + valueSize)
  }
}
