package summary

import bloomierfilter.main.BloomierFilter

import scala.collection.mutable.{Map => MMap}
import java.lang.{String => JString}

import filter.Filter
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
object FBFSummary extends ChitchatSummaryFactory[FBFSummary] {
  val name = "fbf"

  def make(q: Int, filter:Filter) =
    new FBFSummary(q = q, filter = filter)
}

/** Creates FBF summary
  *
  * ==== Bug history ====
  *  1. [2016/05/05]07:01PM the force_depth_count_1 was set to true, which is a bug as CBF should set this count to 1
  *
  * @param q
  * @param force_depth_count_1 // force there should be no ordering (for CBF)
  * @param filter
  */

class FBFSummary(val q:Int = 4*8,
                 val force_depth_count_1:Boolean = false,
                 val filter:Filter = null) extends ChitchatTypeSummary(filter.getTypeDatabase){
  var bloomierFilter:BloomierFilter = null

  // create
  override def create(map: Map[JString, Any] = null) : Unit = {
    super.create(map)
    bloomierFilter = new BloomierFilter(inputAny = map, q = q,
      force_depth_count_1 = force_depth_count_1,
      force_m_multiple_by_four=true,
      typeDatabase = typeDatabase)
  }

  // query
  def get(label:JString) : Option[Any] = {
    this.bloomierFilter.get(label)
  }

  def getFiltered(label:JString) : Option[Any] = {
    val res = get(label)
    if (res.isEmpty) return res
    if (filter.check(fbf = this, label = label)) res else None
  }

  def schema: Option[Set[JString]] = {
    if (this.map != null)
      Some(this.map.keys.toSet)
    else
      None
  }

  def size: Int = this.serialize.size

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
    if (bloomierFilter == null) create()
    deserialize(_load(filePath))
  }

  // ID
  def name : JString = FBFSummary.name

  // Internal
  protected def serializedContent : Array[SByte] = {
    bloomierFilter.serialize
  }
}
