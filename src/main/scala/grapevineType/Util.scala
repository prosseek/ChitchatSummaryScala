package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
object Util {
  /**
   * Grapevine type can be retrieved from the key string
   *
   * @param key
   * @return
   */
  def getTypeFromKey(key:String) : Option[Class[_]] = {
    val lowerKey = key.toLowerCase()
    val keyType = Map[String, Class[_]](
      "age" -> classOf[AgeType],
      "speed" -> classOf[SpeedType],
      "number" -> classOf[CountType],
      "latitude" -> classOf[LatitudeType],
      "longitude" -> classOf[LongitudeType],
      "date" -> classOf[DateType],
      "time" -> classOf[TimeType]
    )

    keyType.foreach { case(key, grapevineType) =>
      if (lowerKey.startsWith(key)) return Some(grapevineType)
    }
    None
  }

  /**
   * Get the Grapevine type from value
   * When the input is null, None is returned
   *   This is the preparation of the case when uses' give input with null
   * It allows only int/double/string, and raises an error with other inputs
   *
   * @param v
   * @return
   */
  def getTypeFromValue(v:Any) : Option[Class[_]] = {
    // when the value is null, it is Grapevine Null type data
    if (v == null) {
      None
    } else {
      //println(v.getClass.toString())
      v.getClass.toString() match {
        case "int" | "class java.lang.Integer" => Some(classOf[IntegerType])
        case "float" | "class java.lang.Float" | "double" | "class java.lang.Double" => Some(classOf[FloatType])
        case "class java.lang.String" => Some(classOf[StringType])
        case _ => throw new RuntimeException(s"ERROR: Cannot extract type info from value ${v.getClass}")
      }
    }
  }
}
