package grapevineType

/**
 * Created by smcho on 8/13/14.
 */
class RangeType(range:(Int, Int)) extends ByteType {
  override val minValue = range._1
  override val maxValue = range._2

  override def fromByteArray(b: Array[Byte]): Boolean = {
    // check Bottom_c -> Just check if encoding is OK
    if (super.fromByteArray(b)) true
    else false
  }
}
