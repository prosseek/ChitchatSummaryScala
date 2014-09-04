package util.conversion

/**
 * Created by smcho on 8/16/14.
 */
object Util {
  def ceil(q:Int, d:Int) = {
    if (q == 0) 1
    else if (q % d == 0) q / d
    else q / d + 1
  }

  def getByteSize(q:Int) = {
    ceil(q, 8)
  }

  def getBitWidth(size:Int) : Int = {
    if (size <= 0 || size == 1) return 1
    else
      (0 to 100).foreach { i =>
        if (scala.math.pow(2.0, i) < size && scala.math.pow(2.0, (i+1)) >= size)
          return i+1
      }
    throw new RuntimeException(s"${size} is not between 2^0 and 2^100")
  }

  def getBitSizeFromSize(size:Int) = {
    size * getBitWidth(size)
  }

  def getByteSizeFromSize(size:Int) = {
    getByteSize(getBitSizeFromSize(size))
  }

  def intToShort(v:Int) : Short = {
    val shortMax = ((1 << 15) - 1) // 32767
    if (v > shortMax) (v - (1 << 16)).toShort
    else v.toShort
  }

  def dms2dd(v:Any) :Double = {
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
