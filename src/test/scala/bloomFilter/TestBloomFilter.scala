package bloomFilter

import org.scalatest.FunSuite

/**
 * Created by smcho on 8/25/14.
 */
class TestBloomFilter extends FunSuite {
  val filePath = "experiment/data/simple_words.txt"
  val fullWordsPath = "experiment/data/words.txt"

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

  test ("checkInput test") {
    val m = 100000 * 20
    val k = 3
    val n = 235886

    val bf = new BloomFilter(fullWordsPath, m = m, k = k, seed=0)
    println(BloomFilter.getFalsePositiveProbability(m = m, k = k, n = n))
    assert(BloomFilter.checkInput(bf, "hello world"))
  }
}
