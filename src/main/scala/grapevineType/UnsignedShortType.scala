package grapevineType

import grapevineType.BottomType._
import util.conversion.{Util, ByteArrayTool}

object UnsignedShortType {
  def getId = 1
  def getSize = (new UnsignedShortType).getSize
  val defaultValue = 0
}

/**
 * Created by smcho on 8/17/14.
 */
case class UnsignedShortType(input:Int) extends SingleBitsType(16, 0, 65535) {
  set(input)
  def this() = this(UnsignedShortType.defaultValue)

  this.signed = false
  override def getId(): Int = UnsignedShortType.getId
  override def getTypeName() = "UnsignedShortType"
  override def toByteArray(goalSize:Int = -1) : Array[Byte] = {
    val size = if (goalSize == -1) 2 else goalSize
    assert(size >= 2)
    val v = value.asInstanceOf[Int]

    val res = ByteArrayTool.shortToByteArray(Util.intToShort(v), size)
    res
  }

  override def fromByteArray(ba:Array[Byte]) :BottomType = {
    if (ba.size > 2 && !ba.slice(2, ba.size).forall(_ == 0)) {
      return Computational
    }
    else {
      super.fromByteArray(ba.slice(0,2))
    }
  }
}

