package grapevineType

/**
 * Created by smcho on 8/13/14.
 */
class FixedPointType extends GrapevineType {
  override def set(value: Any): Unit = {}

  def fromByteArray(b: Array[Byte]): Boolean = {true}

  override def toByteArray(goalSize: Int): Array[Byte] = {null}
}
