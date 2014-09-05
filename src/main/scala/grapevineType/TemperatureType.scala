package grapevineType

object TemperatureType {
  def getId = 11
  def getSize = (new TemperatureType).getSize
  val defaultValue = 0
  val bits = 8
  val max = 60
  val min = -50
}

/**
 * Created by smcho on 8/15/14.
 */
case class TemperatureType(input:Int) extends SingleBitsSingleByteType(TemperatureType.bits, TemperatureType.min, TemperatureType.max) {
  set(input)
  def this() = this(TemperatureType.defaultValue)
  this.signed = true

  override def getId(): Int = TemperatureType.getId
  override def getTypeName() = "TemperatureType"
}