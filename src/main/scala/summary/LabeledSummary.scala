package summary

import chitchat.typetool.TypeInference

object LabeledSummary {
  def name = "labeled"
}

class LabeledSummary(override val typeInference:TypeInference) extends ChitchatTypeSummary(typeInference = typeInference)
{
  // create
  override def create(map: Map[String, Any]) : Unit = {
    super.create(map)
  }

  // query
  def get(label:String) : Option[Any] = {
    null
  }
  def schema: Option[Set[String]] = {
    null
  }
  def size: Int = {
    0
  }

  // modify
  def update(label:String, value:Any) : Boolean = {
    true
  }
  def add(label:String, value:Any) : Boolean = {
    true
  }
  def delete(label:String) : Boolean = {
    true
  }

  // transform
  def serialize : Array[Byte] = {
    null
  }
  def deserialize(ba: Array[Byte]) : Map[String, Any] = {
    null
  }

  // I/O
  override def saveJson(filePath:String) : Unit = {
    null
  }
  def save(filePath:String) : Unit = {

  }
  def load(filePath:String) : Any = {
    null
  }

  // ID
  def name : String = LabeledSummary.name

  // Internal
  protected def serializedContent : Array[Byte] = {
    null
  }
}