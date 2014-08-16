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
}
