package summary

import org.scalatest.FunSuite

class TestJsonSummary extends FunSuite {
  val baFilePath = "./src/test/resources/jsonFiles/simple.bin"
  val filePath = "./src/test/resources/jsonFiles/simple.json"
  val saveFilePath = "./src/test/resources/jsonFiles/simple_result.json"
  test ("simple access test") {
    val json = JsonSummary(filePath)
    assert(json.get("string").get == "James")
    assert(json.get("age").get == 10)
    assert(json.get("date").get == Seq(10,3,17))
    assert(json.get("time").get == Seq(12, 14))
    assert(json.get("lattitude").get == Seq(1,2,3,4))
    assert(json.get("longitude").get == Seq(11,12,13,14))

    assert(json.size == 153)
    assert(json.serialize.size == 154)
    assert(json.serialize.slice(0,5).mkString(":") == "0:123:10:32:32")

    assert(json.schema.get == Set("string", "age", "longitude", "lattitude", "date", "time"))
  }

  test ("simple load") {
    // create the json and save it in binary
    val js = JsonSummary(filePath)
    js.save(baFilePath)
    // load the binary
    val json = new JsonSummary
    json.load(baFilePath)

    assert(json.get("string").get == "James")
    assert(json.get("age").get == 10)
    assert(json.get("date").get == Seq(10,3,17))
    assert(json.get("time").get == Seq(12, 14))
    assert(json.get("lattitude").get == Seq(1,2,3,4))
    assert(json.get("longitude").get == Seq(11,12,13,14))
  }

  test ("simple json save") {
    val json = new JsonSummary
    json.loadJson(filePath)
    json.saveJson(saveFilePath)

    val json2 = JsonSummary(saveFilePath)
    assert(json2.get("string").get == "James")
    assert(json2.get("age").get == 10)
    assert(json2.get("date").get == Seq(10,3,17))
    assert(json2.get("time").get == Seq(12, 14))
    assert(json2.get("lattitude").get == Seq(1,2,3,4))
    assert(json2.get("longitude").get == Seq(11,12,13,14))
  }

  test ("simple add ") {
    val json = JsonSummary(filePath)
    assert(json.add("hello", 1234))
    assert(json.get("hello").get == 1234)
  }

  test ("simple updage ") {
    val json = JsonSummary(filePath)
    assert(json.update("age", 11))
    assert(json.get("age").get == 11)
  }

  test ("simple delete ") {
    val json = JsonSummary(filePath)
    assert(json.delete("age"))
    assert(!json.schema.contains("age"))
  }
}
