package filter

import chitchat.typetool.TypeInference
import org.scalatest.FunSuite
import summary.{CBFSummary, FBFSummary}

class TestFilter extends FunSuite {
  test ("simple") {
    val ti = TypeInference()
    val m = Map[String, Any]("name" -> "John", "age" -> 10)
    val bf = FBFSummary(input = m, q = 4*8, typeInference = ti)

    assert(bf.get("name").get == "John")
    assert(bf.get("age").get == 10)
  }
}
