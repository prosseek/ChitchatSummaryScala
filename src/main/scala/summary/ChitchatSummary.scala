package summary

abstract class ChitchatSummary {
  // create
  def create(map: Map[String, Any]) : Unit
  def create(string:String) : Unit

  // get/set
  def get(label:String) : Option[Any]
  // def set(label:String, value:Any) : Unit

  // get information
  def getSchema: Set[String]
  def size: Int

  // transform
  def serialize : Array[Byte]

  // I/O
  def save(filePath:String, content:Any = null) : Unit
  def load(filePath:String) : Any
}