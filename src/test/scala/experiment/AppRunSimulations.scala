package experiment

import java.io.File
import java.nio.file.{Files, Paths}

import simulation.grapevineType.LatitudeSimulation

/**
 * Created by smcho on 8/26/14.
 */
object AppRunSimulations extends App {
  val resultsDirectory = "experiment/simulationResults"

  def directoryCheck(directoryPath:String) = {
    if (!Files.exists(Paths.get(directoryPath))) {
      val dir = new File(directoryPath)
      dir.mkdir()
    }
  }

  def getName(m:Map[String, AnyVal]) = {
    val name = m("name").asInstanceOf[String]
  }

  def run(m:Map[String, AnyVal], iter:Int) = {
    val name = getName(m)

//    (1 to iter).foreach { i =>
//      val fileName = s"${name}${i}"
//      val sb = new StringBuilder
//      val ls = new LocationSimulation
//      // location simulation
//      val now = System.nanoTime()
//      print(fileName + ": ")
//      (1 to 10).foreach { i =>
//        print(s"${i}-")
//        sb.append(s"${i} ************\n")
//        val res = ls.simulate("Latitude check", width = i, size = size, near = near)
//        val simResults = (res.mkString("", "\n", "") + "\n")
//        sb.append(simResults + "\n")
//      }
//      util.io.File.resultsToFile(s"${directoryPath}/${fileName}.txt", sb.toString)
//      val seconds = (System.nanoTime - now) / 1000000000
//
//      println(": %d minutes %d seconds".format(seconds / 60, seconds % 60))
//    }

  }

  def generateDataFromMaps(maps:Map[Int, Map[String, Double]], keys:Array[String]) = {
    val size = maps.size
    val sb = new StringBuilder
    (1 to size).foreach {i =>
      val m = maps(i)
      val str = keys.map(key => s"${m(key)}\t").reduce(_ ++ _)
      sb.append(s"${i}\t" + str + "\n")
    }
    sb.toString
  }

  def runLatitudeSimulation(size:Int, near:Int, iter:Int) = {
    val directoryName = "latitude"
    val directoryPath = s"${resultsDirectory}/${directoryName}"
    directoryCheck(directoryPath)

    val m = Map[String,Int]("size" -> size, "near" -> near, "both_directions"->0, "iteration" -> iter, "verbose" ->0)
    val ls = new LatitudeSimulation(m)
    val res = ls.simulateOverWidth(1,10)
    val keys = Array[String]("fp", "fp_pair", "fp_near","theory_fp", "theory_fp_pair", "theory_fp_near")
    println(generateDataFromMaps(res, keys))
    //println(res.mkString("\n\n"))
  }
//
  val size = 100000
  val iter = 5
  val near = 10
  runLatitudeSimulation(size = size, near = near, iter = iter)
//  runLocationSimulation(size = size, near = 50, iter = iter)
//  runLocationSimulation(size = size, near = 100, iter = iter)
}
