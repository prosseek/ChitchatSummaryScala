package simulation

import org.scalatest.{BeforeAndAfter, FunSuite}
import simulation.grapevineType.{Util, Simulation}

class S(m:Map[String, Int]) extends Simulation(m) {
  override def simulate(width: Int): Map[String, Double] = null
  override def simulateOverWidth(start: Int, end: Int): Map[Int, Map[String, Double]] = null
}

class TestSimulation extends FunSuite with BeforeAndAfter {
  var s:S = _
  before {
    s = new S(null)
  }

  test ("runAndAverage") {
    val f = {
      () => Map[String, Double]("a" -> 10, "b" -> 20)
    }
    val result = """a:10.0
               |b:20.0
               |""".stripMargin
    assert(Util.map2string(s.runAndAverage(10, f)) == result)
  }

  test ("insertBeforeString") {
    val input = "a\nb\nc\n"
    val result = ":a\n:b\n:c\n"

    assert(result == s.insertBeforeString(input, ":"))
  }
}
