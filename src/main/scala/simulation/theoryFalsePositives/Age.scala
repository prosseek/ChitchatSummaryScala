package simulation.theoryFalsePositives

import grapevineType.{StringType, AgeType}

/**
 * Created by smcho on 9/5/14.
 */
object Age {
  def fp(bytes:Int) :Double = {
    val fp = (AgeType.max - AgeType.min + 1.0)/(1 << AgeType.bits)
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = AgeType.getSize)
  }

  def fp_pair(bytes:Int) :Double = {
    val fp = Age.fp(bytes)
    fp * String.fp(StringType.getMiniumLength)
  }
}