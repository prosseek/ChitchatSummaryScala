package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
class LongitudeType extends LocationType {
  override def toByteArray(goalSize: Int): Array[Byte] = {
    null
  }
  override def fromByteArray(b: Array[Byte]): Boolean = {
    true
  }
}
