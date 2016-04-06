package summary

object JSonSummary {
  def apply(map: Map[String, Any]) = {
    val summary = new JSonSummary
    summary.create(map)
    summary
  }
}

/**
  * Created by smcho on 4/5/16.
  */
class JSonSummary extends ChitchatSummary {

  // create
  override def create(map: Map[String, Any]): Unit = ???
  override def create(string: String): Unit = ???

  // get/set
  override def get(label: String): Any = ???
  override def set(label: String, value: Any): Unit = ???

  // get information
  override def getSchema: Set[String] = ???

  override def size: Int = ???

  // transform
  override def serialize: Array[Byte] = ???

  // I/O
  override def load(filePath: String): Array[Byte] = ???
  override def save(filePath: String, content: Array[Byte]): Unit = ???

}
