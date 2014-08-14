package grapevineType

import util.conversion.ByteArrayTool._
import util.conversion.BitSetTool
import BottomType._

/**
 * The input parameter should be three tuples of data
 * (bits, min, max)
 */
class BitsType(a:(Int, Int, Int), b:(Int, Int, Int), c:(Int, Int, Int)) extends GrapevineType with RangeChecker {
  //var value = (0, 0, 0)
  var aBits = a._1
  var bBits = b._1
  var cBits = c._1

  override def set(value: Any) : Unit = {
    val (a, b, c) = value.asInstanceOf[(Int, Int, Int)]
    set(a, b, c)
  }
  def set(aValue:Int, bValue:Int, cValue:Int) = {
    if (check(aValue, a._2, a._3) && check (bValue, b._2, b._3) && check(cValue, c._2, c._3)) {
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
    val a = v._1
    val b = v._2
    val c = v._3

    // second argument is shift bits
    val res = BitSetTool.intToBitSet(a, bBits + cBits) ++
      BitSetTool.intToBitSet(b, cBits) ++
      BitSetTool.intToBitSet(c)

    bitSetToByteArray(res, goalSize = goalSize)
  }

  def fromByteArray(ba: Array[Byte]): BottomType = {
    val totalBytes = (aBits + bBits + cBits)/8 + 1 // get the total bytes for the encoded data
    if (super.fromByteArray(ba, byteSize = totalBytes) == NoError) {
      val bs = byteArrayToBitSet(ba)
      val c = bs.filter(_ < cBits)
      val b = bs.filter(v => v < cBits + bBits && v >= cBits).map(_ - cBits)
      val a = bs.filter(_ >= cBits + bBits).map(_ - (cBits + bBits))
      try {
        set(BitSetTool.bitSetToInt(a),
          BitSetTool.bitSetToInt(b),
          BitSetTool.bitSetToInt(c))
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
