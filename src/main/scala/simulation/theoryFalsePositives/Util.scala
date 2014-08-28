package simulation.theoryFalsePositives

import bloomFilter.BloomFilter

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
  def getBf() = {
    val filePath = "experiment/data/words.txt"
    new BloomFilter(filePath, m = 20*100000, k = 5, seed = 0)
  }
}
