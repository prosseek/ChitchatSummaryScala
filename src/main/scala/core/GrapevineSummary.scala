package core

import dataType.GrapevineType._

import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/10/14.
 */
abstract class GrapevineSummary extends ContextSummary {
  protected val dataStructure = MMap[String, Tuple2[GrapevineType, Any]]()

  protected def set(key:String, t:GrapevineType, v:Any) : Unit = {
    dataStructure(key) = Tuple2[GrapevineType, Any](t, v)
  }

  def getTypeValue(key:String) : Option[Tuple2[GrapevineType, Any]] = {
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
        val t = getTypeFromKey(key)
        if (t.nonEmpty) {
          set(key, t.get, v)
        } else { // t is empty which means the type info is not in the key
          val t = getTypeFromValue(v)
          if (t.nonEmpty) set(key, t.get, v)
          else {
            throw new RuntimeException(s"No GrapevineType retrieved from key:${key} - value:${v.getClass.toString}")
          }
        }
    }
  }
}
