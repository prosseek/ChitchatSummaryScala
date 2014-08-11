package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
class StringType extends GrapevineType {
  var value:String = ""
  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[String]
  }
  override def get(): String = {
    value
  }
}