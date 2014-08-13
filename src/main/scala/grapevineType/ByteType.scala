package grapevineType

import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/13/14.
 */
abstract class ByteType extends GrapevineType with RangeChecker {
  var value: Byte = -1
  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[Int].toByte
  }
  override def get(): Byte = {
    value
  }
  def check(value:Int): Boolean = {
    val maxi = (-1 & 0xFF).toInt
    val mini = 0
    check[Int](value, mini, maxi)
  }
  override def toByteArray(goalSize:Int = 4): Array[Byte] = {
    ByteArrayTool.byteToByteArray(value, goalSize)
  }
  override def fromByteArray(b: Array[Byte]): Unit = {
    value = ByteArrayTool.byteArrayToByte(b)
  }
}