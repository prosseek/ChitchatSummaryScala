package grapevineType
/**
 * Created by smcho on 8/11/14.
 */
class SpeedType extends SingleBitsType(8, 0, 150) {
  this.signed = false
  override def getId = 9
}
