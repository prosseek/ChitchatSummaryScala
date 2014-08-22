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

    println(Util.toMile(Util.getDistance(lat1, long1, lat2, long2)))
  }
}
