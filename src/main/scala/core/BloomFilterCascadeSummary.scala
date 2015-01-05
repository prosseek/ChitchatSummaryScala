package core

import bfc.BloomFilterCascade
import grapevineType.BottomType._
import grapevineType._
import util.conversion.{ByteArrayTool, Util}

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 8/16/14.
 */
class BloomFilterCascadeSummary extends GrapevineSummary {

  var instance: GrapevineType = _
  var bloomFilterCascade: BloomFilterCascade = _
  var m:List[Int] = _
  var k:List[Int] = _
  var r:Int = _

  def create(map: Map[String, Any], m:List[Int], k:List[Int], r:Int, maxTry:Int = 20, initialSeed:Int = 0, complete:Boolean = false): Unit = {
    this.k = k
    this.r = r
    this.m = m

    super.create(map) // any map to grapevineDataTypeMap
    val baMap = grapevineToByteArrayMap(super.getMap, Util.getByteSize(r))
    //println(!complete)
    bloomFilterCascade = new BloomFilterCascade() // map = baMap, k = k, q = q, initialSeed = initialSeed, maxTry = maxTry, complete = complete)
    bloomFilterCascade.create(dictionary = baMap, m = m, k = k, r = r)
  }

  /**
   * Returns the size of the summary
   *
   * @return
   */
  def getSize(debug:Boolean = false): Int = {
    bloomFilterCascade.getSize(debug)
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

  override def check(key: String): BottomType = {
    val value = bloomFilterCascade.get(key)
    if (value.isEmpty) Bottom
    else {
      // value is Some(BitSet)
      val byteArray = ByteArrayTool.bitSetToByteArray(value.get)
      // get the data
      val t = GrapevineType.getTypeFromKey(key)
      var assumedType: Class[_] = if (t.isDefined) t.get else classOf[ByteType]
      instance = assumedType.newInstance.asInstanceOf[GrapevineType]
      val res = instance.fromByteArray(byteArray) // value.get)
      res
    }
  }

  override def load(filePath:String) :Unit = {
    super.load(filePath) // fill in the dataStructure
    val baMap = grapevineToByteArrayMap(super.getMap, Util.getByteSize(r))
    //byteArrayBloomierFilter = new ByteArrayBloomierFilter(map = baMap, initialM = this.initM, k = k, q = q, initialSeed = 0, maxTry = maxTry, complete = complete)
  }

  /**
   * Returns the size of the summary
   *
   * @return
   */
  override def getSize() = (getSize(false), getSize(false), getSize(false))
  override def serialize() = {
    Array[Byte]()
  }
}

