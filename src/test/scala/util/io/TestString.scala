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

    map = Map[String, Any](
      "latitude" -> (10, 10, 10, 10),
      "message" -> "Hello, world",
      "time" -> (11, 11),
      "date" -> (2014, 10, 01),
      "age" -> 12,
      "level of athelete" -> 9,
      "athelete" -> 32,
      "value_f" -> 11.2
    )
    testString =
      """latitude -> (10, 10, 10, 10)
        |message -> Hello, world
        |time -> (11, 11)
        |date -> (2014, 10, 01)
        |age -> 12
        |level of athelete -> 9
        |athelete -> 32
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
    def same(summary:LabeledSummary, map:Map[String, Any]) = {
      summary.getMap.keys.foreach { key =>
        //println(key)
        if (map(key) != summary.get(key)) {
          var val1:Float = 0.0F
          var val2:Float = 0.0F

          if (map(key).isInstanceOf[Float]) val1 = map(key).asInstanceOf[Float]
          else if (map(key).isInstanceOf[Double]) val1 = map(key).asInstanceOf[Double].toFloat
          if (summary.get(key).isInstanceOf[Float]) val2 = summary.get(key).asInstanceOf[Float]
          else if (summary.get(key).isInstanceOf[Double]) val2 = summary.get(key).asInstanceOf[Double].toFloat

          assert(math.abs(val1 - val2) < 0.0000001)
        }
      }
    }

    val summary = String.parseSummary(testString)
    //println(summary.get("latitude"))
    println(summary.toString)
    same(summary, map)
  }
}
