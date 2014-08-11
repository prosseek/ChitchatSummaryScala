package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
class FloatingPointType extends GrapevineType {
  var value:Double = 0.0
  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[Double]
  }
  override def get(): Double = {
    value
  }
}
