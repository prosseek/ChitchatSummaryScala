package grapevineType

/**
 * Age uses 1 byte (8 bits: 0 - 255)
 */
class AgeType extends ByteType {
  var maxi:Byte = 120
  var mini:Byte = 0

  override def check(value:Int) : Boolean = {
    check(value, mini.toInt, maxi.toInt)
  }

  override def set(value:Any) = {
    if (check(value.asInstanceOf[Int].toByte)) {
      super.set(value)
    }
    else {
      throw new RuntimeException(s"For age type, the range should be between ${mini} - ${maxi}")
    }
  }
}
