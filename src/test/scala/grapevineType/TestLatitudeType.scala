package grapevineType

import org.scalatest._
import BottomType._
/**
 * Created by smcho on 8/14/14.
 */
class TestLatitudeType extends FunSuite with BeforeAndAfter {
  var t: LatitudeType = _
  before {
    t = new LatitudeType
  }
  test ("simple") {
    t.set(100, 10, 20, 40)
    assert(t.get == (100, 10, 20, 40))

    intercept[RuntimeException] {
      t.set(-181, 10, 20, 40)
    }
  }

  test ("to/from byte array - error") {
    val ba = Array[Byte](-1, 74, 33, 3)
    assert(t.fromByteArray(ba) == Computational)
  }
}
