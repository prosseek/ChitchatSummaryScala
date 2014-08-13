package util.conversion

import scala.collection.BitSet
import scala.collection.mutable.ArrayBuffer
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
  def byteArrayToBitSet(x:Array[Byte]) = {
    var res = ArrayBuffer[Int]()
    for ((v,i) <- x.zipWithIndex if v != 0) {
      res.appendAll(BitSetTool.byteToBitSet(v).toArray.map(_ + 8*i))
    }
    scala.collection.immutable.BitSet(res: _*)
  }

  def bitSetToByteArray(x:BitSet) = {
    val bits = MMap[Int, Byte]().withDefaultValue(0)
    for (i <- x) {
      val bitLocation = i % 8
      val group = i / 8
      bits(group) = (bits(group) + (1 << bitLocation)).toByte
    }
    val byteArray = Array.fill[Byte](bits.keys.max + 1)(0)

    for ((k,v) <- bits) {
      byteArray(k) = v
    }
    byteArray
  }
  def bitSetToByte(b:BitSet, sh:Int=0) = ((0 /: b) {(acc, input) => acc + (1 << (input - sh))}).toByte
  def bitSetToShort(b:BitSet, sh:Int=0) = ((0 /: b) {(acc, input) => acc + (1 << (input - sh))}).toShort
  def bitSetToInt(b:BitSet, sh:Int=0) = ((0 /: b) {(acc, input) => acc + (1 << (input - sh))})
}
