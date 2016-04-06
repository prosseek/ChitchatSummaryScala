package summary

import org.scalatest.FunSuite

class TestJsonSummary extends FunSuite {
  val filePath = "./src/test/resources/jsonFiles/simple.json"
  val saveFilePath = "./src/test/resources/jsonFiles/simple_result.json"
  test ("simple") {
    val json = JSonSummary(filePath)
    assert(json.get("string").get == "James")
    assert(json.get("age").get == 10)
    assert(json.get("date").get == Seq(10,3,17))
    assert(json.get("time").get == Seq(12, 14))
    assert(json.get("lattitude").get == Seq(1,2,3,4))
    assert(json.get("longitude").get == Seq(11,12,13,14))

    assert(json.size == 153)
    assert(json.serialize.size == 153)
    assert(json.serialize.slice(0,5).mkString(":") == "123:10:32:32:32")

    assert(json.getSchema == Set("string", "age", "longitude", "lattitude", "date", "time"))
  }

  test ("simple load") {
    val json = new JSonSummary
    json.load(filePath)
    assert(json.get("string").get == "James")
    assert(json.get("age").get == 10)
    assert(json.get("date").get == Seq(10,3,17))
    assert(json.get("time").get == Seq(12, 14))
    assert(json.get("lattitude").get == Seq(1,2,3,4))
    assert(json.get("longitude").get == Seq(11,12,13,14))
  }

  test ("simple save") {
    val json = new JSonSummary
    json.load(filePath)
    json.save(saveFilePath)

    val json2 = JSonSummary(saveFilePath)
    assert(json2.get("string").get == "James")
    assert(json2.get("age").get == 10)
    assert(json2.get("date").get == Seq(10,3,17))
    assert(json2.get("time").get == Seq(12, 14))
    assert(json2.get("lattitude").get == Seq(1,2,3,4))
    assert(json2.get("longitude").get == Seq(11,12,13,14))
  }
}
