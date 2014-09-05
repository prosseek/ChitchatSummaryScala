package simulation.theoryFalsePositives

import grapevineType.{StringType, LevelType}

/**
 * Created by smcho on 9/5/14.
 */
object Level {
  def fp(bytes:Int) :Double = {
    val fp = (LevelType.max - LevelType.min + 1.0)/(1 << LevelType.bits)
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = LevelType.getSize)
  }

  def fp_pair(bytes:Int) :Double = {
    val fp = Level.fp(bytes)
    fp * String.fp(StringType.getMiniumLength)
  }
}