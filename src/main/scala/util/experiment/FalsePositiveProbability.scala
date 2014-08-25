package util.experiment

/**
 * Created by smcho on 8/25/14.
 */
object FalsePositiveProbability {
  def location(byteWidth:Int, degree:Int) = {
    // (180*60*60*100)/(2^32)
    val fp = (degree*60.0*60.0*100.0)/(2.toLong << 32)
    if (byteWidth > 4.0) {
      fp / math.pow(2.0, (byteWidth - 4.0))
    }
    else
      fp
  }
  def latitude(byteWidth:Int) = {
    location(byteWidth, 180)
  }
  def longitude(byteWidth:Int) = {
    location(byteWidth, 360)
  }
}
