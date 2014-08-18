package grapevineType

object AgeType {
  def getId = 9
  def getSize = (new AgeType).getSize
  val defaultValue = 0
}
/**
 * Age uses 1 byte (8 bits: 0 - 255)
 */
case class AgeType(input:Int) extends SingleBitsSingleByteType(8, 0, 120) {
  this.signed = false
  set(input)
  def this() = this(AgeType.defaultValue)

  override def getId(): Int = AgeType.getId
}
