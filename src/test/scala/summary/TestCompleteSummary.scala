package summary

import chitchat.typefactory.TypeDatabase
import org.scalatest.FunSuite

class TestCompleteSummary extends FunSuite {

  test ("simple") {
    val filePath = "./src/test/resources/jsonFiles/simple_example/simple.json"

    val ti = TypeDatabase()
    val completeSummary = new CompleteSummary(typeDatabase = ti)
    completeSummary.loadJson(filePath)
    val loadedJson = completeSummary.map

    val serialized = completeSummary.serialize
    assert(serialized.slice(0,4).mkString(":") == "81:10:-119:-118")
    completeSummary.save("./src/test/resources/jsonFiles/simple_example/simple_complete.bin")


    val orderedLabel = completeSummary.orderedLabel
    val deserialized = completeSummary.deserialize(serialized, orderedLabel)

    //    assert(deserialized == loadedJson)
    loadedJson foreach {
      case (key, value) => assert(value == deserialized(key))
    }
  }
}
