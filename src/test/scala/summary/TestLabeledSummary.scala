package summary

import org.scalatest.FunSuite
import util.types.TypeInference

class TestLabeledSummary extends FunSuite {

  test ("simple") {
    val filePath = "./src/test/resources/jsonFiles/simple_example/simple.json"

    val typeInference = TypeInference()
    val labeled = new LabeledSummary(typeInference = typeInference)
    labeled.loadJson(filePath)
   // val serialized = labeled.serialize
   // println(serialized.mkString(":"))
  }

}
