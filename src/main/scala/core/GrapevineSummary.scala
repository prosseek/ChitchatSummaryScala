package core

import grapevineType._

import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/10/14.
 */
abstract class GrapevineSummary extends ContextSummary {
  protected val dataStructure = MMap[String, GrapevineType]()

  protected def set(key:String, t:Class[_], v:Any) : Unit = {
    val gv = t.newInstance.asInstanceOf[GrapevineType]
    gv.set(v)
    dataStructure(key) = gv
  }

  def getTypeValue(key:String) : Option[GrapevineType] = {
    if (dataStructure.contains(key)) Some(dataStructure(key))
    else None
  }

  /**
   * 1. check if key has Grapevine type info
   * 2. check if value is integer/floating point number
   *
   * @param dict
   */
  override def create(dict: Map[String, Any]): Unit = {
    dict.foreach { case (key, v) =>
        val t = Util.getTypeFromKey(key)
        if (t.nonEmpty) {
          set(key, t.get, v)
        } else { // t is empty which means the type info is not in the key
          val t = Util.getTypeFromValue(v)
          if (t.nonEmpty) set(key, t.get, v)
          else {
            throw new RuntimeException(s"No GrapevineType retrieved from key:${key} - value:${v.getClass.toString}")
          }
        }
    }
  }
}
