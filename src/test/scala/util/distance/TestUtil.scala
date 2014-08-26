package util.distance

import grapevineType._
import org.scalatest.FunSuite

/**
 * Created by smcho on 8/22/14.
 */
class TestUtil extends FunSuite {
  test ("Simple") {
    val lat1 = new LatitudeType((30, 25, 38, 2))
    val long1 = new LongitudeType((-97, 47, 19, 1))

    val lat2 = new LatitudeType((30, 25, 6, 9))
    val long2 = new LongitudeType((-97, 53, 19, 0))

    val lat3 = new LatitudeType((29, 56, 18, 4))
    val long3 = new LongitudeType((-95, 23, 32, 8))

    // 5.986833079502547 miles
    println(Util.toMile(Util.getDistance(lat1, long1, lat2, long2)))
    // 147.05341534242595 miles (from Austin to Houston)
    println(Util.toMile(Util.getDistance(lat1, long1, lat3, long3)))
  }

  test ("Date Distance") {
    val t1 = DateType((2014,1,1))
    val t2 = DateType((2014,1,2))
    val t3 = DateType((2015,1,1))
    assert(Util.getDateDistance(t1, t2) == 1)
    assert(Util.getDateDistance(t1, t3) == 366)
  }

  // date distance from byteFromArray
  test ("Date distance from array") {

  }

  test ("Time Distance") {
    val t1 = TimeType((14, 30))
    val t2 = TimeType((14, 31))
    val t3 = TimeType((20, 30))
    assert(Util.getTimeDistance(t1, t2) == 1)
    assert(Util.getTimeDistance(t1, t3) == 6 * 60)
  }
}
