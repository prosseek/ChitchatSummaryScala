package summary

import bloomierfilter.main.BloomierFilter
import chitchat.typefactory.TypeDatabase
import org.scalatest.FunSuite
import filter._

class TestCBFSummary extends FunSuite {

  test ("Simple") {
    val simpleFile = "./src/test/resources/jsonFiles/simple/simple.json"
    val saveFilePath = "./src/test/resources/jsonFiles/simple/simple_cbf_q4.bin"
    val ti = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(ti)
    val fbf = CBFSummary(source=simpleFile, q = 1*8, filter = filter)
    fbf.save(saveFilePath)
  }

  test ("load") {
    val saveFilePath = "./src/test/resources/jsonFiles/simple/simple_cbf_q4.bin"
    val ti = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(ti)
    val fbf = CBFSummary(source=saveFilePath, q = 0, filter = filter)

    assert(fbf.get("string").get == "James")
    assert(fbf.get("age").get == 10)
  }

  test ("byte array") {
    val ti = TypeDatabase()
    val m = Map[String, Any]("age" -> 10, "string" -> "James")
    val filter = ChitchatFilter(ti)
    val bf = CBFSummary(input = m, q = 4*8, filter = filter)

    assert(bf.get("string").get == "James")
    assert(bf.get("age").get == 10)
  }
}
