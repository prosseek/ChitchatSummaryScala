package grapevineType

/**
 * Age uses 1 byte (8 bits: 0 - 255)
 */
class AgeType extends ByteType {
  var maxi:Byte = 120
  var mini:Byte = 0

  def check(value:Int) : Boolean = {
    check(value, mini.toInt, maxi.toInt)
  }

  override def set(value:Any) = {
    super.set(value.asInstanceOf[Int].toByte, mini, maxi)
  }

  override def fromByteArray(b: Array[Byte]): Boolean = {
    if (super.fromByteArray(b)) {
      if (check(value)) true
      else false // if not in range false is returned
    } // Bottom_c
    else false
  }
}
