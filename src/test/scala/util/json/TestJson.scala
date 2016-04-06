package util.json

import org.scalatest.FunSuite

class TestJson extends FunSuite {

  val jsonString = """{
    "string": "James",
    "age": 10,
    "date": [10, 3, 17],
    "time": [12, 14],
    "lattitude": [1, 2, 3, 4],
    "longitude": [11, 12, 13, 14]
  }"""

  test ("simple parser") {
    val m = Json.parse(jsonString)
    assert(m("string") == "James")
    assert(m("age") == 10)
    assert(m("date") == Seq(10,3,17))
    assert(m("time") == Seq(12, 14))
    assert(m("lattitude") == Seq(1,2,3,4))
    assert(m("longitude") == Seq(11,12,13,14))
  }

  test ("simple json file read") {
    val m = Json.loadJson("./src/test/resources/jsonFiles/simple.json")
    assert(m("string") == "James")
    assert(m("age") == 10)
    assert(m("date") == Seq(10,3,17))
    assert(m("time") == Seq(12, 14))
    assert(m("lattitude") == Seq(1,2,3,4))
    assert(m("longitude") == Seq(11,12,13,14))
  }
}

