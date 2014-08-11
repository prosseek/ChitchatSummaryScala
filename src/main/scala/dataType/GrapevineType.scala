package dataType

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
//      FixedPoint,
//      Latitude,
//      Longitude,
//      Altitude,
//      Date,
//      Time,
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
    if (lowerKey.startsWith("age")) {
      Some(GrapevineType.Age)
    }
    else if (lowerKey.startsWith("speed")) {
      Some(GrapevineType.Speed)
    }
    else if (lowerKey.startsWith("number")) {
      Some(GrapevineType.Count)
    }
    else {
      None
    }
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
        case _ => None
      }
    }
  }
}
