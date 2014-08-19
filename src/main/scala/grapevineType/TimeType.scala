package grapevineType

object TimeType {
  def getId = 6
  def getSize = (new TimeType).getSize
  val defaultValue = (0,0)
}

/**
 * This is 3 bytes (17 bits/full) implementation of time data
 * For 2 bytes (16 bits) implementation
 *     hour + minute (5 + 6 = 11 bits)
 * For 1 byte (8 bits) implementation
 *     hour + half/minute (5 + 3 = 8 bits) are used
 * Created by smcho on 8/11/14.
 */
case class TimeType(input:(Int, Int)) extends DoubleBitsType((5, 0, 23), (6, 0, 59)) {
  set(input)
  def this() = this(TimeType.defaultValue)
  this.signed = false

  override def getId = TimeType.getId
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 2 else goalSize
    super.toByteArray(size)
  }
  override def getTypeName() = "TimeType"
}
