package grapevineType

import org.scalatest.{BeforeAndAfter, FunSuite}
import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/23/14.
 */
class TestUnsignedShortType extends FunSuite with BeforeAndAfter {
  var t: UnsignedShortType = _
  before {
    t = new UnsignedShortType
  }
  test ("simple") {
    t = new UnsignedShortType
    t.set(12234)
    assert(t.get() == 12234)

    t.set(90)
    assert(t.get() == 90)
  }

  test ("to/from bytes") {
    val b = ByteArrayTool.shortToByteArray(-2, size = 2)
    t.fromByteArray(b)
    assert(t.get == 65534)

    val c = t.toByteArray(2)
    assert(b.deep == c.deep)
  }
}
