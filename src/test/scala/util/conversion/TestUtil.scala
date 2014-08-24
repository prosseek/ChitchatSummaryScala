package util.conversion

import org.scalatest.FunSuite

/**
 * Created by smcho on 8/21/14.
 */
class TestUtil extends FunSuite {
  test ("getBitWidth") {
    // 0 -> 1, 1 -> 1, 2 -> 1bit, 3 -> 2, 4 -> 2, 5-> 3, 6->3, 7->3, 8->3, 9->4, 10->4
    val results = Array(1, 1,1, 2,2, 3,3,3,3, 4,4)
    (0 to 10).foreach { i =>
      assert(Util.getBitWidth(i) == results(i))
    }
  }

  test ("getBitSizeFromSize") {
    val results = Array(0, 1, 2, 6, 8, 15, 18, 21, 24, 36, 40)
    (0 to 10).foreach { i =>
      assert(Util.getBitSizeFromSize(i) == results(i))
    }
  }
  test ("getByteSizeFromSize") {
    val results = Array(1, 1, 1, 1, 1, 2, 3, 3, 3, 5, 5)
    (0 to 10).foreach { i =>
      assert(Util.getByteSizeFromSize(i) == results(i))
    }
  }
  test ("intToShort") {
    var input = 1.toShort
    assert(Util.intToShort(1) == input)

    var intInput = 1 << 15
    assert(Util.intToShort(intInput) == (1 << 15).toShort)
  }
}
