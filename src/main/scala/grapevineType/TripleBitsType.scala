package grapevineType

import util.conversion.ByteArrayTool._
import util.conversion.BitSetTool
import BottomType._

/**
 * The input parameter should be three tuples of data
 * (bits, min, max)
 */
class TripleBitsType(a:(Int, Int, Int), b:(Int, Int, Int), c:(Int, Int, Int)) extends BitsType {
  val bits = List(a._1, b._1, c._1)
  val ranges = List((a._2, a._3), (b._2, b._3), (c._2, c._3))

  override def set(value: Any) : Unit = {
    val (a, b, c) = value.asInstanceOf[(Int, Int, Int)]
    set(a, b, c)
  }
  def set(aValue:Int, bValue:Int, cValue:Int) = {
    if (check(List(aValue, bValue, cValue), ranges)) {
      this.value = (aValue, bValue, cValue)
    }
    else {
      throw new RuntimeException(s"ERROR: a [${aValue}(${a._2}-${a._3}] b [${bValue}(${b._2}-${b._3}] c [${cValue}(${c._2}-${c._3}]")
    }
  }
  override def get() : (Int, Int, Int) = {
    this.value.asInstanceOf[(Int,Int,Int)]
  }

  override def toByteArray(goalSize: Int): Array[Byte] = {
    val v = value.asInstanceOf[(Int,Int,Int)]
    val values = List(v._1, v._2, v._3)

    val res = shiftAndJoin(values, bits)
    bitSetToByteArray(res, goalSize = goalSize)
  }

  def fromByteArray(ba: Array[Byte]): BottomType = {
    val totalBytes = bits.sum/8 + 1 // get the total bytes for the encoded data
    if (super.fromByteArray(ba, byteSize = totalBytes) == NoError) {
      val bs = byteArrayToBitSet(ba)
      val bitSets = splitBitSets(bs, bits)

      try {
        set(BitSetTool.bitSetToInt(bitSets(0)),
          BitSetTool.bitSetToInt(bitSets(1)),
          BitSetTool.bitSetToInt(bitSets(2)))
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
