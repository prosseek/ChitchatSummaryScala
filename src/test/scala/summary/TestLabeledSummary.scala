package summary

import chitchat.typefactory.TypeDatabase
import org.scalatest.FunSuite

class TestLabeledSummary extends FunSuite {

  test ("simple") {
    val filePath = "./src/test/resources/jsonFiles/simple_example/simple.json"

    val typeInference = TypeDatabase()
    val labeled = new LabeledSummary(typeDatabase = typeInference)
    labeled.loadJson(filePath)
    val loadedJson = labeled.map

    val serialized = labeled.serialize
    assert(serialized.slice(0,4).mkString(":") == "33:4:100:97")
    labeled.save("./src/test/resources/jsonFiles/simple_example/simple_labeled.bin")
    val deserialized = labeled.deserialize(serialized)

//    assert(deserialized == loadedJson)
    loadedJson foreach {
      case (key, value) => assert(value == deserialized(key))
    }
  }
}
