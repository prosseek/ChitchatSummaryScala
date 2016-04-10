package summary

import bloomierfilter.main.BloomierFilter
import chitchat.typetool.TypeInference

import scala.collection.mutable.{Map => MMap}
import java.lang.{String => JString}

import util.header.Header

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
  val name = "fbf"

  def apply(q:Int, input:Map[JString, Any], typeInference: TypeInference) = {
    val fbf = new FBFSummary(q = q, typeInference = typeInference)
    fbf.create(map = input)
    fbf
  }

  def apply(q:Int, filePath:JString, typeInference: TypeInference) = {
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

class FBFSummary(val q:Int, val force_m_multiple_by_four:Boolean = true, override val typeInference: TypeInference) extends ChitchatTypeSummary(typeInference){
  var bloomierFilter:BloomierFilter = null

  // create
  override def create(map: Map[JString, Any] = null) : Unit = {
    super.create(map)
    bloomierFilter = new BloomierFilter(inputAny = map, q = q,
      force_m_multiple_by_four=force_m_multiple_by_four, typeInference = typeInference)
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
    val header = Header(version=1).encode(FBFSummary.name).get
    header ++ serializedContent
  }

  def deserialize(ba: Array[SByte]) : Map[JString, Any] = {
    // todo: Duplication code
    // get the content from the byte array
    val version = _getVersion(ba)
    val nameId = _getName(ba)

    if (nameId != FBFSummary.name)
      throw new RuntimeException(s"Wrong summary type ${nameId}, expected ${FBFSummary.name}")
    val contentByteArray:Array[Byte] = _getContent(ba)

    // todo: This is not good, make a function and use the function to setup the internals
    bloomierFilter.byteArrayBloomierFilter.loadByteArray(contentByteArray)
    BloomierFilter.copyParameters(from = bloomierFilter.byteArrayBloomierFilter, to = bloomierFilter)

    // todo: We need to find a way to recover the Map
    var res =  MMap[JString, Any]()
    res.toMap
  }

  // I/O
  override def saveJson(filePath:JString, map:Map[JString, Any] = null) : Unit = {
    if (map.size > 0) {
      super.saveJson(filePath, map)
    }
  }
  override def loadJson(filePath:JString) = {
    super.loadJson(filePath)
  }

  /**
    * Save stores header + table
    *
    * @param filePath
    */
  def save(filePath:JString) : Unit = {
    bloomierFilter.save(filePath, serialize)
  }

  def load(filePath:JString) : Any = {
    // the create can be invoked before the instantiation
    if (bloomierFilter == null) {
      create()
    }
    val res = deserialize(_load(filePath))
  }

  // ID
  def name : JString = FBFSummary.name

  // Internal
  protected def serializedContent : Array[SByte] = {
    bloomierFilter.serialize
  }
}
