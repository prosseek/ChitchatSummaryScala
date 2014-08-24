package grapevineType

import grapevineType.BottomType._
import util.conversion.BitSetTool
import util.conversion.ByteArrayTool._

/**
 * The input parameter should be three tuples of data
 * (bits, min, max)
 */
abstract class SingleBitsType(a:(Int, Int, Int)) extends BitsType {
  bits = List(a._1)
  ranges = List((a._2, a._3))

  override def set(value: Any) : Unit = {
    val a = value.asInstanceOf[Int]
    set(a)
  }
  def set(aValue:Int) = {
    if (check(List(aValue), ranges)) {
      this.value = getValue(aValue, bits(0), this.signed)
    }
    else {
      throw new RuntimeException(s"ERROR: a [${aValue}(${a._2}-${a._3})]")
    }
  }
  override def get() : Int = {
    this.value.asInstanceOf[Int]
  }

  override def toByteArray(goalSize: Int): Array[Byte] = {
    val v = value.asInstanceOf[Int]
    val values = List(v)

    val res = shiftAndJoin(values, bits)
    bitSetToByteArray(res, goalSize = goalSize)
  }


  def fromByteArray(ba: Array[Byte]): BottomType = {
    val totalBytes = getBytes(bits) // bits.sum/8 + 1 // get the total bytes for the encoded data
    if (super.fromByteArray(ba, byteSize = totalBytes) != NoError)
      return Computational
    // bug [2014/08/23]
    // You should not use byteArrayToBitSet, because the byte array is big endian.
    val bs = byteArrayToBitSet(ba.reverse)
    val bitSets = splitBitSets(bs, bits)

    if (bitSets.size > 1) return Computational

    try {
      set(BitSetTool.bitSetToInt(bitSets(0)))
      NoError
    }
    catch {
      // RuntimeException is raised from set when the values decoded are out of range
      case e: RuntimeException => Computational
    }
  }
}
