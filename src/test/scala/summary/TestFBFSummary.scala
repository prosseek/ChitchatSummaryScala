package summary

import bloomierfilter.main.BloomierFilter
import chitchat.typetool.TypeInference
import org.scalatest.FunSuite

class TestFBFSummary extends FunSuite {

  test ("Simple") {
    val simpleFile = "./src/test/resources/jsonFiles/simple/simple.json"
    val saveFilePath = "./src/test/resources/jsonFiles/simple/simple_fbf_q4.bin"
    val typeInference = TypeInference() // () to use apply
    val fbf = FBFSummary(filePath=simpleFile, q = 1*8, typeInference = typeInference)
    fbf.save(saveFilePath)
  }

  test ("load") {
    val saveFilePath = "./src/test/resources/jsonFiles/simple/simple_fbf_q4.bin"
    val typeInference = TypeInference() // () to use apply
    val fbf = FBFSummary(filePath=saveFilePath, q = 0, typeInference = typeInference)

    assert(fbf.get("string").get == "James")
    assert(fbf.get("age").get == 10)
  }

  test ("byte array") {
    val ti = TypeInference()
    val m = Map[String, Any]("age" -> 10, "string" -> "James")
    val bf = FBFSummary(input = m, q = 4*8, typeInference = ti)

    assert(bf.get("string").get == "James")
    assert(bf.get("age").get == 10)
  }
}
