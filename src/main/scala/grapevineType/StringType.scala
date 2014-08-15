package grapevineType

import grapevineType.BottomType._
import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/11/14.
 */
class StringType extends GrapevineType {
  def isPrintable(v:Char) = {
    v >= 0x20 && v <= 0x7E
  }
  def check(value:String) = {
    value.forall {isPrintable}
  }
  def set(value: Any) : Unit = {
    if (check(value.asInstanceOf[String])) {
      this.value = value
    }
    else {
      throw new RuntimeException(s"ERROR: [${value}]")
    }
  }

  override def get() : String = {
    this.value.asInstanceOf[String]
  }

  override def toByteArray(goalSize: Int = -1): Array[Byte] = {
    val v = this.value.asInstanceOf[String]
    val size = if (goalSize == -1) v.size else goalSize
    ByteArrayTool.stringToByteArray(v, size)
  }

  def fromByteArray(ba: Array[Byte]): BottomType = {
    val npList = ba.zipWithIndex.filter { v => !isPrintable(v._1.toChar) } map {v => v._2}
    val strlen = if (npList.size == 0) ba.size else npList(0)

    if (super.fromByteArray(ba, byteSize = strlen, f = ByteArrayTool.byteArrayToString) == NoError) {
      try {
        val result = this.value.asInstanceOf[String]
        if (check(result)) {
          NoError
        } else {
          Computational
        }
      }
      // whenever we have an error, we return false
      catch {
        case e: Exception => Computational
      }
    }
    else {
      Computational
    }
  }
}