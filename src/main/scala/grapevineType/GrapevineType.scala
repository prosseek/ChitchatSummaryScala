package grapevineType

import grapevineType.BottomType._

/**
 * Some of the types are identified from key and some are from value
 */
abstract class GrapevineType {
  var value: Any = _

  def getBytes(bits:List[Int]) :Int = {
    val size = bits.sum
    getBytes(size)
  }
  def getBytes(size:Int) = {
    if (size % 8 == 0) size/8
    else size/8 + 1
  }

  def set(value:Any) : Unit
  def get() : Any = {
    throw new RuntimeException("ERROR: You should implement the get() method")
  }

  def toByteArray(goalSize:Int = -1) : Array[Byte]
  def fromByteArray(b: Array[Byte], byteSize:Int, f:Array[Byte] => Any = null): BottomType = {
    val result = getValueByteArray(b, byteSize)
    if (result.isEmpty) {
      Computational
    } else {
      //this.value = ByteArrayTool.byteArrayToByte(result.get)
      if (f != null)
          this.value = f(result.get)
      NoError
    }
  }
  def fromByteArray(b: Array[Byte]): BottomType

  /**
   * This just checks if the additional bytes are all zero
   * [1][2][3][4]
   * V  0  0  0  <-- For byte data, all the rest bits should be zero if correctly encoded
   * tail--head--
   */
  def getValueByteArray(b: Array[Byte], byteSize:Int): Option[Array[Byte]] = {
    val (tail, head) = b.splitAt(byteSize) // - byteSize)
    if (head.forall(_ == 0)) {
      Some(tail)
    } else {
      None
    }
  }

  def getId() : Int
  def getSize() : Int
  def getTypeName() : String
}

/**
 * Created by smcho on 8/11/14.
 */
object GrapevineType {
  /**
   * Grapevine type can be retrieved from the key string
   *
   * @param key
   * @return
   */
  def getTypeFromKey(key:String) : Option[Class[_]] = {
    val lowerKey = key.toLowerCase()
    val keyType = Map[String, Class[_]](
      "ttl" -> classOf[UnsignedShortType],
      "age" -> classOf[AgeType],
      "speed" -> classOf[SpeedType],
      "number" -> classOf[ByteType],
      "latitude" -> classOf[LatitudeType],
      "longitude" -> classOf[LongitudeType],
      "date" -> classOf[DateType],
      "time" -> classOf[TimeType],
      "message" -> classOf[StringType],
      "temperature" -> classOf[TemperatureType],
      "level" -> classOf[LevelType],
      "athelete" -> classOf[StringType],
      "recommenation" -> classOf[StringType],
      "advertisement" -> classOf[BitType],
      "urgent" -> classOf[BitType],
      "notification" -> classOf[StringType],
      "average" -> classOf[FloatType]
    )
    keyType.foreach { case(key, grapevineType)  =>
      if (lowerKey.startsWith(key)) return Some(grapevineType)
    }
    if (key.endsWith(" level")) return Some(classOf[LevelType])
    if (key.endsWith(" date")) return Some(classOf[DateType])
    if (key.endsWith(" time")) return Some(classOf[TimeType])
    if (key.endsWith(" rating")) return Some(classOf[LevelType])
    if (key.endsWith(" count")) return Some(classOf[UnsignedShortType])
    if (key.endsWith(" id")) return Some(classOf[StringType])
    if (key.endsWith("_f")) return Some(classOf[FloatType])

    Some(classOf[StringType])
    //None
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
        case "int" | "class java.lang.Integer" => Some(classOf[ByteType])
        case "float" | "class java.lang.Float" | "double" | "class java.lang.Double" => Some(classOf[FloatType])
        case "class java.lang.String" => Some(classOf[StringType])
        case _ => throw new RuntimeException(s"ERROR: Cannot extract type info from value ${v.getClass}")
      }
    }
  }
}
