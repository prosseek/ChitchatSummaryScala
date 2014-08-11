package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
class IntegerType extends GrapevineType {
  var value: Int = -1
  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[Int]
  }
  override def get(): Int = {
    value
  }
}
