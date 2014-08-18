package grapevineType

object LatitudeType {
  def getId = 7
  def getSize = (new LatitudeType).getSize
  val defaultValue = (0,0,0,0)
}

/**
 * DMSS format
 *
 * Degree  (-180 - 180) 2**9 = 512 => 9 bits
 * Minute  (0 - 60) 2**6 = 64 => 6 bits
 * Second  (0 - 60) 2**6 = 64 => 6 bits
 * Second' (0 - 99) 2**7 = 128 => 7 bits
 */
case class LatitudeType(input:(Int, Int, Int, Int)) extends QuadrupleBitsType((9, -180, 180), (6, 0, 59), (6, 0, 59), (7, 0, 99)) {
  set(input)
  def this() = this(LatitudeType.defaultValue)

  override def getId = LatitudeType.getId
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 4 else goalSize
    super.toByteArray(size)
  }
}
