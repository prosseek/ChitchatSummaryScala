package experiment

import grapevineType._
import simulation.grapevineType.Simulation
import simulation.theoryFalsePositives.Util
//import simulation.StringSimulation

/**
 * Created by smcho on 8/26/14.
 */
object FloatSimulation extends App {
  /*
    GET RANDOM String/Byte
   */
  def getFloat(ba:Array[Byte]) :Option[FloatType] = {
    val d = new FloatType
    if (d.fromByteArray(ba) == BottomType.NoError) {
      Some(d)
    }
    else None
  }

  def getRandomFloat(size:Int = 4) :Option[FloatType] = {
    getFloat(Simulation.getRandomByteArray(size))
  }

  /*
    GET THEORETICAL FP TIME/DATE
   */
  def getTheoryFp() :Double = {
    0.5
  }

  def getFp(bytes:Int = 4) = {
    val theory_fp :Double = getTheoryFp()
    val theory_pair = theory_fp // * StringSimulation.theory_bf()

    Map[String, Double](
      "theory_fp" -> Util.reduced(theory_fp, totalBytes = bytes, thresholdBytes = FloatType.getSize),
      "theory_pair"->Util.reduced(theory_pair, totalBytes = bytes, thresholdBytes = FloatType.getSize)
    )
  }

  def simulation(bs:Int = 4, size:Int = 100000) :Map[String, Double] = {
    var bottom = 0
    var fp = 0
    var fp_pair = 0
    var bytes = math.max(bs, FloatType.getSize)

    (1 to size).foreach { i =>
      val level = getRandomFloat(bytes)
      val str = None // StringSimulation.getRandomStringThatPassesBf()
      if (level.isEmpty)
        bottom += 1
      else {
        fp += 1
        if (str.isDefined) {
          fp_pair +=1
        }
      }
    }
    Map[String, Double](
      "fp" -> fp.toDouble/size,
      "fp_pair"-> fp_pair.toDouble/size
    ) ++ getFp(bytes)
  }

  (3 to 5).foreach { i =>
    val res = simulation(i)
    //    val res = testTime(1000000)
    println(res.mkString("", "\n", "") + "\n")
  }
}
