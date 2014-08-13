package grapevineType

/**
 * This is 3 bytes (full) implementation of time data
 * For 2 bytes (16 bits) implementation
 *     hour + minute (5 + 6 = 11 bits)
 * For 1 byte (8 bits) implementation
 *     hour + half/minute (5 + 3 = 8 bits) are used
 * Created by smcho on 8/11/14.
 */
class TimeType extends GrapevineType {
  // time is stored in 3-tuple (hour, minute, second)
  // hour: 0 - 23 (5 bits)
  // minute: 0 - 59 (6 bits)
  // second: 0 - 59 (6 bits)
  // total: 5 + 6 + 6 = 17 bits (3 bytes)
  var value: (Int, Int, Int) = (0, 0, 0)

  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[(Int, Int, Int)]
  }
  override def toByteArray(goalSize: Int): Array[Byte] = {
    null
  }
  override def fromByteArray(b: Array[Byte]): Unit = {
  }
}
