package util.gen

import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 9/10/14.
 */
class TestSummary extends FunSuite with BeforeAndAfter {
  var strs: Array[String] = _

  before {
    strs = Summary.getDictionaryStrings()
  }
  test ("random string") {
    (1 to 10).foreach { i =>
      val res = Summary.getRandomString(strs)
      assert(res.getClass().getName() == "java.lang.String")
    }
  }
  test ("random map") {
    val mp = Summary.getRandomMap(strs, 10)
    assert(mp.size == 10, s"${mp.mkString(":")}")
  }
  test ("get random m n summary") {
    val m = 10
    val res = Summary.getBFfromMNK(m = m, n = 2, k = 3, a = strs, t="byte")
    assert(res.getM == m)
    assert(res.getN() == 2)
  }
  test("bottom theory") {
    val m = 10
    val n = 3
    val k = 3
    assert(Summary.bottomTheory(m, n, k) == 0.29166666666666663)
  }
}
