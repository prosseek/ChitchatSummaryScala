package grapevineType

import org.scalatest._
import BottomType._
/**
 * Created by smcho on 8/14/14.
 */
class TestLongitudeType extends FunSuite with BeforeAndAfter {
  var t: LongitudeType = _
  before {
    t = new LongitudeType
  }
  test ("simple") {
    t.set(32, 10, 20, 40)
    assert(t.get == (32, 10, 20, 40))

    intercept[RuntimeException] {
      t.set(-91, 10, 20, 40)
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
}
