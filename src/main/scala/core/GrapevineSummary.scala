package core

import dataType.GrapevineType._

import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/10/14.
 */
abstract class GrapevineSummary extends ContextSummary {
  protected val dataStructure = MMap[String, Tuple2[GrapevineType, AnyVal]]()

  protected def set(key:String, t:GrapevineType, v:AnyVal) : Unit = {
    dataStructure(key) = Tuple2[GrapevineType, AnyVal](t, v)
  }

  override def get(key:String) : Option[Tuple2[GrapevineType, AnyVal]] = {
    if (dataStructure.contains(key)) Some(dataStructure(key))
    else None
  }

  override def create(dict: Map[String, AnyVal]): Unit = {
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
