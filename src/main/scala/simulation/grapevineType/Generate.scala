package simulation.grapevineType

import scala.collection.mutable.{Map => MMap}
import scala.util.Random
/**
 * Created by smcho on 8/27/14.
 */
object Generate {
  def getRandomMaps(size:Int) = {
    val random = new Random
    val x = Random.alphanumeric
    def getRandomString(length: Int) = {
      val r = new scala.util.Random
      val sb = new StringBuilder
      for (i <- 1 to length) {
        sb.append(r.nextPrintableChar)
      }
      sb.toString
    }
    val mmap = MMap[String, String]()

    (0 until size).foreach { i =>
      mmap += (getRandomString(random.nextInt(5) + 3) -> getRandomString(random.nextInt(5) + 3))
    }
    mmap.toMap
  }

  def randomContextSummary(size:Int) = {
    val res = getRandomMaps(size)
    println(res.mkString)
  }
}
