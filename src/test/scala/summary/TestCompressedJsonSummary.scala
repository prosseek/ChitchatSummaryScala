package summary

import org.scalatest.FunSuite

class TestCompressedJsonSummary extends FunSuite {

  val filePath = "./src/test/resources/jsonFiles/simple.json"
  val saveFilePath = "./src/test/resources/jsonFiles/simple_result.json"
  test ("simple") {
    val json = CompresseJSonSummary(filePath)
    assert(json.get("string").get == "James")
    assert(json.get("age").get == 10)
    assert(json.get("date").get == Seq(10,3,17))
    assert(json.get("time").get == Seq(12, 14))
    assert(json.get("lattitude").get == Seq(1,2,3,4))
    assert(json.get("longitude").get == Seq(11,12,13,14))

    assert(json.size == 153)
    //assert(json.serialize.size == 98)
    //assert(json.serialize.slice(0,5).mkString(":") == "120:-100:-85:-26:82")

    assert(json.schema.get == Set("string", "age", "longitude", "lattitude", "date", "time"))
  }
}
