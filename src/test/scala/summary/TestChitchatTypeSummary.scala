package summary

import chitchat.typefactory.TypeDatabase
import org.scalatest.FunSuite
import util.json.Json

class CTS(override val typeDatabase: TypeDatabase) extends ChitchatTypeSummary(typeDatabase = typeDatabase) {override def schema: Option[Set[String]] = ???

  // protected APIs
  override protected def serializedContent: Array[Byte] = ???

  // modify
  override def update(label: String, value: Any): Boolean = ???

  // query
  override def get(label: String): Option[Any] = ???

  override def size: Int = ???

  // ID
  override def name: String = ???

  // transform
  override def serialize: Array[Byte] = ???

  override def delete(label: String): Boolean = ???

  override def add(label: String, value: Any): Boolean = ???

  override def deserialize(ba: Array[Byte]): Map[String, Any] = ???

  override def save(filePath: String): Unit = ???

  override def load(filePath: String): Any = ???
}

class TestChitchatTypeSummary extends FunSuite {
  test("create from map") {
    val ti = TypeDatabase()
    val cts = new CTS(ti)

    val m = Map[String, Any]("string" -> "hello", "float" -> 32.55f, "age"-> 10)

    cts.create(m)
    assert(cts.map == m)
  }

  test ("create from JSON") {
    val filePath = "./src/test/resources/jsonFiles/simple_example/simple.json"
    val ti = TypeDatabase()
    val cts = new CTS(ti)
    cts.loadJson(filePath)
    assert(cts.map.size == 6)
  }
}
