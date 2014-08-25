package bloomFilter

import org.scalatest.FunSuite

/**
 * Created by smcho on 8/25/14.
 */
class TestBloomFilter extends FunSuite {
  val filePath = "experiment/data/simple_words.txt"

  test ("getSet") {
    val keys = BloomFilter.getSet(filePath)
    assert(keys.contains("abaff"))
    assert(keys.contains("xyz") == false)
  }

  test ("fp") {
    val m = 100000
    val k = 5
    val n = 235886

    (1 until 50).foreach { i =>
      println(s"${i} - ${BloomFilter.getFalsePositiveProbability(m = m * i, k = k, n = n)}")
    }
  }

  test ("simple") {
    val bf = new BloomFilter(filePath, m = 100000, k = 3, seed=0)
    assert(bf.get("abacus"))
    assert(bf.get("Abadite"))
    assert(bf.get("abaff"))
    assert(bf.get("abaft"))
    assert(bf.get("hello") == false)
  }
}
