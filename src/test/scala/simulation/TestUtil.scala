package simulation

import org.scalatest.FunSuite
import simulation.grapevineType.Util

/**
 * Created by smcho on 8/26/14.
 */
class TestUtil extends FunSuite {
  test ("map2string") {
    val m = Map[String, Double](
      "a" -> 10.0,
      "theory_a" -> 20.0,
      "b" -> 11.0,
      "theory_b" -> 21.0
    )
    val results = """a:10.0
                    |theory_a:20.0
                    |b:11.0
                    |theory_b:21.0
                    |""".stripMargin
    assert(Util.map2string(m) == results)
  }
  test ("getAverage") {
    val a = Map[String, Double]("a" -> 1, "b" -> 2)
    val b = Map[String, Double]("a" -> 3, "b" -> 4)
    val results = """a:2.0
                    |b:3.0
                    |""".stripMargin
    assert(Util.map2string(Util.getAverage(Array[Map[String, Double]](a,b))) == results)
  }
}
