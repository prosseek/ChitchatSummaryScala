package summary

import bloomierfilter.main.BloomierFilter
import chitchat.typefactory.TypeDatabase
import filter.ChitchatFilter
import org.scalatest.FunSuite

class TestFBFSummary extends FunSuite {

  test ("Simple") {
    val simpleFile = "./src/test/resources/jsonFiles/simple/simple.json"
    val saveFilePath = "./src/test/resources/jsonFiles/simple/simple_fbf_q4.bin"
    val typeInference = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(typeInference)
    val fbf = FBFSummary(source=simpleFile, q = 1*8, filter = filter)
    fbf.save(saveFilePath)
  }

  test ("load") {
    val saveFilePath = "./src/test/resources/jsonFiles/simple/simple_fbf_q4.bin"
    val typeInference = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(typeInference)
    val fbf = FBFSummary(source=saveFilePath, q = 0, filter = filter)

    assert(fbf.get("string").get == "James")
    assert(fbf.get("age").get == 10)
  }

  test ("byte array") {
    val ti = TypeDatabase()
    val m = Map[String, Any]("age" -> 10, "string" -> "James")
    val filter = ChitchatFilter(ti)
    val bf = FBFSummary(input = m, q = 4*8, filter = filter)

    assert(bf.get("string").get == "James")
    assert(bf.get("age").get == 10)
  }

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

    val ti = TypeDatabase()
    val filter = ChitchatFilter(ti)
    val bf = FBFSummary(q = 1*8, source=str, filter=filter)

    assert(bf.get("string").get == "James")
    assert(bf.get("age").get == 10)
  }
}
