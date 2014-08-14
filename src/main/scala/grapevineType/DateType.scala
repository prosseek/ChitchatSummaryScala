package grapevineType

//  // 7 + 4 + 5 = 16 bits (2 bytes)
//  val yearBits = 7 // 0 - 127
//  val monthBits = 4 // 0 - 15
//  val dayBits = 5 // 0 - 31
class DateType extends BitsType((7, 0, 127), (4, 1, 12), (5, 1, 31)) {
    val yearBase = 2000
    override def set(year:Int, month:Int, day:Int) = {
      val yearp = year - yearBase
      super.set(yearp, month, day)
    }
    override def get(): (Int, Int, Int) = {
      val v = value.asInstanceOf[(Int, Int, Int)]
      (v._1 + yearBase, v._2, v._3)
    }
}
