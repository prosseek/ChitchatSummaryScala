package grapevineType

object DateType {
  def getId = 5
  def getSize = (new DateType).getSize
  val defaultValue = (0,1,1)
}

//  // 7 + 4 + 5 = 16 bits (2 bytes)
//  val yearBits = 7 // 0 - 127
//  val monthBits = 4 // 0 - 15
//  val dayBits = 5 // 0 - 31
case class DateType(input:(Int, Int, Int)) extends TripleBitsType((7, 0, 127), (4, 1, 12), (5, 1, 31)) {
  set(input)
  def this() = this(DateType.defaultValue)

  val yearBase = 2000
    override def set(year:Int, month:Int, day:Int) = {
      val yearp = year - yearBase
      super.set(yearp, month, day)
    }
    override def get(): (Int, Int, Int) = {
      val v = value.asInstanceOf[(Int, Int, Int)]
      (v._1 + yearBase, v._2, v._3)
    }

  override def getId = DateType.getId
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 2 else goalSize
    super.toByteArray(size)
  }
}
