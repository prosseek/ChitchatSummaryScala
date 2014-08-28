package simulation.theoryFalsePositives

import org.scalatest.FunSuite

/**
 * Created by smcho on 8/27/14.
 */
class TestUtil extends FunSuite {
  test ("reduced") {
    assert(Util.reduced(value = 100, totalBytes = 10, thresholdBytes = 4) == 100.0/(1.toLong << 6*8))
    assert(Util.reduced(value = 100, totalBytes = 4, thresholdBytes = 4) == 100.0)
  }
}
