package grapevineType

object LevelType {
  def getId = 12
  def getSize = (new LevelType).getSize
  val defaultValue = 0
  val min = 0
  val max = 10
  val bits = 8
}

/**
 * Created by smcho on 8/17/14.
 */
case class LevelType(input:Int) extends SingleBitsSingleByteType(LevelType.bits, LevelType.min, LevelType.max) {
  set(input)
  def this() = this(LevelType.defaultValue)

  this.signed = false
  override def getId(): Int = LevelType.getId
  override def getTypeName() = "LevelType"
}