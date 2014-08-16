package bloomierFilter

import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 8/15/14.
 */
class TestOrderAndMatch extends FunSuite with BeforeAndAfter  {
  var map1 : Map[String, Any] = _
  before {
    map1 = Map("a" -> 1, "b" -> 2, "c" -> 3, "d" -> 4)
  }

  test ("Simple") {
    val k = 3
    val q = 8 * 4

    var m = 6
    var oam = new OrderAndMatchFinder(map = map1, m = m, k = k, q = q)
    oam.find()
    println(oam.getDepthCount())
    println(oam.getOrderHistory())
    println(oam.getTauList())
    println(oam.getPiList())
    println(oam.getKMap())

//    m = 14
//    oam = new OrderAndMatchFinder(map = map1, m = m, k = k, q = q)
//    oam.find()
//    println(oam.getDepthCount())
//    println(oam.getOrderHistory())
//    println(oam.getTauList())
//    println(oam.getPiList())
//    println(oam.getKMap())
  }
}
