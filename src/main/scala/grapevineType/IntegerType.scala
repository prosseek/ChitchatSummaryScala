package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
abstract class IntegerType extends GrapevineType {
  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[Int]
  }
  override def get(): Int = {
    value.asInstanceOf[Int]
  }
}
