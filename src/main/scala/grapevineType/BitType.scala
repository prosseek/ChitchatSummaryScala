package grapevineType

object BitType {
  def getId = 13
  def getSize = (new BitType).getSize
  val defaultValue = 1
}

/**
 * Created by smcho on 8/17/14.
 */
case class BitType(input:Int) extends SingleBitsSingleByteType(1, 1, 1) {
  set(input)
  def this() = this(BitType.defaultValue)

  this.signed = false
  override def getId(): Int = BitType.getId
  override def getTypeName() = "BitType"
}