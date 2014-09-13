package simulation.grapevineType

import bloomFilter.BloomFilter
import grapevineType.{BottomType, StringType}
import simulation.theoryFalsePositives
import simulation.theoryFalsePositives.String

import scala.util.Random

/**
 * Created by smcho on 8/24/14.
 */
class StringSimulation(config:Map[String, Int]) extends Simulation(config) {
  val random = new Random
  if (StringSimulation.bf == null) {
    StringSimulation.bf = getBf()
  }
  //  val filePath = "experiment/data/words.txt"
  //  // false positive rate - 0.017551354431981234 (1.7%)
  //  val bf = new BloomFilter(filePath, m = 20*100000, k = 5, seed = 0)


  def getTheoryFp(minimumSize: Int) = {
    Map[String, Double](
      //"bottom_computation" -> bottom_computation/size.toDouble,
      "theory_fp" -> String.fp(minimumSize),
      //"bottom_relation_pair" -> bottom_relation_pair/size.toDouble,
      "theory_fp_bf" -> String.fp_bf(getBf(), minimumSize)
    )
  }

  /*
    Get random string
   */
  def getString(ba: Array[Byte], minimumSize: Int = 2): Option[StringType] = {
    assert((ba(0) & 0xFF) + 1 == ba.size, s"${ba(0) + 1} != ${ba.size}")
    StringType.setMinimumLength(minimumSize)
    val stringType = new StringType
    if (stringType.fromByteArray(ba) == BottomType.NoError) {
      Some(stringType)
    }
    else None
  }

  def getRandomString(minimumSize: Int = 2): Option[StringType] = {
    val random = new Random
    val size = random.nextInt(255) & 0xFF
    getString(Array[Byte](size.toByte) ++ Simulation.getRandomByteArray(size), minimumSize = minimumSize)
  }

  def getRandomStringThatPassesBf(bf: BloomFilter = null, minimumSize: Int = 2): Option[StringType] = {
    var blf = bf
    if (blf == null)
      blf = getBf()
    val ra = getRandomString(minimumSize = minimumSize)
    if (ra.isEmpty) None
    else {
      if (checkBf(blf, ra.get.get)) {
        ra
      }
      else None
    }
  }

  /*
    BF
   */
  def getBf() = {
    if (StringSimulation.bf == null) {
      StringSimulation.bf = theoryFalsePositives.Util.getBf()
    }
    StringSimulation.bf
  }

  def checkBf(bf: BloomFilter, str: String) = {
    bf.get(str)
  }

  /*
    Simulation
   */
  def simulate(minimumSize: Int): Map[String, Double] = {
    //var minimumSize = config("minimum_size")
    var bottom = 0
    var fp = 0
    var fp_bf = 0

    var size = config("size")

    (1 to size).foreach { i =>
      val ra = getRandomString(minimumSize)
      if (ra.isEmpty)
        bottom += 1
      else {
        fp += 1
        if (checkBf(StringSimulation.bf, ra.get.get)) {
          fp_bf += 1
        }
      }
    }

    Map[String, Double](
      //"bottom_computation" -> bottom_computation/size.toDouble,
      "fp" -> fp / size.toDouble,
      //"bottom_relation_pair" -> bottom_relation_pair/size.toDouble,
      "fp_bf" -> fp_bf / size.toDouble
    ) ++ getTheoryFp(minimumSize)
  }
}

object StringSimulation extends App {
  var bf: BloomFilter = null
  var config = Map[String, Int]("iteration" -> 1, "minimum_size" -> 2, "size" -> 10000000)
  var ss = new StringSimulation(config)

  // for string we simulate over the minimum string
  (1 to 4).foreach { i =>
    val res = ss.simulate(i)
    println(s"minimum ${i} -> ${Util.map2string(res)}")
  }
}