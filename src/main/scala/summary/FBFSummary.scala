package summary

//import bloomierFilter.ByteArrayBloomierFilter
//import grapevineType.BottomType._
//import grapevineType._
//import ontology.Relation
//import util.conversion.{Joiner, Util}

import bloomierfilter.main.BloomierFilter
import chitchat.typetool.TypeInference

import scala.collection.mutable.{Map => MMap}

class FBFSummary(val q:Int, override val typeInference: TypeInference) extends ChitchatTypeSummary(typeInference){
  val bloomierFilter:BloomierFilter = null

  // create
  override def create(map: Map[String, Any]) : Unit = {
    super.create(map)
    //bloomierFilter = new BloomierFilter()
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
  override def update(label:String, value:Any) : Boolean = {
    true
  }
  override def add(label:String, value:Any) : Boolean = {
    true
  }
  override def delete(label:String) : Boolean = {
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
