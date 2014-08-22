package grapevineType

import org.scalatest.FunSuite

class Dummy extends FormatChanger

/**
 * Created by smcho on 8/22/14.
 */
class TestFormatChanger extends FunSuite {
  test ("toDDS") {
    val h = new Dummy
    assert(math.abs(h.dms2dd(30,17,12,9) - 30.286916) < 0.0001)
    assert(math.abs(h.dms2dd((30,17,12,9)) - 30.286916) < 0.0001)
  }
}
