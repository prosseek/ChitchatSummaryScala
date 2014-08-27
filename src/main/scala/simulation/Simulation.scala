package simulation

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object Simulation {
  def getRandomByteArray(size: Int) = {
    val random = new Random
    val ab = ArrayBuffer[Byte]()
    (ab /: (1 to size)) { (acc, value) =>
      acc += random.nextInt(256).toByte
    }
    ab.toArray
  }
}

/**
 * Created by smcho on 8/27/14.
 */
abstract class Simulation {
  def simulate(message:String, width:Int, m:Map[String, Int]) :Map[String,Double]
}
