package core

import dataType.GrapevineType.GrapevineType
import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/10/14.
 */
trait GrapevineSummary {
  protected val dataStructure = MMap[String, Tuple2[GrapevineType, Object]]()

  protected def set(key:String, t:GrapevineType, v:Object) : Unit = {
    dataStructure(key) = Tuple2[GrapevineType, Object](t, v)
  }

//  override def create(dict: Map[String, Object]): Unit = {
//    dict.foreach { case (key, v) =>
//        val r = getTypeFromKey(key)
//        if (r.isEmpty) {
//        } else {
//          r.get
//        }
//    }
//  }
}
