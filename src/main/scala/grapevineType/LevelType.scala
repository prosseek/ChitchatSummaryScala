package grapevineType

object LevelType {
  def getId = 12
  def getSize = (new LevelType).getSize
  val defaultValue = 0
}

/**
 * Created by smcho on 8/17/14.
 */
case class LevelType(input:Int) extends SingleBitsSingleByteType(8, 0, 10) {
  set(input)
  def this() = this(LevelType.defaultValue)

  this.signed = false
  override def getId(): Int = LevelType.getId
  override def getTypeName() = "LevelType"
}