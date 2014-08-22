package grapevineType

import BottomType._
import org.scalatest._
/**
 * Created by smcho on 8/14/14.
 */
class TestLongitudeType extends FunSuite with BeforeAndAfter {
  var t: LongitudeType = _
  before {
    t = new LongitudeType
  }
  test ("simple") {
    t.set(100, 10, 20, 40)
    assert(t.get == (100, 10, 20, 40))

    intercept[RuntimeException] {
      t.set(-181, 10, 20, 40)
    }
  }

  test ("to/from byte array") {
    t.set(31, 10, 33, 99)
    val ba = t.toByteArray()
    t.fromByteArray(ba)
    assert(t.get() == (31, 10,  33, 99))
  }

  test ("to/from byte array - error") {
    val ba = Array[Byte](-1, 74, 33, 3)
    assert(t.fromByteArray(ba) == Computational)
  }

  test ("toDouble") {
    t.set(-97, 53, 19, 0)
    //println(t.toDouble)
    assert(math.abs(t.toDouble - -97.88862) < 0.01)
  }
}
