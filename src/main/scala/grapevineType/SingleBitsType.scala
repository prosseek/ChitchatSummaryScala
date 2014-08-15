package grapevineType

import util.conversion.ByteArrayTool._
import util.conversion.BitSetTool
import BottomType._

/**
 * The input parameter should be three tuples of data
 * (bits, min, max)
 */
class SingleBitsType(a:(Int, Int, Int)) extends BitsType {
  val bits = List(a._1)
  val ranges = List((a._2, a._3))

  override def set(value: Any) : Unit = {
    val (a, b) = value.asInstanceOf[(Int, Int)]
    set(a, b)
  }
  def set(aValue:Int) = {
    if (check(List(aValue), ranges)) {
      this.value = (aValue)
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
    val totalBytes = getTotalBytes(bits) // bits.sum/8 + 1 // get the total bytes for the encoded data
    if (super.fromByteArray(ba, byteSize = totalBytes) == NoError) {
      val bs = byteArrayToBitSet(ba)
      val bitSets = splitBitSets(bs, bits)

      try {
        set(BitSetTool.bitSetToInt(bitSets(0)))
        NoError
      }
      catch {
        // RuntimeException is raised from set when the values decoded are out of range
        case e: RuntimeException => Computational
      }
    } else {
      Computational
    }
  }
}