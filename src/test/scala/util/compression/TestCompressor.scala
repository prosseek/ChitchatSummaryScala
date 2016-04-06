package util.compression

import org.scalatest.FunSuite

class TestCompressor extends FunSuite {
  test ("compress") {
    val input = "Hello, world. Hello, world. Hello, world"
    assert(input.size == 40)
    val compressed = Compressor.compress(input.getBytes)
    assert(compressed.size == 25)
    val decompressed = Compressor.decompress(compressed)
    assert(input == new String(decompressed))
  }
}