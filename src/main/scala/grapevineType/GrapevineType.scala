package grapevineType

/**
 * Some of the types are identified from key and some are from value
 */
abstract class GrapevineType {
  def set(value:Any)
  def get() : Any = {
    throw new RuntimeException("ERROR: You should implement the get() method")
  }
}
