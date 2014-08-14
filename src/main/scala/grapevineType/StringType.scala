package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
class StringType extends GrapevineType {
  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[String]
  }
  override def get(): String = {
    value.asInstanceOf[String]
  }
  override def toByteArray(goalSize: Int): Array[Byte] = {
    null
  }
  def fromByteArray(b: Array[Byte]): Boolean = {
    true
  }
}