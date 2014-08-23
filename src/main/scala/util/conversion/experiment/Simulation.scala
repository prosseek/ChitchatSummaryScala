package util.conversion.experiment

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Created by smcho on 8/22/14.
 */
object Simulation {
  val random = new Random
  def getRandomByteArray(size:Int) = {
    val ab = ArrayBuffer[Byte]()
    (ab /: (1 to size)) { (acc, value) =>
      acc += random.nextInt(256).toByte
    }
    ab.toArray
  }
}
