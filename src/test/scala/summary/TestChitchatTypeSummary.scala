package summary

import org.scalatest.FunSuite
import util.json.Json
import util.types.TypeInference

class CTS extends ChitchatTypeSummary {// query
override def get(label: String): Option[Any] = ???
  // protected APIs
  override protected def serializedContent: Array[Byte] = ???
  // modify
  override def update(label: String, value: Any): Boolean = ???
  // I/O
  override def saveJson(filePath: String): Unit = ???
  override def size: Int = ???
  override def loadJson(filePath: String): Any = ???
  // ID
  override def name: String = ???
  override def delete(label: String): Boolean = ???
  // transform
  override def serialize: Array[Byte] = ???
  override def add(label: String, value: Any): Boolean = ???
  override def deserialize(ba: Array[Byte]): Map[String, Any] = ???
}

class TestChitchatTypeSummary extends FunSuite {
  test("simple") {
    val filePath = "./src/test/resources/jsonFiles/simple_example/simple.json"
    val cts = new CTS
    val typeInference = TypeInference("")
    val map = Json.loadJson(filePath)
    val res = cts.anyToByteArray(inputMap = map, typeInference)
    println(res.mkString(":"))
  }
}
