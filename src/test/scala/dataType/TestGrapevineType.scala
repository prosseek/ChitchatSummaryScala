package dataType

import dataType.GrapevineType._
import org.scalatest._

/**
 * Created by smcho on 8/10/14.
 */
class TestGrapevineType extends FunSuite {

  test ("Simple test") {
    val a = Integer
    assert(a == Integer)
    //assert(GrapevineType.values.size == 2)
  }

  test ("getTypeFromKey test") {
    val keys = Array("age of john", "speed of a car", "number of friends",
      "latitude", "longitude","date","time")
    val values = Array(Some(Age), Some(Speed), Some(Count),
      Some(Latitude), Some(Longitude), Some(Date), Some(Time))
    keys.zipWithIndex.foreach { case(key, index) =>
        val v = values(index)
        assert(getTypeFromKey(key) == v)
    }
  }

  test ("getTypeFromValue test") {
    val values = Array(1, 1.2, null)
    val types = Array(Some(Integer), Some(FloatingPoint), Some(Null))

    values.zipWithIndex.foreach { case(value, index) =>
      val t = types(index)
      assert(getTypeFromValue(value) == t)
    }
  }

}
