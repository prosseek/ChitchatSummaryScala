package grapevineType

object DateType {
  def getId = 5
  def getSize = (new DateType).getSize
  val defaultValue = (2014,1,1)
  val yearBase = 2010
  val range = 10 // ten years (0 - 9) in 7 bits
}

//WARNING
// The year is stored in 7 bits.
// The set() should be used with the idea that yearBase will be subtracted,
// for fromByteArray(), we don't care as we just stores in 7 bytes, but when it's shown the yearBase
// should be added, so the get() method should be invoked.
//
//  // 7 + 4 + 5 = 16 bits (2 bytes)
//  val yearBits = 7 // 0 - 127
//  val monthBits = 4 // 0 - 15
//  val dayBits = 5 // 0 - 31
case class DateType(input:(Int, Int, Int)) extends TripleBitsType((7, 0, DateType.range-1), (4, 1, 12), (5, 1, 31)) {
  this.signed = false
  set(input)
  def this() = this(DateType.defaultValue)

  def set(value:(Int, Int, Int)) :Unit = {
    set(value._1, value._2, value._3)
  }
  override def set(year:Int, month:Int, day:Int) = {
    val yearp = year - DateType.yearBase
    super.set(yearp, month, day)
  }
  override def get(): Any = { // (Int, Int, Int) = {
    val v = value.asInstanceOf[(Int, Int, Int)]
    (v._1 + DateType.yearBase, v._2, v._3)
  }

  override def getId = DateType.getId
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 2 else goalSize
    super.toByteArray(size)
  }
  override def getTypeName() = "DateType"

}
