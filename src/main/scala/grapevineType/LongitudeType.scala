package grapevineType

object LongitudeType {
  def getId = 8
  def getSize = (new LongitudeType).getSize
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
case class LongitudeType(input:(Int, Int, Int, Int)) extends QuadrupleBitsType((9, -180, 180), (6, 0, 59), (6, 0, 59), (7, 0, 99)) with FormatChanger {
  set(input)
  def this() = this(LongitudeType.defaultValue)

  override def getId = LongitudeType.getId

  def toByteArray(): Array[Byte] = toByteArray(-1)

  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 4 else goalSize
    super.toByteArray(size)
  }
  override def getTypeName() = "LongitudeType"

  def toDouble() = {
    dms2dd(value)
  }

//  //TODO - duplicate code
//  // This code checks the upper bits of the 4 bytes if they are zero or not
//  override def fromByteArray(ba: Array[Byte]): BottomType = {
//    val sumBits = bits.sum
//    if (ByteArrayTool.byteArrayToBitSet(ba).filter(_ >= sumBits).size > 0)
//      BottomType.Computational
//    else
//      super.fromByteArray(ba)
//  }
}
