package simulation.theoryFalsePositives

import grapevineType._

/**
 * Created by smcho on 9/5/14.
 */
object Bit {
  def fp(bytes:Int) :Double = {
    val fp = 1.0/256.0
    Util.reduced(fp, totalBytes = bytes, thresholdBytes = BitType.getSize)
  }
}