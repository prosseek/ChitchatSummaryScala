package filter

import chitchat.typefactory.TypeDatabase
import org.scalatest.FunSuite
import summary.{CBFSummary, FBFSummary}

class TestFilter extends FunSuite {
  test ("simple") {
    val ti = TypeDatabase()
    val filter = ChitchatFilter(ti)

    val m = Map[String, Any]("name" -> "John", "age" -> 10,
      "latitude" -> List(1,2,3,4), "longitude" -> List(11,12,13,14))
    val bf = FBFSummary(input = m, q = 4*8, filter = filter)

    assert(bf.get("longitude").get == List(11,12,13,14))
    assert(bf.getFiltered("longitude").get == List(11,12,13,14))
    assert(bf.get("name").get == "John")
    assert(bf.get("age").get == 10)
  }

  test ("missing member") {
    val ti = TypeDatabase()
    val filter = ChitchatFilter(ti)
    val m = Map[String, Any]("name" -> "John", "age" -> 10,
      "latitude" -> List(1, 2, 3, 4))

    val bf = FBFSummary(input = m, q = 4 * 8, filter = filter)
    assert(bf.get("latitude").get == List(1, 2, 3, 4))
    // assert(bf.getFiltered("latitude").isEmpty)
    assert(bf.get("name").get == "John")
    assert(bf.get("age").get == 10)
  }
}
