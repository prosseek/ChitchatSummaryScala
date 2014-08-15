package grapevineType
/**
 * Created by smcho on 8/13/14.
 */
class FixedPointType extends DoubleBitsType((12, 0, 4095), (4, 0, 15)) {
  override def getId = 1
}
