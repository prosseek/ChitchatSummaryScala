package grapevineType
/**
 * Created by smcho on 8/11/14.
 */
class SpeedType extends SingleBitsType(8, 0, 150) {
  this.signed = false
  override def getId = 9
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 1 else goalSize
    super.toByteArray(size)
  }
}
