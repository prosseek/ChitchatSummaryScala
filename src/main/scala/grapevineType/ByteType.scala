package grapevineType

/**
 * Created by smcho on 8/15/14.
 */
class ByteType extends SingleBitsType(8, -128, 127){
  override def getId(): Int = 0
}

