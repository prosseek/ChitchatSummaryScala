package api

import org.scalatest.FunSuite

class TestAPI extends FunSuite {
  test ("create from a string") {
    val str =
      """
        |{
        |  "string": "James",
        |  "age": 10,
        |  "date": [10, 3, 17],
        |  "time": [12, 14],
        |  "latitude": [1, 2, 3, 4],
        |  "longitude": [11, 12, 13, 14]
        |}
        |""".stripMargin

    val bf = API.create_fbf_summary(Q = 1, jsonSummary = str)

    assert(bf.get("string").get == "James")
    assert(bf.get("age").get == 10)
  }
}
