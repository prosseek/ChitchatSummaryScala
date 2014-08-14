package grapevineType

import BottomType._

/**
 * Created by smcho on 8/11/14.
 */
class LongitudeType extends LocationType {
  override def set(value: Any): Unit = {}

  override def fromByteArray(b: Array[Byte]): BottomType = {NoError}

  override def toByteArray(goalSize: Int): Array[Byte] = {null}
}
