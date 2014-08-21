package core

import grapevineType._

import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/10/14.
 */
abstract class GrapevineSummary extends ContextSummary {
  protected val dataStructure = MMap[String, GrapevineType]()

  protected def set(key:String, t:Class[_], v:Any):Unit = {
    val gv = t.newInstance.asInstanceOf[GrapevineType]
    gv.set(v)
    dataStructure(key) = gv
  }

  protected def set(key:String, v:GrapevineType):Unit = {
    dataStructure(key) = v
  }

  def getValue(key:String) : Option[Any] = {
    if (dataStructure.contains(key)) Some(dataStructure(key).get)
    else None
  }

  def getMap() = {
    dataStructure.toMap
  }

  /**
   * 1. check if key has Grapevine type info
   * 2. check if value is integer/floating point number
   *
   * @param dict
   */
  override def create(dict: Map[String, Any]): Unit = {
    dict.foreach { case (key, v) =>
      if (v.isInstanceOf[GrapevineType]) {
        set(key, v.asInstanceOf[GrapevineType])
      }
      else {
        val t = GrapevineType.getTypeFromKey(key)
        if (t.nonEmpty) {
          set(key, t.get, v)
        } else {
          // t is empty which means the type info is not in the key
          val t = GrapevineType.getTypeFromValue(v)
          if (t.nonEmpty) set(key, t.get, v)
          else {
            throw new RuntimeException(s"No GrapevineType retrieved from key nor value:${key} - value:${v.getClass.toString}")
          }
        }
      }
    }
  }

  override def toString() = {
    val sb = new StringBuilder
    dataStructure.foreach { case (key, gvData) =>
        sb.append(s"${key} => ${gvData.get}: ${gvData.getTypeName}\n")
    }
    sb.toString
  }
}
