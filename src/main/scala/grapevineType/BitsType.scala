package grapevineType

import util.conversion.ByteArrayTool._
import util.conversion.BitSetTool
/**
 * The input parameter should be three tuples of data
 * (bits, min, max)
 */
class BitsType(a:(Int, Int, Int), b:(Int, Int, Int), c:(Int, Int, Int)) extends GrapevineType with RangeChecker {
  //var value = (0, 0, 0)
  var aBits = a._1
  var bBits = b._2
  var cBits = c._3

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
    val a = value.asInstanceOf[(Int,Int,Int)]_1
    val b = value.asInstanceOf[(Int,Int,Int)]_2
    val c = value.asInstanceOf[(Int,Int,Int)]_3

    // second argument is shift bits
    val res = BitSetTool.intToBitSet(a, bBits + cBits) ++
      BitSetTool.intToBitSet(b, cBits) ++
      BitSetTool.intToBitSet(c)

    bitSetToByteArray(res)
  }
  def fromByteArray(b: Array[Byte]): Boolean = {
    val bs = byteArrayToBitSet(b)
    val day = bs.filter(_ < cBits)
    val month = bs.filter(v => v < cBits + bBits && v >= cBits).map(_ - cBits)
    val year = bs.filter(_ >= cBits + bBits).map(_ - (cBits + bBits))
    try {
      set(BitSetTool.bitSetToInt(year),
        BitSetTool.bitSetToInt(month),
        BitSetTool.bitSetToInt(day))
      true
    }
    catch {
      case e: RuntimeException => false
    }
  }

}
