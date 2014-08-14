package grapevineType

import org.scalatest._
import util.conversion.ByteArrayTool._
import BottomType._
import scala.collection.BitSet

/**
 * Created by smcho on 8/13/14.
 */
class TestByteType extends FunSuite {
  test ("return false for from/to byte array") {
    val t = new ByteType
    var bs = BitSet(0, 8) // 2^0 + 2^8 = 257
    var ba = bitSetToByteArray(bs)
    assert(t.fromByteArray(ba) == Computational) // it has the bit set at higher bits than 7

    bs = BitSet(0, 1) // 2^0 + 2^1 = 3
    ba = bitSetToByteArray(bs)
    assert(t.fromByteArray(ba) == NoError) // It's OK as there is no higher bits set to 1
  }
}
