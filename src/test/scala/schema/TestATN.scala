package schema

import chitchat.typefactory.TypeDatabase
import filter.ChitchatFilter
import org.scalatest.FunSuite
import summary.FBFSummary

/*
val grammar = Map[String, Seq[String]](
"notification" -> Seq("sender", "event", "datetime", "location"),
"sensor" -> Seq("count", "datetime", "location", "value+")
)

val alias = Map[String, Seq[String]](
"count" -> Seq("ubyte")
)

val typedefs = Map[String, Seq[String]](
"sender" -> Seq("name", "id?", "location?"),
"location" -> Seq("longitude", "latitude"),
"datetime" -> Seq("date", "time"),
"value" -> Seq("name", "id?", "value", "unit")
)
*/

class TestATN extends FunSuite {
  test ("simple") {
    val simpleFile = "./src/test/resources/jsonFiles/schema/simple.json"
    val typeInference = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(typeInference)
    val fbf = FBFSummary(source=simpleFile, q = 1*8, filter = filter)

    val atn = new ATN(fbf)
    val result = atn.atn("notification")
    assert(result.size == 6)
  }
}
