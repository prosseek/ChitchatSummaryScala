package core



import bloomierFilter.ByteArrayBloomierFilter
import grapevineType.BottomType._
import grapevineType._
import util.conversion.{Joiner, Splitter, Util}

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 8/16/14.
 */
class BloomierFilterSummary extends GrapevineSummary {
  var instance: GrapevineType = _
  var byteArrayBloomierFilter: ByteArrayBloomierFilter = _

  def grapevineToByteArrayMap(inputMap:Map[String, GrapevineType], goalByteSize:Int)  = {
    val splitter = new Splitter

    inputMap.map { case (key, value) =>
        splitter.split(key, value.toByteArray(), goalByteSize)
    }.reduce { _ ++ _}
  }

  def create(map: Map[String, Any], m:Int, k:Int, q:Int, maxTry:Int = 5, complete:Boolean = false): Unit = {
    super.create(map) // any map to grapevineDataTypeMap
    val baMap = grapevineToByteArrayMap(super.getMap, Util.getByteSize(q))
    //println(!complete)
    byteArrayBloomierFilter = new ByteArrayBloomierFilter(map = baMap, initialM = m, k = k, q = q, initialSeed = 0, maxTry = maxTry, complete = complete)
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
    // [2014/08/21] bug
    // It should call the method, not the value
    instance.get()
  }

  override def check(key: String): BottomType = {
    val j = new Joiner
    val value = j.join(byteArrayBloomierFilter, key) // Option[Array[Byte]]

    if (value.isEmpty) Bottom
    else {
      val t = GrapevineType.getTypeFromKey(key)
      var assumedType: Class[_] = if (t.isDefined) t.get else classOf[ByteType]
      instance = assumedType.newInstance.asInstanceOf[GrapevineType]
      instance.fromByteArray(value.get)
    }
  }
}
