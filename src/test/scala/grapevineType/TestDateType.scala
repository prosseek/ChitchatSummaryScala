package grapevineType

import org.scalatest._
import util.conversion.ByteArrayTool._
import BottomType._
import scala.collection.BitSet

/**
 * Created by smcho on 8/13/14.
 */
class TestDateType extends FunSuite with BeforeAndAfter {
  var t: DateType = _
  before {
    t = new DateType
  }

  test ("simple test") {
    t.set((2013, 10, 22))
    assert(t.get() == (2013, 10, 22))
    t.set(2013, 10, 22)
    assert(t.get() == (2013, 10, 22))
  }

  test ("range error test") {
    intercept[RuntimeException] {
      t.set(1013, 10, 25)
    }
    intercept[RuntimeException] {
      t.set(2013, 16, 25)
    }
    intercept[RuntimeException] {
      t.set(2013, 10, 44)
    }
  }

  test("to/from byteArray") {
    t.set(2014, 10, 11)
    val b = t.toByteArray(goalSize = 2)
    t.fromByteArray(b)
    assert(t.get() == (2014,10,11))
  }

  test("Bottom_c check") {
    //t.set(2000,12,31) -> BitSet(0, 1, 2, 3, 4, 7, 8)
    //println(BitSetTool.byteArrayToBitSet(t.toByteArray(4)))
    var ba = bitSetToByteArray(BitSet(7,8)) //  [0-4]->0:0, [5-8]->2,3 (2^2 + 2^3 = 12), 2000/12/0
    assert(t.fromByteArray(ba) == Computational)
    ba = bitSetToByteArray(BitSet(0,5,7,8)) //  2000/13/1
    assert(t.fromByteArray(ba) == Computational)
  }

}
