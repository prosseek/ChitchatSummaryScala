package simulation.theoryFalsePositives

import grapevineType.LatitudeType

/**
 * Created by smcho on 8/27/14.
 */
object Latitude {
  def fp(bytes: Int = LatitudeType.getSize) = {
    Location.fpFromDegree(bytes = bytes, degree = 180)
  }
  def fp_pair(bytes: Int = LatitudeType.getSize) = {
    fp(bytes) * Longitude.fp(bytes)
  }
  def fp_near(bytes: Int = LatitudeType.getSize, near:Double = 10.0) = {
    fp_pair(bytes) * 0.25 * math.pow((near / Location.RADIUS_OF_EARTH), 2.0)
  }
}