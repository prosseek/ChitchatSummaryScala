package app

import core.BloomierFilterSummary
import grapevineType._
import util.gen.Summary
import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 9/9/14.
 */
object ScenarioTests extends App {
  var count = 0
  var countFp = 0
  var countFp2 = 0

  val totalSize = 1000000
  var multiply = 1
  var m = 10*multiply
  val n = 10
  val k = 3

  val strs = Summary.getDictionaryStrings()
  var mapMap = Map[String, Any](
    "hop count" -> 3,
    "date" -> (2014, 6, 23),
    "time" -> (13, 11),
    "athelete" -> "football",
    "position of athelete" -> "quarterback",
    "level of athelete" -> 5,
    "msn id" -> "john1988",
    "latitude" -> (30, 25, 38, 2),
    "longitude" -> (-17, 47, 11, 0)
  )
  val conf = MMap[String, Any]()
  conf("iteration") = totalSize
  conf("map") = mapMap // nothing
  conf("bf") = null

  // Summary.getBF(Map[String, GrapevineType](), m = 10, n = 2, k = 3)


  def calculate(i:Int, bf: BloomierFilterSummary) : Unit = {
    count += 1
    if (count % 1000 == 0)
      println(s"${count}")

    val key = "position of athelete"
    if (bf.check(key) == BottomType.NoError) {
      countFp += 1
      println(s"${key} - ${bf.get(key)}")
      val key2 = "athelete"
      if (bf.check(key2) == BottomType.NoError) {
        countFp2 += 1
        println(s"${key2} - ${bf.get(key2)}")
      }
    }
  }

  GenerateContexts.parallelExecute(configuration = conf.toMap, calculate)
  println("%5.3f%% - %5.3f%%".format(countFp.toDouble/totalSize, countFp2.toDouble/totalSize))
}