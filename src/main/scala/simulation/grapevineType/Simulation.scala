package simulation.grapevineType

import scala.collection.mutable.{ArrayBuffer, Map => MMap}
import scala.util.Random

object Simulation {
  def getRandomByteArray(size: Int) = {
    val random = new Random
    (ArrayBuffer[Byte]() /: (1 to size)) { (acc, value) =>
      acc += random.nextInt(256).toByte
    }.toArray
  }
}

/**
 * Created by smcho on 8/27/14.
 */
abstract class Simulation(config:Map[String, Int]) {
  def getTime(diff:Long) = {
    val res = (diff/1000000000)
    (res / 60, res % 60)
  }

  def insertBeforeString(str:String, pattern:String) = {
    str.replaceAll("""(?m)^(.)""",s"${pattern}" + "$1")
  }

  def debugPrint(str:String) = {
    var print = false
    if (config != null && config.contains("verbose") && config("verbose") == 1) print = true
    if (print) {
      println(insertBeforeString(str, " -> "))
    }
  }
  def simulate(width:Int = 4) :Map[String,Double]
  def simulateOverWidth(s: Int, e: Int): Map[Int, Map[String, Double]] = {
    val m = MMap[Int, Map[String, Double]]()

    (s to e).foreach {i =>
      m(i) = simulate(i)
    }
    m.toMap
  }

  def runAndAverage(iteration:Int, f: () => Map[String, Double]) = {
    val m = MMap[String, Double]() withDefaultValue(0.0)

    val t1 = System.nanoTime()
    (1 to iteration).foreach {i =>
      debugPrint(s"iteration - ${i}")
      val res = f()
      debugPrint(Util.map2string(res))
      res.keys.foreach {key => m(key) += res(key)}
    }

    val now = System.nanoTime()
    debugPrint(s"total time to execute (${getTime(now - t1)._1}) min - (${getTime(now - t1)._2}) sec")

    m.keys.foreach {key => m(key) = m(key) / iteration}
    debugPrint(s"RESULT:\n${Util.map2string(m.toMap)}")
    m.toMap
  }
}
