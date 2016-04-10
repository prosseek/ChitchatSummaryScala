package summary

import bloomierfilter.main.BloomierFilter
import chitchat.typetool.TypeInference

import scala.collection.mutable.{Map => MMap}
import java.lang.{String => JString}
import scala.{Byte => SByte}

// todo: Check when creating from JSON, the JSON should be simple form, throw exception otherwise.
/**
  * ==== Creation of FBF summary ====
  *  1. From JSON file
  *    - FBF has all the information
  *  2. From bin file (stored) or Byte Array
  *    - FBF can recover the schema under some circumstances
  */
object FBFSummary {

  def apply(byteArray:Array[Byte]) = {

  }

  def apply(q:Int = 0, filePath:JString, typeInference: TypeInference) = {
    filePath match {
      case f if f.endsWith(".json") => {
        val fbf = new FBFSummary(q = q, typeInference = typeInference)
        fbf.loadJson(filePath)
        fbf
      }
      case f if f.endsWith(".bin") => {
        // we don't care about the q, as it will be overwritten from the byte array
        val fbf = new FBFSummary(q = q, typeInference = typeInference)
        fbf.load(filePath)
        fbf
      }
      case _ => throw new RuntimeException(s"Only json or bin can be used for setup ${filePath}")
    }
  }
}

/**
  *
  * @param q
  * @param typeInference
  */

class FBFSummary(val q:Int, override val typeInference: TypeInference) extends ChitchatTypeSummary(typeInference){
  var bloomierFilter:BloomierFilter = null

  // create
  override def create(map: Map[JString, Any]) : Unit = {
    super.create(map)
    bloomierFilter = new BloomierFilter(inputAny = map, q = q, typeInference = typeInference)
  }

  def create : Unit = {
    this.map.clear()
    // ??? how to setup the bloomier filter?
    bloomierFilter
  }

  // query
  def get(label:JString) : Option[Any] = {
    this.bloomierFilter.get(label)
  }

  def schema: Option[Set[JString]] = {
    if (this.map != null)
      Some(this.map.keys.toSet)
    else
      None
  }

  def size: Int = {
    this.serialize.size
  }

  // modify
  override def update(label:JString, value:Any) : Boolean = {
    if (this.map == null || !schema.contains(label)) return false
    this.map(label) = value
    create(this.map.toMap)
    true
  }
  override def add(label:JString, value:Any) : Boolean = {
    if (this.map == null || schema.contains(label)) return false
    this.map(label) = value
    create(this.map.toMap)
    true
  }
  override def delete(label:JString) : Boolean = {
    if (this.map == null || !schema.contains(label)) return false
    this.map -= label
    create(this.map.toMap)
    true
  }

  // transform
  def serialize : Array[SByte] = {
    null
  }

  def recoverBFTable(ba:Array[SByte]) = {

  }

  def deserialize(ba: Array[SByte]) : Map[JString, Any] = {
    null
  }

  // I/O
  override def saveJson(filePath:JString) : Unit = {
    null
  }

  /**
    * Save stores header + table
    *
    * @param filePath
    */
  def save(filePath:JString) : Unit = {

  }
  def load(filePath:JString) : Any = {
    val res = deserialize(_load(filePath))
    create(res)
  }

  // ID
  def name : JString = LabeledSummary.name

  // Internal
  protected def serializedContent : Array[SByte] = {
    null
  }
}
