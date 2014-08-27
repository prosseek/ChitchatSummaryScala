package experiment

import java.io.File
import java.nio.file.{Files, Paths}

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

//  def runLocationSimulation(size:Int, near:Int, iter:Int) = {
//    val directoryName = "latitude"
//    val directoryPath = s"${resultsDirectory}/${directoryName}"
//    directoryCheck(directoryPath)
//  }
//
//  val size = 100
//  val iter = 1
//  runLocationSimulation(size = size, near = 10, iter = iter)
//  runLocationSimulation(size = size, near = 50, iter = iter)
//  runLocationSimulation(size = size, near = 100, iter = iter)
}
