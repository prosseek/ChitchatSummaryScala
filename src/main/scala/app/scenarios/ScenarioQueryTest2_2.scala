package app.scenarios

import app.GenerateContexts
import core.BloomierFilterSummary
import grapevineType.BottomType

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 9/11/14.
 */
object ScenarioQueryTest2_2 extends App {

  val totalSize = 1000000
  var count = 0

  val conf = MMap[String, Any]()
  conf("iteration") = totalSize
  conf("byteWidth") = 1 // in order to get maximal false positives

  var mapMap = Map[String, Any](
    "hop count" -> 2,
    "date" -> (2014, 6, 13),
    "time" -> (11, 11),
    "level of recommendation" -> 2,
    "recommendation" -> "Chef's special",
    "comment" -> "Too expensive considering the quality"
  )
  conf("map") = mapMap

  def query(i:Int, bf: BloomierFilterSummary) : Unit = {
    count += 1
    if (count % 10000 == 0)
      println(s"${count}")

    val key = "recommendation"
    if (bf.check(key) == BottomType.NoError) {
      val key2 = "level of recommendation"
      if (bf.check(key2) == BottomType.NoError) {
        // I only want to find today
        val key = "date"
        if (bf.check(key) == BottomType.NoError) {
          println(bf.get(key).asInstanceOf[(Int, Int, Int)])
          //println(s"${bf.get(key)}")
        }
      }
    }
  }

  GenerateContexts.parallelExecute(configuration = conf.toMap, query)

}
