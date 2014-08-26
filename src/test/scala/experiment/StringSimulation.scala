package experiment

import bloomFilter.BloomFilter
import grapevineType.{BottomType, StringType}
import util.experiment.Simulation

import scala.util.Random
import math._
/**
 * Created by smcho on 8/24/14.
 */
object StringSimulation extends App  {
  val random = new Random
  var bf:BloomFilter = null
//  val filePath = "experiment/data/words.txt"
//  // false positive rate - 0.017551354431981234 (1.7%)
//  val bf = new BloomFilter(filePath, m = 20*100000, k = 5, seed = 0)

  /*
    Theory
   */
  def theory_string(minimumSize:Int=2) = {
    // 0x20 - 0x7E (95/256)
    // 1/256 * a^(start)*(a^(255 - start + 1) - 1)/(a - 1)
    // or 1/256 * a^(start)/(a - 1)
    val alpha = (95.0/256.0)
    1.0/256.0 * pow(alpha, minimumSize) * (pow(alpha, 256 - minimumSize) - 1)/(alpha - 1.0)
  }
  def theory_bf(bfInput:BloomFilter = null, minimumSize:Int=2) = {

    var newBf = bfInput
    if (newBf == null) {
      val filePath = "experiment/data/words.txt"
      val bf = new BloomFilter(filePath, m = 20*100000, k = 5, seed = 0)
      newBf = bf
    }

    //println(bf.getFP)
    newBf.getFP * theory_string(minimumSize)
  }


  def getFp(minimumSize:Int) = {
    Map[String, Double](
      //"bottom_computation" -> bottom_computation/size.toDouble,
      "theory_fp" -> theory_string(minimumSize),
      //"bottom_relation_pair" -> bottom_relation_pair/size.toDouble,
      "theory_fp_filter" -> theory_bf(getBF(), minimumSize)
    )
  }

  /*
    Get random string
   */
  def getString(ba:Array[Byte], minimumSize:Int=2) :Option[StringType] = {
    assert((ba(0) & 0xFF) + 1 == ba.size, s"${ba(0) + 1} != ${ba.size}")
    StringType.setMinimumLength(minimumSize)
    val stringType = new StringType
    if (stringType.fromByteArray(ba) == BottomType.NoError) {
      Some(stringType)
    }
    else None
  }

  def getRandomString(minimumSize:Int = 2) :Option[StringType] = {
    val random = new Random
    val size = random.nextInt(255) & 0xFF
    getString(Array[Byte](size.toByte) ++ Simulation.getRandomByteArray(size), minimumSize = minimumSize)
  }

  def getRandomStringThatPassesBf(bf:BloomFilter = null, minimumSize:Int = 2) :Option[StringType] = {
    var blf =  bf
    if (blf == null)
      blf = getBF()
    val ra = getRandomString(minimumSize = minimumSize)
    if (ra.isEmpty) None
    else {
      if (checkBF(blf, ra.get.get)) {
        ra
      }
      else None
    }
  }

  /*
    BF
   */

  def getBF() = {
    if (StringSimulation.bf == null) {
      val filePath = "experiment/data/words.txt"
      // false positive rate - 0.017551354431981234 (1.7%)
      StringSimulation.bf = new BloomFilter(filePath, m = 20 * 100000, k = 5, seed = 0)
    }
    StringSimulation.bf
  }

  def checkBF(bf:BloomFilter, str:String) = {
    bf.get(str)
  }

  /*
    Simulation
   */
  def simulate(message:String, bf:BloomFilter, minimumSize:Int = 2, size:Int = 100000) = {
    println(message)

    var bottom = 0
    var fp = 0
    var fp_filter = 0

    (1 to size).foreach { i =>
      val ra = getRandomString(minimumSize)
      if (ra.isEmpty)
        bottom += 1
      else {
        fp += 1
        if (checkBF(bf, ra.get.get)) {
          fp_filter += 1
        }
      }
    }

    Map[String, Double](
      //"bottom_computation" -> bottom_computation/size.toDouble,
      "fp" -> fp/size.toDouble,
      //"bottom_relation_pair" -> bottom_relation_pair/size.toDouble,
      "fp_filter" -> fp_filter/size.toDouble
    ) ++ getFp(minimumSize)
  }

  //println(getRandomString())

  (1 to 5).foreach { i =>
    val mybf = getBF()
    val res = simulate(s"minimum size = ${i}", mybf, minimumSize = i)
    println(res.mkString("", "\n", "") + "\n")
  }
}