package core



import bloomierFilter.ByteArrayBloomierFilter
import grapevineType.BottomType._
import grapevineType._
import ontology.Relation
import util.conversion.{Joiner, Util}

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 8/16/14.
 */
class BloomierFilterSummary extends GrapevineSummary {
  var instance: GrapevineType = _
  var byteArrayBloomierFilter: ByteArrayBloomierFilter = _
  var k:Int = 3
  var q:Int = 4*8
  var maxTry:Int = 20
  var complete:Boolean = false
  var initM:Int = -1

  def createFromGrapevineMap(map: Map[String, Any], m:Int, k:Int, q:Int, maxTry:Int = 20, complete:Boolean = false): Unit = {
  }

  def create(map: Map[String, Any], m:Int, k:Int, q:Int, maxTry:Int = 20, initialSeed:Int = 0, complete:Boolean = false): Unit = {
    this.initM = m
    this.k = k
    this.q = q
    this.maxTry = maxTry
    this.complete = complete

    super.create(map) // any map to grapevineDataTypeMap
    val baMap = grapevineToByteArrayMap(super.getMap, Util.getByteSize(q))
    //println(!complete)
    byteArrayBloomierFilter = new ByteArrayBloomierFilter(map = baMap, initialM = m, k = k, q = q, initialSeed = initialSeed, maxTry = maxTry, complete = complete)
  }

  /**
   * Returns the size of the summary
   *
   * @return
   */
  override def getSize(): (Int, Int, Int) = {
    val s = byteArrayBloomierFilter.serialize()
    val z = util.compression.CompressorHelper.compress(s)
    (byteArrayBloomierFilter.getSize(), s.size, z.size)
  }

  def getDetailedSize() = {
    byteArrayBloomierFilter.getDetailedSize()
  }

  def getM() = {
    byteArrayBloomierFilter.getM()
  }

  def getN() = {
    byteArrayBloomierFilter.getN()
  }
  /**
   * Returns the value from the input key
   * The returned value can be null, so Option type is used.
   *
   * WARNING: get should be sent after the check method
   *
   * @param key
   * @return
   *
   */
  override def get(key: String): Any = {
    // [2014/08/21] bug
    // It should call the method, not the value
    instance.get()
  }

  override def check(key:String): BottomType = {
    check(key, useRelation = false)
  }

  def check(key: String, useRelation:Boolean = false): BottomType = {
    val j = new Joiner
    val value = j.join(byteArrayBloomierFilter, key) // Option[Array[Byte]]

    if (value.isEmpty) Bottom
    else {
      // get the data
      val t = GrapevineType.getTypeFromKey(key)
      var assumedType: Class[_] = if (t.isDefined) t.get else classOf[ByteType]
      instance = assumedType.newInstance.asInstanceOf[GrapevineType]
      val res = instance.fromByteArray(value.get)

      if (useRelation) {
        val r = new Relation(this)
        r.check(key)
      } else
        // if we don't use relation, we just return the OK/Bottom status 
        res
    }
  }

  override def load(filePath:String) :Unit = {
    super.load(filePath) // fill in the dataStructure
    val baMap = grapevineToByteArrayMap(super.getMap, Util.getByteSize(q))
    byteArrayBloomierFilter = new ByteArrayBloomierFilter(map = baMap, initialM = this.initM, k = k, q = q, initialSeed = 0, maxTry = maxTry, complete = complete)
  }

  /* NOT FINISHED */
  override def serialize(): Array[Byte] = {
    var ab = Array[Byte]()
    // get the contents

    dataStructure.foreach { case (key, value) =>
    }
    return ab
  }
}
