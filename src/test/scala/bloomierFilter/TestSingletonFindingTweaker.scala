package bloomierFilter
import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 8/15/14.
 */
class TestSingletonFindingTweaker extends FunSuite with BeforeAndAfter {
  var map1 : Map[String, Any] = _
  before {
    map1 = Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4)
  }

  /*
    It shows that with m = 12 and with map1, we have singletons only setup

    key a/1
    List(10, 3, 1)
    key b/1
    List(4, 5, 4)
    key c/1
    List(10, 8, 11)
    key d/2
    List(4, 4, 6)

   */
  test ("Simple") {
    val h = new BloomierHasher(m = 12, k = 3, q = 0, hashSeed = 0)
    val tweaker = new SingletonFindingTweaker(map1, h)

    for (k <- map1.keys) {
      // find the key has k that is not occupied by others
      val res = tweaker.tweak(k)

      println(s"key ${k}/${res}")
      println(h.getNeighborhood(k))

      // not -1 means that they found singular, so delete them.
      if (res != SingletonFindingTweaker.NONSINGLETON) { // when the result is **singleton**
      }
    }
  }
}
