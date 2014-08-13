package grapevineType

import util.conversion.BitSetTool

/**
 * Created by smcho on 8/11/14.
 */
class DateType extends GrapevineType with RangeChecker {
  // YYYY/MM/DD format

  val yearBase = 2000
  var value = (0, 0, 0)
  val yearBits = 7 // 0 - 127
  val monthBits = 4 // 0 - 15
  val dayBits = 5 // 0 - 31

  override def set(value: Any) : Unit = {
    this.value = value.asInstanceOf[(Int, Int, Int)]
  }
  def set(year:Int, month:Int, day:Int) = {
    if (check(year, 2000, 2127) && check (month, 1, 12) && check(day, 1, 31)) {
      this.value = (year, month, day)
    }
    else {
      throw new RuntimeException(s"Date format error year should be (2000-2017), month should be (1-12), day should be (1-31) ${year}/${month}/${day}")
    }
  }
  override def get() : (Int, Int, Int) = {
    this.value
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
    val year = value._1 - yearBase
    val month = value._2
    val day = value._3

    // second argument is shift bits
    val res = BitSetTool.intToBitSet(year, monthBits + dayBits) ++
              BitSetTool.intToBitSet(month, dayBits) ++
              BitSetTool.intToBitSet(day)

    BitSetTool.bitSetToByteArray(res)
  }
  override def fromByteArray(b: Array[Byte]): Boolean = {
    val bs = BitSetTool.byteArrayToBitSet(b)
    val day = bs.filter(_ < dayBits)
    val month = bs.filter(v => v < dayBits + monthBits && v >= dayBits).map(_ - dayBits)
    val year = bs.filter(_ >= dayBits + monthBits).map(_ - (dayBits + monthBits))
    try {
      set(BitSetTool.bitSetToInt(year) + yearBase,
        BitSetTool.bitSetToInt(month),
        BitSetTool.bitSetToInt(day))
      true
    }
    catch {
      case e: RuntimeException => false
    }
  }
}