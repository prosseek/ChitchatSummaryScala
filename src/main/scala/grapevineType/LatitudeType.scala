package grapevineType

object LatitudeType {
  def getId = 7
  def getSize = (new LatitudeType).getSize
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
case class LatitudeType(input:(Int, Int, Int, Int))
      extends QuadrupleBitsType((8, -90, 90), (6, 0, 59), (6, 0, 59), (7, 0, 99))
      with FormatChanger {
  set(input)
  def this() = this(LatitudeType.defaultValue)

  override def getId = LatitudeType.getId
  def toByteArray() : Array[Byte] = toByteArray(-1)
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 4 else goalSize
    super.toByteArray(size)
  }
  override def getTypeName() = "LatitudeType"
  def toDouble() = {
    dms2dd(value)
  }
}
