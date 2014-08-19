package grapevineType

import grapevineType.BottomType._
import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/11/14.
 */

object StringType {
  def isPrintable(v:Char) = {
    v >= 0x20 && v <= 0x7E
  }
  def getId = 4
}

case class StringType(input:String) extends GrapevineType {
  if (input != null) set(input)
  def this() = this(null)

  def check(value:String) = value.forall {StringType.isPrintable}
  def set(value: Any) : Unit = {
    if (check(value.asInstanceOf[String])) this.value = value
    else throw new RuntimeException(s"ERROR: [${value}]")
  }

  override def get() : String = this.value.asInstanceOf[String]

  override def toByteArray(goalSize: Int): Array[Byte] = {
    val v = this.value.asInstanceOf[String]
    val size = if (goalSize == -1) v.size else goalSize
    ByteArrayTool.stringToByteArray(v, size)
  }

  def fromByteArray(ba: Array[Byte]): BottomType = {
    // http://stackoverflow.com/questions/25328027/detecting-the-index-in-a-string-that-is-not-printable-character-with-scala
    val npList = ba.indexWhere(v => !StringType.isPrintable(v.toChar))
    val strlen = if (npList == -1) ba.size else npList

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
  override def getId = StringType.getId
  override def getSize = this.value.asInstanceOf[String].size
  override def getTypeName() = "StringType"
}