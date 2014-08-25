package util.hash

import org.scalatest.FunSuite

/**
 * Created by smcho on 8/25/14.
 */
class TestHash extends FunSuite {
  test ("Large number ") {
    val maxVal = 100000000
    val count = 3
    val h = Hash.getHashes("hello", count = count, maxVal = maxVal, startSeed = 0)
    assert(h.size == count)
    assert(h.forall(_ < maxVal))
  }
}
