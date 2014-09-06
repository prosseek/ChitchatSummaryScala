package simulation.theoryFalsePositives

import grapevineType.{StringType, FloatType}

/**
 * Created by smcho on 9/5/14.
 */
object Float {
  def fp(bytes:Int) :Double = {
    val fp = 0.5
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = FloatType.getSize)
  }

  def fp_pair(bytes:Int) :Double = {
    val fp = Float.fp(bytes)
    fp * String.fp(StringType.getMiniumLength)
  }
}
