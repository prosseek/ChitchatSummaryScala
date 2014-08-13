package util.conversion

import java.nio.ByteBuffer

/**
 * Created by smcho on 5/31/14.
 *
 * http://stackoverflow.com/questions/2183240/java-integer-to-byte-array
 */
object ByteArrayTool {

  /**
   * Add more bytes to the value:Array[Byte]
   *
   * @param value
   * @param originalSize
   * @param goalSize
   */
  def adjust(value:Array[Byte], originalSize:Int, goalSize:Int, signExtension:Boolean = false) : Array[Byte] = {
    if (goalSize == originalSize) return value // nothing to do when the goal size is the same as originalSize
    if (goalSize < originalSize) throw new Exception(s"Goal size (${goalSize}}) should be larger than original size (${originalSize}})")

    var v:Byte = 0
    if (signExtension) {
      // ByteBuffer uses BigEndian, so use the lower bytes to check the sign
      if (value(0) < 0) v = -1.toByte
    }
    val head = Array.fill[Byte](goalSize - originalSize)(v)
    head ++ value
  }

  /*
    from Data : T -> ByteArray
   */
  // int
  def intToByteArray(x: Int) = ByteBuffer.allocate(4).putInt(x).array()
  def intToByteArray(x: Int, size: Int) : Array[Byte] = {
    val res = intToByteArray(x)
    adjust(value = res, originalSize = 4, goalSize = size, signExtension = true)
  }

  // short
  def shortToByteArray(x: Short) = ByteBuffer.allocate(2).putShort(x).array()
  def shortToByteArray(x: Short, size: Int) : Array[Byte] = {
    val res = shortToByteArray(x)
    adjust(value = res, originalSize = 2, goalSize = size, signExtension = true)
  }

  // long
  def longToByteArray(x: Long) = ByteBuffer.allocate(8).putLong(x).array()
  def longToByteArray(x: Long, size: Int) : Array[Byte] = {
    val res = longToByteArray(x)
    adjust(value = res, originalSize = 8, goalSize = size, signExtension = true)
  }

  // byte
  def byteToByteArray(x: Byte) = ByteBuffer.allocate(1).put(x).array()
  def byteToByteArray(x: Byte, size: Int) : Array[Byte] = {
    val res = byteToByteArray(x)
    adjust(value = res, originalSize = 1, goalSize = size, signExtension = true)
  }

  // double
  def doubleToByteArray(x: Double) = {
    val l = java.lang.Double.doubleToLongBits(x)
    longToByteArray(l)
  }
  def doubleToByteArray(x: Double, size:Int = 8) = {
    if (size < 8) throw new Exception(s"Double data should be at least 8 bytes, but given ${size}")
    val l = java.lang.Double.doubleToLongBits(x)
    adjust(longToByteArray(l), originalSize = 8, goalSize = size)
  }

  // float
  def floatToByteArray(x: Float) = {
    val l = java.lang.Float.floatToIntBits(x)
    intToByteArray(l)
  }
  def floatToByteArray(x: Float, size:Int = 4) = {
    if (size < 4) throw new RuntimeException(s"Float data should be at least 4 bytes, but given ${size}")
    val l = java.lang.Float.floatToIntBits(x)
    adjust(intToByteArray(l), originalSize = 4, goalSize = size)
  }

  // string
  def stringToByteArray(x: String, n:Int) = {
    assert (n >= x.size)
    // When n is given a value more than 0, it makes room for storing all of the n bytes
    //val size = x.size // if (n <= 0) x.size else n
    //ByteBuffer.allocate(n).put(x.slice(0, size).getBytes()).array()
    val diff = n - x.size
    ByteBuffer.allocate(n).put((x + " "*diff).getBytes()).array()
  }

  /*
    ByteArray to Data : T
   */

  /**
   * given a byte array to split it into n bytes
   * @param n
   * @param x
   */
  def checkedByteArray(x: Array[Byte], n:Int, f: Array[Byte] => AnyVal) = {
    val (header, value) = x.splitAt(x.size - n)
    if (header.forall(_ == 0)) Some(f(value)) else None
  }

  /**
   * detect the location of 0
   * http://stackoverflow.com/questions/23976309/trimming-byte-array-when-converting-byte-array-to-string-in-java-scala
   * @param x
   */
  def byteArrayToString(x: Array[Byte]) = {
    new String( x.array.takeWhile(_ > 0), "ASCII" )
  }

  // byte
  /**
   * ByteBuffer.wrap(x).get() -> Returns the value (Byte)
   * @param x
   * @return
   */
  def byteArrayToByte(x: Array[Byte]) = {
    ByteBuffer.wrap(x).get()
  }

  def checkedByteArrayToByte(x: Array[Byte]) = {
    checkedByteArray(x, 1, byteArrayToByte)
  }

  // int
  def byteArrayToInt(x: Array[Byte]) = {
    ByteBuffer.wrap(x).getInt
  }
  def checkedByteArrayToInt(x: Array[Byte]) = {
    checkedByteArray(x, 4, byteArrayToInt)
  }

  // long
  def byteArrayToLong(x: Array[Byte]) = {
    ByteBuffer.wrap(x).getLong
  }
  def checkedByteArrayToLong(x: Array[Byte]) = {
    checkedByteArray(x, 8, byteArrayToLong)
  }

  // double
  def checkedByteArrayF(x: Array[Byte], n:Int, fn: Array[Byte] => AnyVal) = {
    val (header, value) = x.splitAt(x.size - n)
    try {
      if (!header.forall(_ == 0)) None
      else {
        val f = fn(value)
        if (if (n == 4) f.asInstanceOf[Float].isNaN else f.asInstanceOf[Double].isNaN) None else Some(f)
      }
    }
    catch {
      case _:java.nio.BufferUnderflowException => None
    }
  }

  def byteArrayToDouble(x: Array[Byte]) = {
    ByteBuffer.wrap(x).getDouble
  }
  def checkedByteArrayToDouble(x: Array[Byte]) = {
    checkedByteArrayF(x, 8, byteArrayToDouble)
  }

  // float
  def byteArrayToFloat(x: Array[Byte]) = {
    ByteBuffer.wrap(x).getFloat
  }
  def checkedByteArrayToFloat(x: Array[Byte]) = {
    checkedByteArrayF(x, 4, byteArrayToFloat)
  }
}

