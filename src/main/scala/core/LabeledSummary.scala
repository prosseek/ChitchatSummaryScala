package core

/**
 * Created by smcho on 8/10/14.
 */
class LabeledSummary extends GrapevineSummary {

  //  override def get(key: String): Option[Object] = {
  //    Some[Integer](10)
  //  }
  //  override def create(dict: Map[String, Object], wholeDict: Map[String, Object]): Unit = {
  //    dict.foreach { case (key, value) =>
  //    }
  //  }
  //
  //  override def getSize(): Int = 100
  //}
  override def getSize(): Int = 10
  override def get(key: String): Option[Any] = {
    val r = getTypeValue(key)
    if (r.nonEmpty)
    //MMap[String, Tuple2[GrapevineType, Object]]()
      Some(r.get)
    else
      None
  }
}