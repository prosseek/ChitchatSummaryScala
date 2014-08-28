package simulation.theoryFalsePositives

import grapevineType._

/**
 * Created by smcho on 8/25/14.
 */
object Location {
  val RADIUS_OF_EARTH = util.distance.Util.R
  // LOCATION
  def fpFromDegree(bytes: Int, degree: Int) = {
    val fp = (degree * 60.0 * 60.0 * 100.0) / (1.toLong << (LatitudeType.getSize * 8))
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = LatitudeType.getSize)
  }
}


