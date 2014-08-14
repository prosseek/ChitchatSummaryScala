package grapevineType

import util.conversion.BitSetTool

/**
 * This is 3 bytes (17 bits/full) implementation of time data
 * For 2 bytes (16 bits) implementation
 *     hour + minute (5 + 6 = 11 bits)
 * For 1 byte (8 bits) implementation
 *     hour + half/minute (5 + 3 = 8 bits) are used
 * Created by smcho on 8/11/14.
 */
class TimeType extends GrapevineType with RangeChecker {
  // time is stored in 3-tuple (hour, minute, second)
  // hour: 0 - 23 (5 bits)
  // minute: 0 - 59 (6 bits)
  // second: 0 - 59 (6 bits)
  // total: 5 + 6 + 6 = 17 bits (3 bytes)
  var value: (Int, Int, Int) = (0, 0, 0)

  // 5 + 6 + 6 = 17 bits (3 bytes)
  val hourBits3 = 5 // 1 - 23
  val minuteBits3 = 6 // 1 - 59
  val secondBits3 = 6 // 1 - 59

  // 5 + 6 + 0 = 11 bits (2 bytes)
  val hourBits2 = 5 // 1 - 23
  val minuteBits2 = 6 // 1 - 59
  val secondBits2 = 0 // 1 - 59

  // 5 + 3 + 0 = 8 bits (1 byte)
  val hourBits1 = 5 // 1 - 23
  val minuteBits1 = 3 // 1 - 59
  val secondBits1 = 0 // 1 - 59

  override def set(value: Any) : Unit = {
    val (hour, minute, second) = value.asInstanceOf[(Int, Int, Int)]
    set(hour, minute, second)
  }
  def set(hour:Int, minute:Int, second:Int) = {
    if (check(hour, 0, 23) && check (minute, 0, 59) && check(second, 0, 59)) {
      this.value = (hour, minute, second)
    }
    else {
      throw new RuntimeException(s"Time format error hour should be (0-23), hour should be (0-59), minute should be (0-59) ${hour}/${minute}/${second}")
    }
  }
  override def get() : (Int, Int, Int) = {
    this.value
  }

  def getBits(goalSize: Int) = {
    var hourBits = hourBits3
    var minuteBits = minuteBits3
    var secondBits = secondBits3

    if (goalSize == 1)  {
      hourBits = hourBits1
      minuteBits = minuteBits1
      secondBits = secondBits1
    }
    else if (goalSize == 2) {
      hourBits = hourBits2
      minuteBits = minuteBits2
      secondBits = secondBits2
    }
    (hourBits, minuteBits, secondBits)
  }

  /**
   * YEAR - 7 bits (0 - 127)
   * It's relative year from the base year; the year based on can be any year, but 2000 is the default.
   * MM - 4 bits (0 - 15)
   * DD - 5 bits (0 - 31)

   * @param goalSize
   * @return
   */
  override def toByteArray(goalSize: Int): Array[Byte] = {
    val hour = value._1
    val minute = value._2
    val second = value._3

    val (hourBits, minuteBits, secondBits) = getBits(goalSize)

    val res = BitSetTool.intToBitSet(hour, minuteBits + secondBits) ++
      BitSetTool.intToBitSet(minute, secondBits) ++
      BitSetTool.intToBitSet(second)

    BitSetTool.bitSetToByteArray(res)
  }
  override def fromByteArray(b: Array[Byte]): Boolean = {
    true
  }
}
