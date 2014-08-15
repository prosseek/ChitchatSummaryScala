package grapevineType

import org.scalatest._
import util.conversion.ByteArrayTool._
import BottomType._
import scala.collection.BitSet

/**
 * Created by smcho on 8/13/14.
 */
class TestTimeType extends FunSuite with BeforeAndAfter {
  var t: TimeType = _
  before {
    t = new TimeType
  }

  test ("simple test") {
    t.set((10, 11))
    assert(t.get() == (10, 11))
    t.set(23, 59)
    assert(t.get() == (23, 59))
  }

  test ("range error test") {
    intercept[RuntimeException] {
      t.set(30, 11)
    }
    intercept[RuntimeException] {
      t.set(11, 99)
    }
  }

  test("to/from byteArray") {
    t.set(10, 11)
    val b = t.toByteArray(goalSize = 2)
    t.fromByteArray(b)
    assert(t.get() == (10,11))
  }

  test("Bottom_c check") {
    var ba = bitSetToByteArray(BitSet(7,8,9,33))
    assert(t.fromByteArray(ba) == Computational)
  }
}
