package grapevineType

import util.conversion.ByteArrayTool._
import util.conversion.BitSetTool
import BottomType._

/**
 * The input parameter should be three tuples of data
 * (bits, min, max)
 */
abstract class DoubleBitsType(a:(Int, Int, Int), b:(Int, Int, Int)) extends BitsType {
  bits = List(a._1, b._1)
  ranges = List((a._2, a._3), (b._2, b._3))

  override def set(value: Any) : Unit = {
    val (a, b) = value.asInstanceOf[(Int, Int)]
    set(a, b)
  }
  def set(aValue:Int, bValue:Int) = {
    val values = List(aValue, bValue)
    if (check(values, ranges)) {
      this.value = getValues(values, bits, this.signed) match {case List(a,b) => (a,b)}
    }
    else {
      throw new RuntimeException(s"ERROR: a [${aValue}(${a._2}-${a._3})] b [${bValue}(${b._2}-${b._3})]")
    }
  }
  override def get() : (Int, Int) = {
    this.value.asInstanceOf[(Int,Int)]
  }

  override def toByteArray(goalSize: Int): Array[Byte] = {
    val v = value.asInstanceOf[(Int,Int)]
    val values = List(v._1, v._2)

    val res = shiftAndJoin(values, bits)
    bitSetToByteArray(res, goalSize = goalSize)
  }

  override def fromByteArray(ba: Array[Byte]): BottomType = {
    val totalBytes = getBytes(bits) // get the total bytes for the encoded data
    if (super.fromByteArray(ba, byteSize = totalBytes) == NoError) {
      val bs = byteArrayToBitSet(ba)
      val bitSets = splitBitSets(bs, bits)

      //TODO:
      // it's to unsigned transform. Make sure this is the case
      try {
        set(BitSetTool.bitSetToUnsignedInt(bitSets(0), bits(0)),
          BitSetTool.bitSetToUnsignedInt(bitSets(1), bits(1)))
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
