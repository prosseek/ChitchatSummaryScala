package grapevineType

import util.conversion.ByteArrayTool
import BottomType._

/**
 * Created by smcho on 8/13/14.
 */
class ByteType extends GrapevineType with RangeChecker {
  val minValue = 0
  val maxValue = (-1 & 0xFF)

  override def set(value: Any): Unit = {
    set(value.asInstanceOf[Int], minValue, maxValue)
  }
  def set(value:Int, mini:Int, maxi:Int) : Unit = {
    if (check(value, mini, maxi)) {
      this.value = value.toByte
    }
    else {
      throw new RuntimeException(s"ERROR: value ${value} is out of range (${mini}/${maxi})")
    }
  }
  override def get(): Byte = {
    value.asInstanceOf[Byte]
  }
  def check(value:Int, mini:Int = minValue, maxi:Int = maxValue): Boolean = {
    check[Int](value, mini, maxi)
  }
  override def toByteArray(goalSize:Int = 4): Array[Byte] = {
    ByteArrayTool.byteToByteArray(value.asInstanceOf[Byte], goalSize)
  }
  def fromByteArray(b: Array[Byte]): BottomType = {
    super.fromByteArray(b, byteSize = 1, f = ByteArrayTool.byteArrayToByte)
  }
}