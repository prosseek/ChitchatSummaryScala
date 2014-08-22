package grapevineType

/**
 * Created by smcho on 8/22/14.
 */
trait FormatChanger {
  def dms2dd(v:Any) : Double = {
    if (v.isInstanceOf[(Int, Int, Int, Int)]) {
      val value = v.asInstanceOf[(Int, Int, Int, Int)]
      dms2dd(value._1, value._2, value._3, value._4)
    }
    else {
      throw new RuntimeException(s"Wrong input ${v}: The input should be (Int, Int, Int, Int)")
    }
  }
  def dms2dd(d:Int, m:Int, s1:Int, s2:Int) = {
    val s:Double = s"${s1}.${s2}".toDouble
    if (d > 0)
      d.toDouble + m.toDouble/60.0 + s/3600.0
    else
      -(-d.toDouble + m.toDouble/60.0 + s/3600.0)
  }
}
