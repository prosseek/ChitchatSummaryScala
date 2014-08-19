package util.io

import core.{LabeledSummary, GrapevineSummary}
import org.scalatest.{FunSuite, BeforeAndAfter}

/**
 * Created by smcho on 8/19/14.
 */
class TestString extends FunSuite with BeforeAndAfter {
  var testString : String = _
  var lbSummary: GrapevineSummary = _
  var map: Map[String, Any] = _
  before {
    lbSummary = new LabeledSummary

    //map =
    testString =
      """latitude -> (10, 10, 10, 10)
        |message -> Hello, world
        |time -> (11, 11)
        |date -> (2014, 10, 01)
        |age -> 12
        |level of athelete -> 9
        |athete -> 32
        |value_f -> 11.2
      """.stripMargin
  }

  test("parseTuple") {
    val line = " (10,  20,   30,   40) "
    assert(String.parseTuple(line) == (10,20,30,40))
  }

  test("parse line") {
    var line = "latitude -> (10, 10, 10, 10)"
    assert(String.parseLine(line) == ("latitude", (10,10,10,10)))
    line = "level of athelete -> 9"
    assert(String.parseLine(line) == ("level of athelete", 9))
  }

  test ("parse string to labeled summary") {
    val summary = String.parseSummary(testString)
    //println(summary.get("latitude"))
    println(summary.print)
  }
}
