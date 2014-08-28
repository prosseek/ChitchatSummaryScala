package simulation.theoryFalsePositives

import bloomFilter.BloomFilter
import grapevineType._

import scala.math._

/**
 * Created by smcho on 8/28/14.
 */
object String {
  def fp(minimumSize:Int=StringType.getMiniumLength) = {
    // 0x20 - 0x7E (95/256)
    // 1/256 * a^(start)*(a^(255 - start + 1) - 1)/(a - 1)
    // or 1/256 * a^(start)/(a - 1)
    val alpha = (95.0/256.0)
    1.0/256.0 * pow(alpha, minimumSize) * (pow(alpha, 256 - minimumSize) - 1)/(alpha - 1.0)
  }
  def fp_bf(bfInput:BloomFilter = null, minimumSize:Int=StringType.getMiniumLength) = {
    var newBf = bfInput
    if (newBf == null) {
      newBf = Util.getBf()
    }
    newBf.getFp() * fp(minimumSize)
  }
}