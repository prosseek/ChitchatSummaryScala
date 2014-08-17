package core

import util.conversion.Util

import scala.collection.mutable.{Map => MMap}

import bloomierFilter.ByteArrayBloomierFilter

import grapevineType.BottomType._
import grapevineType.{ByteType, GrapevineType}

/**
 * Created by smcho on 8/16/14.
 */
class BloomierFilterSummary extends GrapevineSummary {
  var instance: GrapevineType = _
  var byteArrayBloomierFilter: ByteArrayBloomierFilter = _

  def grapevineToByteArrayMap(map:Map[String, GrapevineType], goalByteSize:Int)  = {
    val res = MMap[String, Array[Byte]]()
    map.foreach { case (key, value) =>
        res(key) = value.toByteArray(goalSize = goalByteSize)
    }
    res.toMap
  }

  def create(map: Map[String, Any], m:Int, k:Int, q:Int, maxTry:Int = 5, complete:Boolean = false): Unit = {
    super.create(map) // any map to grapevineDataTypeMap
    val baMap = grapevineToByteArrayMap(super.getMap, Util.getByteSize(q))
    byteArrayBloomierFilter = new ByteArrayBloomierFilter(map = baMap, initialM = m, k = k, q = q, initialSeed = 0, maxTry = maxTry, allowOrder = !complete)
  }

  /**
   * Returns the size of the summary
   *
   * @return
   */
  override def getSize(): Int = {
    byteArrayBloomierFilter.getSize()
  }

  def getM() = {
    byteArrayBloomierFilter.getM()
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
    // I'm given a byte array, how can I get the type back?
    // get(keyInput: String) : Option[Array[Byte]]
    val value = byteArrayBloomierFilter.get(key)
    if(value.isEmpty) {
      throw new RuntimeException(s"key(${key}) returns None from get, you should run check() first before using get in BloomierFilter")
    }
    instance.value
  }

  override def check(key: String): BottomType = {
    val value = byteArrayBloomierFilter.get(key)
    if (value.isEmpty) Bottom
    else {
      val t = GrapevineType.getTypeFromKey(key)
      var assumedType: Class[_] = if (t.isDefined) t.get else classOf[ByteType]
      instance = assumedType.newInstance.asInstanceOf[GrapevineType]
      instance.fromByteArray(value.get)
    }
  }
}
