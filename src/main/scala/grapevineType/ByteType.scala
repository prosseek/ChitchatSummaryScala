package grapevineType

/**
 * Created by smcho on 8/15/14.
 */
class ByteType extends SingleBitsType(8, -128, 127){
  override def getId(): Int = 0
  override def toByteArray(goalSize:Int) = {
    val size = if (goalSize == -1) 1 else goalSize
    super.toByteArray(size)
  }
}

