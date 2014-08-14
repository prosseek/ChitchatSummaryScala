package grapevineType

import org.scalatest._
import BottomType._

/**
 * Created by smcho on 8/13/14.
 */
class TestFloatType extends FunSuite with BeforeAndAfter {
  var t : FloatType = _

  before {
    t = new FloatType
  }
  test ("Simple test") {
    t.set(10.0)
    assert(t.get == 10.0)
  }
  test ("to/from byte array") {
    t.set(10.0)
    var bs = t.toByteArray(goalSize = 4)
    assert(t.fromByteArray(bs) == NoError)
    assert(t.get == 10.0)

    t.set(0.0)
    bs = t.toByteArray(goalSize = 4)
    assert(t.fromByteArray(bs) == NoError)
    assert(t.get == 0.0)

    t.set(-10.0)
    bs = t.toByteArray(goalSize = 4)
    assert(t.fromByteArray(bs) == NoError)
    assert(t.get == -10.0)
  }

}
