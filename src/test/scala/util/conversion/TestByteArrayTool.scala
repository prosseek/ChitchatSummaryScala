package util.conversion

import org.scalatest._

import scala.collection.BitSet

/**
 * Created by smcho on 5/31/14.
 */
class TestByteArrayTool extends FunSuite {

  // double
  test ("double to byte array and back test ") {
    var value = 12.34
    assert(value == ByteArrayTool.byteArrayToDouble(ByteArrayTool.doubleToByteArray(value)))
    value = 1234.56
    assert(value == ByteArrayTool.byteArrayToDouble(ByteArrayTool.doubleToByteArray(value)))
    value = 0.001234
    assert(value == ByteArrayTool.byteArrayToDouble(ByteArrayTool.doubleToByteArray(value)))
    value = 0.0
    assert(value == ByteArrayTool.byteArrayToDouble(ByteArrayTool.doubleToByteArray(value)))
  }

  // float
  test ("float to byte array and back test ") {
    var value = 12.34F
    assert(value == ByteArrayTool.byteArrayToFloat(ByteArrayTool.floatToByteArray(value)))
    value = 1234.56F
    assert(value == ByteArrayTool.byteArrayToFloat(ByteArrayTool.floatToByteArray(value)))
    value = 0.001234F
    assert(value == ByteArrayTool.byteArrayToFloat(ByteArrayTool.floatToByteArray(value)))
    value = 0.0F
    assert(value == ByteArrayTool.byteArrayToFloat(ByteArrayTool.floatToByteArray(value)))
  }

  // byte
  test ("byte to byte array and back test ") {
    var value : Byte = 0
    assert(value == ByteArrayTool.byteArrayToByte(ByteArrayTool.byteToByteArray(value)))
    value = Byte.MaxValue
    assert(value == ByteArrayTool.byteArrayToByte(ByteArrayTool.byteToByteArray(value)))
    value = Byte.MinValue
    assert(value == ByteArrayTool.byteArrayToByte(ByteArrayTool.byteToByteArray(value)))
    value = 123
    assert(value == ByteArrayTool.byteArrayToByte(ByteArrayTool.byteToByteArray(value)))
    value = -123
    assert(value == ByteArrayTool.byteArrayToByte(ByteArrayTool.byteToByteArray(value)))
  }

  // int
  test ("int to byte array and back test ") {
    var value = 0
    assert(value == ByteArrayTool.byteArrayToInt(ByteArrayTool.intToByteArray(value)))
    value = Int.MaxValue
    assert(value == ByteArrayTool.byteArrayToInt(ByteArrayTool.intToByteArray(value)))
    value = Int.MinValue
    assert(value == ByteArrayTool.byteArrayToInt(ByteArrayTool.intToByteArray(value)))
    value = 12343434
    assert(value == ByteArrayTool.byteArrayToInt(ByteArrayTool.intToByteArray(value)))
  }

  // short
  test ("short to byte array and back test ") {
    var value = 0.toShort
    assert(value == ByteArrayTool.byteArrayToShort(ByteArrayTool.shortToByteArray(value)))
    value = Short.MaxValue
    assert(value == ByteArrayTool.byteArrayToShort(ByteArrayTool.shortToByteArray(value)))
    value = Short.MinValue
    assert(value == ByteArrayTool.byteArrayToShort(ByteArrayTool.shortToByteArray(value)))
    value = -2.toShort
    val s = ByteArrayTool.shortToByteArray(value)
    val a = ByteArrayTool.byteArrayToShort(s)
    assert(value == a)
  }

  // long
  test ("long to byte array and back test ") {
    var value = 0L
    assert(value == ByteArrayTool.byteArrayToLong(ByteArrayTool.longToByteArray(value)))
    value = Long.MaxValue
    assert(value == ByteArrayTool.byteArrayToLong(ByteArrayTool.longToByteArray(value)))
    value = Long.MinValue
    assert(value == ByteArrayTool.byteArrayToLong(ByteArrayTool.longToByteArray(value)))
    value = 12343434L
    assert(value == ByteArrayTool.byteArrayToLong(ByteArrayTool.longToByteArray(value)))
  }

  // string
  // TODO!
  // CHECK THIS OUT, REMAKE THE STRING TEST
  test ("string to byte array and back test") {
    var value = "Hello, world"
    assert(value == ByteArrayTool.byteArrayToString(ByteArrayTool.stringToByteArray(value, 12))) // before it was 10
    // even though the buffer size is bigger, the return value should be OK
    //assert(value == ByteArray.byteArrayToString(ByteArray.stringToByteArray(value, 100)))
    //assert("He" == ByteArray.byteArrayToString(ByteArray.stringToByteArray(value, 2)))
  }

  test("bitSet to bytearray test") {
    var x = BitSet(0,1,2,3,8,10,104)
    var y = ByteArrayTool.bitSetToByteArray(x)
    assert(y.mkString(":") == "15:5:0:0:0:0:0:0:0:0:0:0:0:1")
    assert(ByteArrayTool.byteArrayToBitSet(y) == x)

    x = BitSet(0,1,2,3,4,5,6,7,8)
    y = ByteArrayTool.bitSetToByteArray(x)
    assert(y.mkString(":") == "-1:1")
    assert(ByteArrayTool.byteArrayToBitSet(y) == x)

    x = BitSet(0)
    y = ByteArrayTool.bitSetToByteArray(x, 4)
    assert(y.mkString(":") == "1:0:0:0")
    assert(ByteArrayTool.byteArrayToBitSet(y) == x)
  }

}
