package grapevineType

import grapevineType.BottomType._
import util.conversion.BitSetTool
import util.conversion.ByteArrayTool._

/**
 * The input parameter should be three tuples of data
 * (bits, min, max)
 *
 * WARNING: For QuadrupleBitsType, the a is signed bit.
 *
 */
abstract class QuadrupleBitsType(a:(Int, Int, Int), b:(Int, Int, Int), c:(Int, Int, Int), d:(Int, Int, Int)) extends BitsType {
  bits = List(a._1, b._1, c._1, d._1)
  ranges = List((a._2, a._3), (b._2, b._3), (c._2, c._3), (d._2, d._3))

  override def set(value: Any) : Unit = {
    val (a, b, c, d) = value.asInstanceOf[(Int, Int, Int, Int)]
    set(a, b, c, d)
  }

  def set(aValue:Int, bValue:Int, cValue:Int, dValue:Int) = {
    val values = List(aValue, bValue, cValue, dValue)
    if (check(values, ranges)) {
      this.value = getValues(values, bits, this.signed) match {case List(a,b,c,d) => (a,b,c,d)}
    }
    else {
      throw new RuntimeException(s"ERROR: a [${aValue}(${a._2}-${a._3})] b [${bValue}(${b._2}-${b._3})] c [${cValue}(${c._2}-${c._3})] d [${dValue}(${d._2}-${d._3})]")
    }
  }
  override def get() : (Int, Int, Int, Int) = {
    this.value.asInstanceOf[(Int, Int, Int, Int)]
  }

  override def toByteArray(goalSize: Int): Array[Byte] = {
    val v = value.asInstanceOf[(Int,Int,Int,Int)]
    val values = List(v._1, v._2, v._3, v._4)
    val res = shiftAndJoin(values, bits)
    bitSetToByteArray(res, goalSize = goalSize)
  }

  override def fromByteArray(ba: Array[Byte]): BottomType = {
    val totalBytes = getBytes(bits) // get the total bytes for the encoded data
    //if (super.fromByteArray(ba, byteSize = totalBytes) == NoError) {

    //TODO:
    // it's to signed/unsigned/unsigned transform. Make sure this is the case
    if (super.fromByteArray(ba, byteSize = totalBytes) == NoError) {
      val bs = byteArrayToBitSet(ba)
      val bitSets = splitBitSets(bs, bits)
      try {
        // [2014/08/21] bug fix
        // For quadruple bits, the value can be signed
        // in that case we need to recover the sign bits
        set(BitSetTool.bitSetToInt(bitSets(0), bits(0)), // <- signed bit
          BitSetTool.bitSetToUnsignedInt(bitSets(1), bits(1)),
          BitSetTool.bitSetToUnsignedInt(bitSets(2), bits(2)),
          BitSetTool.bitSetToUnsignedInt(bitSets(3), bits(3)))
        //(set _).tupled(r) <-- tried but not so successful
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
