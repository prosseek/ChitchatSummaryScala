package grapevineType

/**
 * Some of the types are identified from key and some are from value
 */
abstract class GrapevineType {
  def set(value:Any)
  def toByteArray(goalSize:Int = 4) : Array[Byte]
  def fromByteArray(b: Array[Byte]) : Boolean
  def get() : Any = {
    throw new RuntimeException("ERROR: You should implement the get() method")
  }
}
