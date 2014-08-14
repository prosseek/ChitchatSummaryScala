package grapevineType

import org.scalatest._

/**
 * Created by smcho on 8/10/14.
 */
class TestUtil extends FunSuite {

  test ("getTypeFromKey test") {
    val keys = Array("age of john", "speed of a car", "number of friends",
      "latitude", "longitude","date","time")
    val values = Array(Some(classOf[AgeType]), Some(classOf[SpeedType]), Some(classOf[CountType]),
      Some(classOf[LatitudeType]), Some(classOf[LongitudeType]), Some(classOf[DateType]), Some(classOf[TimeType]))
    keys.zipWithIndex.foreach { case(key, index) =>
        val v = values(index)
        assert(Util.getTypeFromKey(key) == v)
    }
  }

  test ("getTypeFromValue test") {
    val values = Array(1, 1.2, null)
    val types = Array(Some(classOf[IntegerType]), Some(classOf[FloatType]), None)

    values.zipWithIndex.foreach { case(value, index) =>
      val t = types(index)
      assert(Util.getTypeFromValue(value) == t)
    }
  }

}
