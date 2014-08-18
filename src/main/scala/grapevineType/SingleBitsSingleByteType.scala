package grapevineType

/**
 * Created by smcho on 8/17/14.
 */
abstract class SingleBitsSingleByteType (a:(Int, Int, Int)) extends SingleBitsType(a) {
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 1 else goalSize
    super.toByteArray(size)
  }
}
