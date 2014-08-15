package core

import grapevineType.BottomType._

/**
 * Created by smcho on 8/10/14.
 */
class LabeledSummary extends GrapevineSummary {
  override def getSize(): Int = {
    1
  }

  override def get(key: String): Option[Any] = {
    val r = getValue(key)
    if (r.nonEmpty)
    //MMap[String, Tuple2[GrapevineType, Object]]()
      Some(r.get)
    else
      None
  }

  override def check(key: String): BottomType = {
    if (getValue(key).isEmpty) Buttom
    else NoError
  }
}