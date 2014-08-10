package dataType

/**
 * Created by smcho on 8/10/14.
 */
object GrapevineType extends Enumeration {
  type GrapevineType = Value
  val Integer,
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

  def getTypeFromValue(v:Object) : Option[GrapevineType] = {
    //println(v.getClass.toString())
    v.getClass.toString() match {
      case "int" | "class java.lang.Integer"  => Some(GrapevineType.Integer)
      case "double" | "class java.lang.Double" => Some(GrapevineType.FloatingPoint)
      case _ => None
    }
  }
}
