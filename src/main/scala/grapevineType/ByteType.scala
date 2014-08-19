package grapevineType

object ByteType {
  def getId = 0
  def getSize = (new ByteType).getSize
  val defaultValue = 0
}
/**
 * Created by smcho on 8/15/14.
 */
case class ByteType(input:Int) extends SingleBitsSingleByteType(8, -128, 127){
  set(input)
  def this() = this(ByteType.defaultValue)

  override def getId = ByteType.getId
  override def getTypeName() = "ByteType"
}

