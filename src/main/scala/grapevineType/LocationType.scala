package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
class LocationType extends GrapevineType {
  var value: Int = -1
  override def set(value: Any): Unit = {
    this.value = value.asInstanceOf[Int]
  }
}

