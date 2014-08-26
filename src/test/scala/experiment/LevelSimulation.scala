package experiment

import grapevineType._
import util.experiment.Simulation

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

    def getFp() = {
      val theory_fp :Double = getTheoryFp()
      val theory_pair = theory_fp * StringSimulation.theory_bf()

      Map[String, Double](
        "theory_fp" -> theory_fp,
        "theory_pair"->theory_pair
      )
    }

    def simulation(size:Int = 100000) :Map[String, Double] = {
      var bottom = 0
      var fp = 0
      var fp_pair = 0

      (1 to size).foreach { i =>
        val level = getRandomLevel()
        val str = StringSimulation.getRandomStringThatPassesBf()
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
      ) ++ getFp
    }

    val res = simulation()
//    val res = testTime(1000000)
    println(res.mkString("","\n",""))
  }
