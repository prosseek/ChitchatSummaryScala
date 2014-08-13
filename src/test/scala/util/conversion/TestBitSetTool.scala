package util.conversion

import org.scalatest._
import scala.collection.BitSet

/**
 * BitSet uses a set of location to indicate a number.
 * 0 => 1 (as 0th location is set to 1)
 */
class TestBitSetTool extends FunSuite {
  test ("byteToBitSet test") {
    assert(BitSetTool.byteToBitSet(-1) == BitSet(Range(0,8):_*))
    assert(BitSetTool.byteToBitSet(0) == BitSet())
    assert(BitSetTool.byteToBitSet(1) == BitSet(0))
  }
  test ("byteToBitSet with shift test") {
    assert(BitSetTool.byteToBitSet(-1, 8) == BitSet(8,9,10,11,12,13,14,15))
    assert(BitSetTool.byteToBitSet(0) == BitSet())
    assert(BitSetTool.byteToBitSet(1, 8) == BitSet(8))
  }
  test("bitSetToByte") {
    assert(BitSetTool.bitSetToByte(BitSet(0,1,2,3)) == 15)
    assert(BitSetTool.bitSetToByte(BitSet(0,1,2,3,4,5,6,7)) == -1)
  }

  test ("shortToBitSet test") {
    assert(BitSetTool.shortToBitSet(-1) == BitSet(Range(0,16):_*))
    assert(BitSetTool.shortToBitSet(0) == BitSet())
    assert(BitSetTool.shortToBitSet(1) == BitSet(0))
  }

  test("bitSet to bytearray test") {
    var x = BitSet(0,1,2,3,8,10,104)
    var y = BitSetTool.bitSetToByteArray(x)
    assert(y.mkString(":") == "15:5:0:0:0:0:0:0:0:0:0:0:0:1")
    assert(BitSetTool.byteArrayToBitSet(y) == x)

    x = BitSet(0,1,2,3,4,5,6,7,8)
    y = BitSetTool.bitSetToByteArray(x)
    assert(y.mkString(":") == "-1:1")
    assert(BitSetTool.byteArrayToBitSet(y) == x)
  }
}
