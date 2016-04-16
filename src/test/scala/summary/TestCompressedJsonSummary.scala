package summary

import org.scalatest.FunSuite

class TestCompressedJsonSummary extends FunSuite {

  val filePath = "./src/test/resources/jsonFiles/simple_example/simple.json"
  val saveFilePath = "./src/test/resources/jsonFiles/simple_example/simple_result.json"
  val compressedBinaryFilePath = "./src/test/resources/jsonFiles/simple_example/simple_compressed.bin"
  test ("simple") {
    val js = CompressedJsonSummary(source = filePath)

    js.save(compressedBinaryFilePath)

    val json = new CompressedJsonSummary
    json.load(compressedBinaryFilePath)
    assert(json.get("string").get == "James")
    assert(json.get("age").get == 10)
    assert(json.get("date").get == Seq(10,3,17))
    assert(json.get("time").get == Seq(12, 14))
    assert(json.get("latitude").get == Seq(1,2,3,4))
    assert(json.get("longitude").get == Seq(11,12,13,14))

    assert(json.size == 106)
    assert(json.serialize.size == 90)
    assert(json.serialize.slice(0,5).mkString(":") == "17:120:-100:-85:86")

    assert(json.schema.get == Set("string", "age", "longitude", "latitude", "date", "time"))
  }
}
