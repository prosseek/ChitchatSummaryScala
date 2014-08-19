package grapevineType

object TemperatureType {
  def getId = 11
  def getSize = (new TemperatureType).getSize
  val defaultValue = 0
}

/**
 * Created by smcho on 8/15/14.
 */
case class TemperatureType(input:Int) extends SingleBitsSingleByteType(8, -50, 60) {
  set(input)
  def this() = this(TemperatureType.defaultValue)
  this.signed = false

  override def getId(): Int = TemperatureType.getId
  override def getTypeName() = "TemperatureType"
}