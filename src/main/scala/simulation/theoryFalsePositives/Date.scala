package simulation.theoryFalsePositives

import grapevineType.DateType

/**
 * Created by smcho on 8/27/14.
 */
object Date {
  // DATE TIME
  def _date(days:Int) :Double = days.toDouble / (1.toLong << (DateType.getSize*8))

  def fp(bytes:Int) :Double = {
    val days = DateType.range * 365 // total date range defined in DateType
    val fp = _date(days)
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = DateType.getSize)
  }
  def fp_pair(bytes:Int) :Double = {
    Time.fp_pair(bytes)
  }

  /**
   * It's fp(time) * (range)/(2^16)
   * This is the same as fp(time)*fp(date)*(range)/(years) as fp(date) = (years)/2^16
   * @param bytes
   * @param near
   * @param both_directions
   * @return
   */
  def fp_near(bytes:Int, near:Int, both_directions:Boolean=false) = {
    val res = fp_pair(bytes) * near.toDouble/(DateType.range * 365.0)
    if (both_directions) res*2 else res
  }
}
