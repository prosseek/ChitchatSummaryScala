package grapevineType

import util.conversion.ByteArrayTool

/**
 * Created by smcho on 8/13/14.
 */
class FloatType extends GrapevineType {
  var value:Float = 0.0F
  val floatShift = 20.0F

  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[Double].toFloat
  }

  override def get() : Float = value

  /**
   * Returns if the input value is **not** in (0 - floatShift) or (-floatShift - 0)
   * @param f
   * @return
   */
  def encodingCheck(f:Float): Boolean = {
    if ((f >= 0.0F && f < floatShift) || (f > -floatShift && f < 0.0F)) {
      false
    }
    else {
      true
    }
  }

  override def fromByteArray(b: Array[Byte]): Boolean = {
    try {
      val result = ByteArrayTool.byteArrayToFloat(b)
      if (encodingCheck(result)) {
        this.value = if (result >= 0) result - floatShift else -(-result - floatShift)
        true
      } else {
        false
      }
    }
    // whenever we have an error, we return false
    catch {
      case e: Exception => false
    }
  }

  /**
   * As the float/double is distributed over 0, the value is shifted to avoid it.
   * This is needed for detecting error better with Bloomier Filters
   * @param goalSize
   * @return
   */
  override def toByteArray(goalSize: Int): Array[Byte] = {
    ByteArrayTool.floatToByteArray(if (value >= 0.0F) value + floatShift else -(-value + floatShift),
                                   size = goalSize)
  }
}
