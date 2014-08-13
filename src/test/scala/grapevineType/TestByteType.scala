package grapevineType

import org.scalatest._
import util.conversion.BitSetTool

import scala.collection.BitSet

/**
 * Created by smcho on 8/13/14.
 */
class TestByteType extends FunSuite {
  test ("return false for from/to byte array") {
    val t = new ByteType
    var bs = BitSet(0, 8) // 2^0 + 2^8 = 257
    var ba = BitSetTool.bitSetToByteArray(bs)
    assert(t.fromByteArray(ba) == false) // it has the bit set at higher bits than 7

    bs = BitSet(0, 1) // 2^0 + 2^1 = 3
    ba = BitSetTool.bitSetToByteArray(bs)
    assert(t.fromByteArray(ba)) // It's OK as there is no higher bits set to 1
  }
}
