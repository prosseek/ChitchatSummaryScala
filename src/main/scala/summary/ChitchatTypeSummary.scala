package summary

class ChitchatTypeSummary extends Summary {
  // create
  override def create(map: Map[String, Any]): Unit = ???

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

  override def load(filePath: String): Any = ???

  override def save(filePath: String): Unit = ???

  override def schema: Option[Set[String]] = ???

  override def add(label: String, value: Any): Boolean = ???

  override def deserialize(ba: Array[Byte]): Map[String, Any] = ???
}