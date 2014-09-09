package util.conversion

import scala.collection.BitSet
import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 6/3/14.
 */
object BitSetTool {
  def byteToBitSet(x:Byte, shift:Int = 0) = {
    BitSet((for (i <- 0 to 7 if (((x & 0xFF) >> i) & 1) == 1) yield (i + shift)): _*)
  }
  def shortToBitSet(x:Short, shift:Int = 0) = {
    BitSet((for (i <- 0 to (16-1) if (((x & 0xFFFF) >> i) & 1) == 1) yield (i + shift)): _*)
  }
  def intToBitSet(x:Int, shift:Int = 0) = {
    BitSet((for (i <- 0 to (32-1) if (((x & 0xFFFFFFFF) >> i) & 1) == 1) yield (i + shift)): _*)
  }
  def bitSetToByte(b:BitSet, sh:Int=0) = ((0 /: b) {(acc, input) => acc + (1 << (input - sh))}).toByte
  def bitSetToShort(b:BitSet, sh:Int=0) = ((0 /: b) {(acc, input) => acc + (1 << (input - sh))}).toShort
  def bitSetToInt(b:BitSet, bitWidth:Int, sh:Int=0) = {
    val res = ((0 /: b) {(acc, input) => acc + (1 << (input - sh))})
    val maxInt = scala.math.pow(2.0, bitWidth-1).toInt - 1
    if (res > maxInt) {
      val mask = (scala.math.pow(2.0, bitWidth).toInt)
      res - mask
    }
    else
      res
  }
  def bitSetToUnsignedInt(b:BitSet, bitWidth:Int, sh:Int=0) = {
    val res = ((0 /: b) {(acc, input) => acc + (1 << (input - sh))})
    res
  }
}
