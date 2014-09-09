package grapevineType

import util.conversion.ByteArrayTool._
import util.conversion.BitSetTool
import BottomType._

/**
 * The input parameter should be three tuples of data
 * (bits, min, max)
 */
abstract class TripleBitsType(a:(Int, Int, Int), b:(Int, Int, Int), c:(Int, Int, Int)) extends BitsType {
  bits = List(a._1, b._1, c._1)
  ranges = List((a._2, a._3), (b._2, b._3), (c._2, c._3))

  override def set(value: Any) : Unit = {
    val (a, b, c) = value.asInstanceOf[(Int, Int, Int)]
    set(a, b, c)
  }


  /**
   *
   * @param aValue
   * @param bValue
   * @param cValue
   * @param aValueOffset
   */
  def checkAndSet(aValue:Int, bValue:Int, cValue:Int) = {
    val values = List(aValue, bValue, cValue)
    if (check(values, ranges)) {
      this.value = getValues(values, bits, this.signed) match {case List(a,b,c) => (a,b,c)}
    }
    else {
      throw new RuntimeException(s"ERROR: a [${aValue}(${a._2}-${a._3})] b [${bValue}(${b._2}-${b._3})] c [${cValue}(${c._2}-${c._3})]")
    }
  }
//
  def set(aValue:Int, bValue:Int, cValue:Int) = {
    checkAndSet(aValue, bValue, cValue)
  }
//  // fromByteArray calls overriden set method (DateType#set)
//  // _set is just to make sure that the method is not invoked
//  def _set(aValue:Int, bValue:Int, cValue:Int) = {
//    checkAndSet(aValue, bValue, cValue)
//  }
  override def get() : Any = { // (Int, Int, Int) = {
    this.value.asInstanceOf[(Int,Int,Int)]
  }

  override def toByteArray(goalSize: Int): Array[Byte] = {
    val v = value.asInstanceOf[(Int,Int,Int)]
    val values = List(v._1, v._2, v._3)

    val res = shiftAndJoin(values, bits)
    bitSetToByteArray(res, goalSize = goalSize)
  }

  override def fromByteArray(ba: Array[Byte]): BottomType = {
    val totalBytes = getBytes(bits) // get the total bytes for the encoded data
    if (super.fromByteArray(ba, byteSize = totalBytes) == NoError) {
      val bs = byteArrayToBitSet(ba)
      val bitSets = splitBitSets(bs, bits)

      //TODO:
      // it's to signed/unsigned/unsigned transform. Make sure this is the case
      try {
        // Bug  [2014/08/21]
        // When DateType calls `fromByteArray`, the DataType.set() extracts
        // Basedate to cause an error.
        // This set should be strictly calling TripleBitsType.scala
        checkAndSet(BitSetTool.bitSetToInt(bitSets(0), bits(0)),
          BitSetTool.bitSetToUnsignedInt(bitSets(1), bits(1)),
          BitSetTool.bitSetToUnsignedInt(bitSets(2), bits(2)))
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
