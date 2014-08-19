package grapevineType

object UnsignedByteType {
  def getId = 1
  def getSize = (new UnsignedByteType).getSize
  val defaultValue = 0
}

/**
 * Created by smcho on 8/17/14.
 */
case class UnsignedByteType(input:Int) extends SingleBitsSingleByteType(8, 0, 255) {
  set(input)
  def this() = this(UnsignedByteType.defaultValue)

  this.signed = false
  override def getId(): Int = UnsignedByteType.getId
  override def getTypeName() = "UnsignedByteType"
}
