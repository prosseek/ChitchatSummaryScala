package simulation

/**
 * Created by smcho on 8/25/14.
 */
object FalsePositiveProbability {
  def location(width:Int, degree:Int) = {
    // (180*60*60*100)/(2^32)
    val fp = (degree*60.0*60.0*100.0)/(2.toLong << 32)
    if (width > 4.0) {
      fp / math.pow(2.0, (width - 4.0))
    }
    else
      fp
  }
  def latitude(width:Int) = {
    location(width, degree = 180)
  }
  def longitude(width:Int) = {
    location(width, degree = 360)
  }
}
