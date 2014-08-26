package grapevineType

import BottomType._
import util.conversion.ByteArrayTool

object FloatType {
  def getId = 3
  def getSize = 4
  val floatShift = 10.0F
  val defaultValue = 10.0F
}

/**
 * Created by smcho on 8/13/14.
 */
case class FloatType(input:Double) extends GrapevineType {
  set(input)
  def this() = this(FloatType.defaultValue)

  override def set(value: Any): Unit = {
    if (value.isInstanceOf[Float])
      this.value = value.asInstanceOf[Float]
    else if (value.isInstanceOf[Double])
      this.value = value.asInstanceOf[Double].toFloat
  }

  override def get() : Float = value.asInstanceOf[Float]

  /**
   * Returns if the input value is **not** in (0 - floatShift) or (-floatShift - 0)
   * @param f
   * @return
   */
  def encodingCheck(f:Float): Boolean = {
    if ((f >= 0.0F && f < FloatType.floatShift) || (f > -FloatType.floatShift && f < 0.0F)) {
      false
    }
    else {
      true
    }
  }

  def fromByteArray(b: Array[Byte]): BottomType = {
    if (super.fromByteArray(b, byteSize = 4, f = ByteArrayTool.byteArrayToFloat) == NoError) {
      try {
        val result = this.value.asInstanceOf[Float]
        if (encodingCheck(result)) {
          this.value = if (result >= 0) result - FloatType.floatShift else -(-result - FloatType.floatShift)
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

  /**
   * As the float/double is distributed over 0, the value is shifted to avoid it.
   * This is needed for detecting error better with Bloomier Filters
   * @param goalSize
   * @return
   */
  override def toByteArray(goalSize: Int): Array[Byte] = {
    val size = if (goalSize == -1) 4 else goalSize
    val v = value.asInstanceOf[Float]
    ByteArrayTool.floatToByteArray(if (v >= 0.0F) v + FloatType.floatShift else -(-v + FloatType.floatShift),
                                   size = size)
  }

  override def getId = FloatType.getId
  override def getSize = FloatType.getSize
  override def getTypeName() = "FloatType"
}
