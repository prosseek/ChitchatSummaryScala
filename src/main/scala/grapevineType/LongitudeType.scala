package grapevineType

object LongitudeType {
  def getId = 8
  def getSize = (new LongitudeType).getSize
  val defaultValue = (0,0,0,0)
}
/**
 * DMSS format
 *
 * Total 27 bits - 8 * 4 = 32 bits with 5 bits empty
 *
 * Degree  (-90 - 90) 2**9 = 512 => 8 bits
 * Minute  (0 - 59) 2**6 = 64 => 6 bits
 * Second  (0 - 59) 2**6 = 64 => 6 bits
 * Second' (0 - 99) 2**7 = 128 => 7 bits
 */
case class LongitudeType(input:(Int, Int, Int, Int)) extends QuadrupleBitsType((8, -90, 90), (6, 0, 59), (6, 0, 59), (7, 0, 99)) {
  set(input)
  def this() = this(LongitudeType.defaultValue)

  override def getId = LongitudeType.getId
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 4 else goalSize
    super.toByteArray(size)
  }
}
