package grapevineType

/**
 * Created by smcho on 8/15/14.
 */
class TemperatureType extends SingleBitsType(8, -50, 60) {
  override def getId(): Int = 10
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 1 else goalSize
    super.toByteArray(size)
  }
}
