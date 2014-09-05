package grapevineType

object AgeType {
  def getId = 9
  def getSize = (new AgeType).getSize
  val defaultValue = 0
  def getTypeName = "AgeType" //(new AgeType).getTypeName
  val min = 0
  val max = 127 // (1 << 7) - 1
  val bits = 8
}
/**
 * Age uses 1 byte (8 bits: 0 - 255)
 */
case class AgeType(input:Int) extends SingleBitsSingleByteType(AgeType.bits, AgeType.min, AgeType.max) {
  this.signed = false
  set(input)
  def this() = this(AgeType.defaultValue)

  override def getId() = AgeType.getId
  override def getTypeName() = AgeType.getTypeName
}
