package app

import api.API
import org.scalatest.FunSuite

/**
  * Created by smcho on 12/9/16.
  */
class BICA2017 extends FunSuite {

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
    println(bf.size)

    val bf2 = API.create_json_summary_from_file("/Users/smcho/Desktop/json/test/ex1.json")
    println(bf2.size)
    val bf3 = API.create_fbf_summary_from_file("/Users/smcho/Desktop/json/test/ex1.json", Q = 1)
    println(bf3.size)
  }

  test("generate 100% and measure") {
    TestGenerator.testFor(testCount = 100, directory = "/Users/smcho/Desktop/json/test/100/", fileName = "schema")
    TestGenerator.testFor(testCount = 60, directory = "/Users/smcho/Desktop/json/test/60/", fileName = "schema")
    TestGenerator.testFor(testCount = 40, directory = "/Users/smcho/Desktop/json/test/60/", fileName = "non-schema")
    TestGenerator.testFor(testCount = 100, directory = "/Users/smcho/Desktop/json/test/0/", fileName = "non-schema")
  }
  test("analyzer test") {
    val filepath = "/Users/smcho/Desktop/json/test/0/non-schema1"
    JSONAnalyzer.getSizes(filePath = filepath)
  }
}
