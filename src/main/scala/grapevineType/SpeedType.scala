package grapevineType

object SpeedType {
  def getId = 10
  def getSize = (new SpeedType).getSize
  val defaultValue = 0
  val min = 0
  val max = 150
  val bits = 8
}
/**
 * Created by smcho on 8/11/14.
 */
case class SpeedType(input:Int) extends SingleBitsSingleByteType(SpeedType.bits, SpeedType.min, SpeedType.max) {
  set(input)
  def this() = this(SpeedType.defaultValue)

  this.signed = false
  override def getId = SpeedType.getId
  override def getTypeName() = "SpeedType"
}
