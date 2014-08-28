package simulation.theoryFalsePositives

import grapevineType.LongitudeType

/**
 * Created by smcho on 8/27/14.
 */
object Longitude {
  def fp(bytes: Int = LongitudeType.getSize) = {
    Location.fpFromDegree(bytes = bytes, degree = 360)
  }
  def fp_pair(bytes: Int = LongitudeType.getSize) = {
    Latitude.fp_pair(bytes)
  }
  def fp_near(bytes: Int = LongitudeType.getSize, near:Double = 10.0) = {
    Latitude.fp_near(bytes = bytes, near = near)
  }
}
