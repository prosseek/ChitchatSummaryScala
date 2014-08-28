package bloomFilter

import scala.util.Random

/**
 * Created by smcho on 8/25/14.
 */
object AppBloomFilter extends App {

  def randomString(length: Int) = {
    val r = new scala.util.Random
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(r.nextPrintableChar)
    }
    sb.toString
  }
  def getFP(size:Int, fp:Int) = {
    fp.toDouble/size.toDouble
  }

  val random = new Random()
  val filePath = "experiment/data/words.txt"
  val bf = new BloomFilter(filePath, m = 20*100000, k = 5, seed = 0)
  val size = 100000

  var in = 0
  var out = 0
  (1 to size).foreach { i =>
    val size = random.nextInt(5) + 2
    val key = randomString(size)
    if (bf.get(key)) {
      in += 1
    }
    else {
      out += 1
    }
  }
  println(s"FP - ${getFP(size, in)}, THEORY - ${bf.getFp()}")
}

/*
    100,000 => FP - 0.02148 (2.1%)
    theory => 0.017 (1.7%)
 */