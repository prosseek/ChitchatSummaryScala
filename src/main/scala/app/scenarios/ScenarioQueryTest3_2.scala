package app.scenarios

import app.GenerateContexts
import core.BloomierFilterSummary
import grapevineType.BottomType

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 9/11/14.
 */
object ScenarioQueryTest3_2 extends App {

  val totalSize = 1000000
  var count = 0

  val conf = MMap[String, Any]()
  conf("iteration") = totalSize
  conf("byteWidth") = 1 // in order to get maximal false positives

  var mapMap = Map[String, Any](
    "hop count" -> 1,
    "date" -> (2014, 1, 23),
    "time" -> (16, 10),
    "notification" -> "10 minutes delay",
    "message" -> "arrive bus stop 2325 at 04:20pm",
    "latitude" -> (30, 25, 38, 2),
    "longitude" -> (-17, 47, 11, 0),
    "sender" -> "south bound bus 11"
  )
  conf("map") = mapMap

  def query(i:Int, bf: BloomierFilterSummary) : Unit = {
    count += 1
    if (count % 10000 == 0)
      println(s"${count}")

    val key = "notification"
    if (bf.check(key) == BottomType.NoError) {
      val key2 = "sender"
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
