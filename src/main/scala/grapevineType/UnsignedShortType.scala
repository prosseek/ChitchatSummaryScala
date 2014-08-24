package grapevineType

object UnsignedShortType {
  def getId = 1
  def getSize = (new UnsignedShortType).getSize
  val defaultValue = 0
}

/**
 * Created by smcho on 8/17/14.
 */
case class UnsignedShortType(input:Int) extends SingleBitsSingleByteType(16, 0, 65535) {
  set(input)
  def this() = this(UnsignedShortType.defaultValue)

  this.signed = false
  override def getId(): Int = UnsignedShortType.getId
  override def getTypeName() = "UnsignedShortType"
}

