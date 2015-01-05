// http://stackoverflow.com/questions/24417103/the-performance-of-java-scalas-deflator-for-compressing-bitset
package util.compression

import org.scalatest.FunSuite
import util.conversion.ByteArrayTool
import scala.collection.immutable.BitSet

/**
 * Created by smcho on 1/4/15.
 */
class TestCompressorHelper extends FunSuite {
  test("gzip test") {
//    assert(BitSetTool.byteToBitSet(-1) == BitSet(Range(0, 8): _*))
    val bs = BitSet(100)
    val ba = ByteArrayTool.bitSetToByteArray(bs)

    val z = CompressorHelper.compress(ba)
    assert(ba.size == 13)
    assert("0:0:0:0:0:0:0:0:0:0:0:0:16" == ba.mkString(":"))
    assert(12 == z.size)
    assert("120:-100:99:96:64:0:1:0:0:29:0:17" == z.mkString(":"))
  }
}