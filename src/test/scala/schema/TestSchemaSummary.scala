package schema

import chitchat.typefactory.TypeDatabase
import filter.ChitchatFilter
import org.scalatest.FunSuite
import summary.FBFSummary

class TestSchemaSummary extends FunSuite {

  test ("simple") {
    val simpleFile = "./src/test/resources/jsonFiles/schema/simple.json"
    val typeInference = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(typeInference)
    val fbf = FBFSummary(source=simpleFile, q = 1*8, filter = filter)
    val schema = SchemaSummary()
    //val json = schema.recover(fbf = fbf, ruleName = "notification")
    //println(json.map.mkString(":"))
  }
}
