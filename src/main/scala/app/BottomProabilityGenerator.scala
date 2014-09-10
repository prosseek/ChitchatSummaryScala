import app.GenerateContexts
import core.BloomierFilterSummary
import grapevineType.{BottomType, GrapevineType}
import util.gen.Summary

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 9/9/14.
 */
object BottomProabilityGenerator extends App {
  var count = 0
  var countFp = 0
  val totalSize = 100000
  var multiply = 1
  var m = 10*multiply
  val n = 10
  val k = 3

  val strs = Summary.getDictionaryStrings()
  var mapMap = Map[String, GrapevineType]() // ("latitude" -> LatitudeType((30, 17, 14, 0)), "longitude" -> LongitudeType((-97, 44, 11, 6)))
  val conf = MMap[String, Any]()
  conf("iteration") = totalSize
  conf("map") = mapMap // nothing
  val s = Summary.getBFfromMNK(m = m, n = n, k = k, t = "byte")
  conf("bf") = s
  assert(s.getN() == n)

  // Summary.getBF(Map[String, GrapevineType](), m = 10, n = 2, k = 3)


  def calculate(i:Int, bf: BloomierFilterSummary) : Unit = {
//    count += 1
//    if (count % 1000 == 0)
//      println(s"${count}")

    val randomKey = Summary.getRandomString(strs)
    if (bf.check(randomKey) == BottomType.Bottom) {
      //println("Gotcha")
      countFp += 1
    }
  }

  val finalFactor = 10
  (2 to finalFactor).foreach { i =>
    m = 10 * i
    val s = Summary.getBFfromMNK(m = m, n = n, k = k, t = "byte")
    conf("bf") = s
    GenerateContexts.parallelExecute(configuration = conf.toMap, calculate)
    // println(s"exp. => ${countFp.toDouble/totalSize}, theory => ${Summary.bottomTheory(m, n, k)}")
    println("%d  %4.2f  %4.2f".format(i, countFp.toDouble/totalSize*100.0, Summary.bottomTheory(m, n, k)*100.0))
    countFp = 0
  }


}
