package simulation.theoryFalsePositives

/**
 * Created by smcho on 8/27/14.
 */
object Util {
  def reduced(value:Double, totalBytes:Int, thresholdBytes:Int) = {
    if (totalBytes <= thresholdBytes) value
    else {
      value / (1.toLong << 8*(totalBytes - thresholdBytes))
    }
  }
}
