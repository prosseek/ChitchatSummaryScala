package grapevineType

import org.scalatest._

/**
 * Created by smcho on 8/14/14.
 */
class TestGrapevineType extends FunSuite {
  test("ID test") {
    val keys = Array(
      classOf[ByteType], classOf[FixedPointType], classOf[FloatType], classOf[StringType], // 0 - 3
      classOf[DateType], classOf[TimeType], // 4 - 5
      classOf[LatitudeType], classOf[LongitudeType], //6 - 7
      classOf[AgeType], classOf[SpeedType], classOf[TemperatureType] // 8 - 10
      )
//
//    Range(0, keys.size).foreach {i =>
//      keys(i).newInstance.asInstanceOf[GrapevineType].getId == i
//    }
//    keys.foreach { t =>
//      t.getInstance
//    }
    keys.zipWithIndex.map { case(t, i) =>
      keys(i).newInstance.asInstanceOf[GrapevineType].getId == i
    }

  }

  test ("getTypeFromKey test") {
    val keys = Array("age of john", "speed of a car", "number of friends",
      "latitude", "longitude","date","time")
    val values = Array(Some(classOf[AgeType]), Some(classOf[SpeedType]), Some(classOf[ByteType]),
      Some(classOf[LatitudeType]), Some(classOf[LongitudeType]), Some(classOf[DateType]), Some(classOf[TimeType]))
    keys.zipWithIndex.foreach { case(key, index) =>
      val v = values(index)
      assert(GrapevineType.getTypeFromKey(key) == v)
    }
  }

  test ("getTypeFromValue test") {
    val values = Array(1, 1.2, null)
    val types = Array(Some(classOf[ByteType]), Some(classOf[FloatType]), None)

    values.zipWithIndex.foreach { case(value, index) =>
      val t = types(index)
      assert(GrapevineType.getTypeFromValue(value) == t)
    }
  }
}

