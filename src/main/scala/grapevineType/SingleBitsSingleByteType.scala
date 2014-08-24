package grapevineType

import grapevineType.BottomType._

/**
 * Created by smcho on 8/17/14.
 */
abstract class SingleBitsSingleByteType (a:(Int, Int, Int)) extends SingleBitsType(a) {
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 1 else goalSize
    val res = super.toByteArray(size)
    // [2014/08/23] bug
    // byte array should be stored as big endian
    res.reverse
  }

  override def fromByteArray(ba:Array[Byte]) :BottomType = {
    if (ba.size > 1 && !ba.slice(1, ba.size).forall(_ == 0)) {
      return Computational
    }
    else {
      super.fromByteArray(ba.slice(0,1))
    }
  }
}
