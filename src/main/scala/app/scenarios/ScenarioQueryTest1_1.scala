package app.scenarios

import app.GenerateContexts
import core.BloomierFilterSummary
import grapevineType.BottomType

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 9/11/14.
 */
object ScenarioQueryTest1_1 extends App {

  val totalSize = 1000000
  var count = 0

  val conf = MMap[String, Any]()
  conf("iteration") = totalSize
  conf("byteWidth") = 1 // in order to get maximal false positives

  var mapMap = Map[String, Any](
    "hop count" -> 3,
    "date" -> (2014, 6, 23),
    "time" -> (13, 11),
    "athelete" -> "football",
    "position of football" -> "quarterback",
    "level of football" -> 5,
    "msn id" -> "john1988",
    "latitude" -> (30, 25, 38, 2),
    "longitude" -> (-17, 47, 11, 0)
  )
  conf("map") = mapMap

  def query(i:Int, bf: BloomierFilterSummary) : Unit = {
    count += 1
    if (count % 10000 == 0)
      println(s"${count}")

    val key = "athelete"
    if (bf.check(key) == BottomType.NoError) {
      println(s"${key} - ${bf.get(key)}")

      val key2 = s"level of ${key}"
      if (bf.check(key2) == BottomType.NoError) {
        println(s">> ${key} - ${bf.get(key2)}")
      }
    }
  }

  GenerateContexts.parallelExecute(configuration = conf.toMap, query)

}
