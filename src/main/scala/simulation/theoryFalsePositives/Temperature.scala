package simulation.theoryFalsePositives

import grapevineType.{TemperatureType, StringType}

/**
 * Created by smcho on 9/5/14.
 */
object Temperature {
  def fp_range(bytes:Int, min:Int, max:Int) = {
    val fp = (max - min + 1.0)/(1 << TemperatureType.bits)
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = TemperatureType.getSize)
  }

  def fp(bytes:Int) :Double = {
    fp_range(bytes = bytes, max = TemperatureType.max, min = TemperatureType.min)
  }

  def fp_pair(bytes:Int) :Double = {
    val fp = Temperature.fp(bytes)
    fp * String.fp(StringType.getMiniumLength)
  }

  def fp_near(bytes:Int, min:Int, max:Int) = {
    val fp = fp_range(bytes, min=min, max=max)
    fp * String.fp(StringType.getMiniumLength)
  }

}