package simulation.theoryFalsePositives

import grapevineType.{TemperatureType, StringType}

/**
 * Created by smcho on 9/5/14.
 */
object Temperature {
  def fp(bytes:Int) :Double = {
    val fp = (TemperatureType.max - TemperatureType.min + 1.0)/(1 << TemperatureType.bits)
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = TemperatureType.getSize)
  }

  def fp_pair(bytes:Int) :Double = {
    val fp = Temperature.fp(bytes)
    fp * String.fp(StringType.getMiniumLength)
  }
}