package grapevineType

import grapevineType.BottomType._
import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/13/14.
 */
class ByteType extends GrapevineType with RangeChecker {
  var value: Byte = -1
  val minValue = 0
  val maxValue = (-1 & 0xFF)

  var bottomType: BottomType = _

  def getBottomType() = bottomType
  def setBottomType(t:BottomType) = this.bottomType = t

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
    value
  }
  def check(value:Int, mini:Int = minValue, maxi:Int = maxValue): Boolean = {
    check[Int](value, mini, maxi)
  }
  override def toByteArray(goalSize:Int = 4): Array[Byte] = {
    ByteArrayTool.byteToByteArray(value, goalSize)
  }

  /**
   * This just checks if the additional bytes are all zero
   * [1][2][3][4]
   * 0  0  0  V  <-- For byte data, all the rest bits should be zero if correctly encoded
   *
   * @param b
   * @return
   */
  override def fromByteArray(b: Array[Byte]): Boolean = {
    val (head, tail) = b.splitAt(b.size - 1)
    if (head.forall(_ == 0)) {
      this.value = ByteArrayTool.byteArrayToByte(tail)
      setBottomType(NoError)
      true
    } else {
      setBottomType(Computational)
      false
    }
  }
}