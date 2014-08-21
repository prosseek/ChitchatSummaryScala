package util.conversion

/**
 * Created by smcho on 8/16/14.
 */
object Util {
  def getByteSize(q:Int) = {
    if (q == 0) 1
    else if (q % 8 == 0) q / 8
    else q / 8 + 1
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
}
