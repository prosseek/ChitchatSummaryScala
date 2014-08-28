package simulation.theoryFalsePositives

import grapevineType.TimeType

/**
 * Created by smcho on 8/27/14.
 */
object Time {
  def fp(bytes: Int = TimeType.getSize) = {
    val fp = (24.0*60.0) / (1.toLong << (TimeType.getSize*8))
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = TimeType.getSize)
  }
  def fp_pair(bytes: Int = TimeType.getSize) = {
    fp(bytes) * Date.fp(bytes)
  }
  def fp_near(bytes: Int = TimeType.getSize, near:Int = 60, both_directions:Boolean) = {
    Date.fp_near(bytes = bytes, near = near, both_directions = both_directions)
  }
}

