package util.experiment

import scala.util.Random

/**
 * Created by smcho on 8/24/14.
 */

import scala.collection.mutable.{Map => MMap}

// http://alvinalexander.com/scala/creating-random-strings-in-scala
object Util {
  val random = new Random
  val x = Random.alphanumeric
  def getRandomMaps(size:Int) = {
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

  def generateRandomContextSummary() = {
    val res = getRandomMaps(10)
    println(res.mkString)
  }
}
