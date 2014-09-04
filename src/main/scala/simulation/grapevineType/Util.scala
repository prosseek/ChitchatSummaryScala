package simulation.grapevineType

import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/26/14.
 */
object Util {
  def map2string(m:Map[String, Double]) :String = {
    val sb = new StringBuilder
    // find all the keys that is not theory and sort them
    val keys = m.keys.filter( s => !s.startsWith("theory")).toList.sorted
    keys.foreach { key =>
      val theoryKey = "theory_" + key
      if (m.keySet.exists(_ == theoryKey))
        sb.append(s"${key}:${m(key)}\n${theoryKey}:${m(theoryKey)}\n")
      else
        sb.append(s"${key}:${m(key)}\n")
    }
    sb.toString
  }

  def getAverage(maps:Array[Map[String, Double]]) : Map[String, Double] = {
    val m = MMap[String, Double]() withDefaultValue(0.0)

    (m /: maps) { (acc, value) =>
      value.keys.foreach { k  =>
        acc(k) += value(k)
      }
      acc
    }
    m.map { case (key, value) =>
      m(key) = value/maps.size
    }
    m.toMap
  }
}
