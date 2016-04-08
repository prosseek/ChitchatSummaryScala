package summary

import org.scalatest.FunSuite
import util.json.Json
import util.types.TypeInference

class CTS(override val typeInference: TypeInference) extends ChitchatTypeSummary(typeInference = typeInference) {// query
  override def schema: Option[Set[String]] = ???

  // protected APIs
  override protected def serializedContent: Array[Byte] = ???

  // modify
  override def update(label: String, value: Any): Boolean = ???

  // I/O
  override def saveJson(filePath: String): Unit = ???

  // query
  override def get(label: String): Option[Any] = ???

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
    val ti = TypeInference()
    val cts = new CTS(ti)

  }
}
