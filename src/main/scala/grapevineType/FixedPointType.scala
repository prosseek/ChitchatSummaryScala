package grapevineType

object FixedPointType {
  def getId = 2
  def getSize = (new FixedPointType).getSize
  val defaultValue = (0, 0)
}
/**
 * Created by smcho on 8/13/14.
 */
case class FixedPointType(input:(Int, Int)) extends DoubleBitsType((12, 0, 4095), (4, 0, 15)) {
  set(input)
  def this() = this(FixedPointType.defaultValue)

  override def getId = FixedPointType.getId
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 2 else goalSize
    super.toByteArray(size)
  }
  override def getTypeName() = "FixedPointType"
}
