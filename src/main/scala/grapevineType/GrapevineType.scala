package grapevineType

/**
 * Some of the types are identified from key and some are from value
 */
object GrapevineType extends Enumeration {
  type GrapevineType = Value
  val
      Null,
      Integer,
      Count,
      FloatingPoint,
      String,
//      FixedPoint,
      Latitude,
      Longitude,
      Date,
      Time,
      Speed,
      Age
  = Value

  /**
   * Grapevine type can be retrieved from the key string
   *
   * @param key
   * @return
   */
  def getTypeFromKey(key:String) : Option[GrapevineType] = {
    val lowerKey = key.toLowerCase()
    val keyType = Map[String, GrapevineType](
        "age" -> GrapevineType.Age,
        "speed" -> GrapevineType.Speed,
        "number" -> GrapevineType.Count,
        "latitude" -> GrapevineType.Latitude,
        "longitude" -> GrapevineType.Longitude,
        "date" -> GrapevineType.Date,
        "time" -> GrapevineType.Time
    )

    keyType.foreach { case(key, grapevineType) =>
      if (lowerKey.startsWith(key)) return Some(grapevineType)
    }
    None
  }

  /**
   * Get the Grapevine type from value
   * @param v
   * @return
   */
  def getTypeFromValue(v:Any) : Option[GrapevineType] = {
    // when the value is null, it is Grapevine Null type data
    if (v == null) {
      Some(GrapevineType.Null)
    } else {
      //println(v.getClass.toString())
      v.getClass.toString() match {
        case "int" | "class java.lang.Integer" => Some(GrapevineType.Integer)
        case "double" | "class java.lang.Double" => Some(GrapevineType.FloatingPoint)
        case "class java.lang.String" => Some(GrapevineType.String)
        case _ => None
      }
    }
  }
}
