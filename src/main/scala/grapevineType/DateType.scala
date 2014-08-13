package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
class DateType extends GrapevineType {
  // YYYY/MM/DD format
  // YEAR - 7 bits (0 - 127)
  //   It's relative year from the base year; the year based on can be any year, but 2000 is the default.
  // MM - 4 bits (0 - 15)
  // DD - 5 bits (0 - 31)
  var value = (0, 0, 0)

  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[(Int, Int, Int)]
  }

  override def toByteArray(goalSize: Int): Array[Byte] = {
    null
  }
  override def fromByteArray(b: Array[Byte]): Unit = {
  }
}