package app.scenarios

import app.GenerateContexts
import core.BloomierFilterSummary
import grapevineType.BottomType

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 9/11/14.
 */
object ScenarioQueryTest1_2 extends App {

  val totalSize = 1000000
  var count = 0

  val conf = MMap[String, Any]()
  conf("iteration") = totalSize
  conf("byteWidth") = 1 // in order to get maximal false positives

  var mapMap = Map[String, Any](
    "hop count" -> 20,
    "date" -> (2014, 6, 23),
    "time" -> (10, 0),
    "message" -> "I'll be waiting at my location.",
    "latitude" -> (30, 25, 48, 2),
    "longitude" -> (-13, 41, 11, 30),
    "group" -> "marathon lovers group",
    "sender" -> "Tim"
  )
  conf("map") = mapMap

  def query(i:Int, bf: BloomierFilterSummary) : Unit = {
    count += 1
    if (count % 10000 == 0)
      println(s"${count}")

    val key = "sender"
    if (bf.check(key) == BottomType.NoError) {
      val value = bf.get(key)
      if (value == "Tim") {
        println(s"${key} - ${value}")
      }
    }
  }

  GenerateContexts.parallelExecute(configuration = conf.toMap, query)

}
