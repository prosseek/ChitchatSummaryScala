package experiment

import grapevineType._
import simulation.grapevineType.Simulation
import simulation.theoryFalsePositives.Util
//import simulation.StringSimulation

/**
 * 12 : LevelType         8            1       (0, 10)               pair with others
 */
object LevelSimulation extends App {
    /*
      GET RANDOM String/Byte
     */
    def getLevel(ba:Array[Byte]) :Option[LevelType] = {
      val d = new LevelType
      if (d.fromByteArray(ba) == BottomType.NoError) {
        Some(d)
      }
      else None
    }

    def getRandomLevel(size:Int = 1) :Option[LevelType] = {
      getLevel(Simulation.getRandomByteArray(size))
    }

    /*
      GET THEORETICAL FP TIME/DATE
     */

    def getTheoryFp() :Double = {
      (10.0) / (1.toLong << 8)
    }

    def getFp(bytes:Int) = {
      val theory_fp :Double = getTheoryFp()
      val theory_pair = theory_fp // * StringSimulation.theory_bf()

      Map[String, Double](
        "theory_fp" -> Util.reduced(theory_fp, totalBytes = bytes, thresholdBytes = LevelType.getSize),
        "theory_pair"->Util.reduced(theory_pair, totalBytes = bytes, thresholdBytes = LevelType.getSize)
      )
    }

    def simulation(bytes:Int = 1, size:Int = 100000) :Map[String, Double] = {
      var bottom = 0
      var fp = 0
      var fp_pair = 0

      (1 to size).foreach { i =>
        val level = getRandomLevel(bytes)
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
        "fp_pair"->fp_pair.toDouble/size
      ) ++ getFp(bytes)
    }

  (1 to 3).foreach { i =>
    println(s"Working with i = ${i}")
    val res = simulation(bytes = i)
    //    val res = testTime(1000000)
    println(res.mkString("", "\n", "") + "\n")
  }
}
