package grapevineType

import grapevineType.BottomType._

/**
 * Created by smcho on 8/13/14.
 */
class RangeType(range:(Int, Int)) extends ByteType {
  override val minValue = range._1
  override val maxValue = range._2

  override def fromByteArray(b: Array[Byte]): BottomType = {
    val r = super.fromByteArray(b)
    // Just check if all the header bits (when byte size is more than one) are zero
    if (r == NoError) {
      // checking at this level means Bottom_r in that it checks the relationship
      if (check(value.asInstanceOf[Byte] & 0xFF)) NoError
      else {
        //setBottomType(Computational)
        Computational
      } // if not in range false is returned
    }
    else r
  }
}
