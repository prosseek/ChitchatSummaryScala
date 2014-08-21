package util.gnuplot

import java.io.OutputStream

import scala.collection.mutable.ArrayBuffer
import scala.concurrent._
import scala.io.Source
import scala.sys.process._

/**
 * Created by smcho on 8/20/14.
 */
object Gnuplotter {
  // plot "/Users/smcho/tmp/imgs/gnuplot/mass/dense_meshes_dir/cohorts.txt.data" using 1:2 title "singles" w lp, "/Users/smcho/tmp/imgs/gnuplot/mass/dense_meshes_dir/cohorts.txt.data" using 1:3 title "aggregates" w lp
  val template = s"""set terminal pngcairo font 'DroidSerif'
                   |set output "OUTPUT"
                   |
                   |set xtics font ", 15"
                   |set ytics font ", 15"
                   |set key font ",18"
                   |set key top right
                   |set xlabel "XLABEL" font ",20"
                   |set ylabel "YLABEL" font ",20"
                   |set title "TITLE" font ",25"
                 """.stripMargin

  def getGnuplotPath = "/usr/local/bin/gnuplot"

  /**
   * smap : "C1" -> "hello"
   * extract 1 and hello from the map and create the gnuplot code
   *
   * @param filePath
   * @param smap
   * @return
   */
  def getPlotCommand(filePath:String, smap:Map[String, String]) : String = {
    // http://stackoverflow.com/questions/4636610/regular-expression-and-pattern-matching-in-scala
    val pattern = "C([0-9]+)".r
    val params = (ArrayBuffer[String]() /: smap) { (acc, elem) =>
      elem._1 match {
        case pattern(c) => acc += s"""using 1:${c} title "${elem._2}" w lp"""
        case _ => throw new RuntimeException(s"Wrong format ${elem._1}")
      }
    }.toArray
    s"""plot "${filePath}" ${params.mkString(", ")}"""
  }

  def generateGnuplotCommands(smap:Map[String, String]): String = {
    //val smap = Map[String, String]("OUTPUT" -> "o", "TITLE" -> "t")
    var tmp = template
    smap.foreach { case (a, b) =>
      tmp = tmp.replace(a, b)
    }

    val pattern = "C([0-9]+)".r
    // find the elements C[0-9]+ format
    val plots = smap filter { x =>
      x._1 match {
        case pattern(r) => true
        case  _ => false
      }
    }
    tmp + "\n" + getPlotCommand(smap("FILEPATH"), plots)
  }

  def executeGnuplot(commandFile:String): Unit = {
    val gnuplot = getGnuplotPath
    val p = Process(s"${gnuplot} ${commandFile}")
    p.!!
  }

  // http://stackoverflow.com/questions/7195051/scala-getting-a-callback-when-an-external-process-exits
  // http://stackoverflow.com/questions/6013415/how-does-the-scala-sys-process-from-scala-2-9-work
  def plot(config:Map[String, String]): Unit = {
    val inputStream = new SyncVar[OutputStream];
    val gnuplot = getGnuplotPath
    val pb = Process(gnuplot)
    val pio = new ProcessIO(stdin => inputStream.put(stdin),
      stdout => Source.fromInputStream(stdout).getLines.foreach(println),
      stderr => Source.fromInputStream(stderr).getLines.foreach(println));

    //spawn { onExit(pb.exitValue())}

    pb.run(pio)
    val a = Array("set terminal gif",
      s"""set output "${config("outputFilepath")}""",
      s"""plot "${config("filepath")}" u 1:2 t "${config("label1")}" w line, "${config("filepath")}" u 1:3 t "${"label2"}" w line""",
      "exit").foreach { s =>
      inputStream.get.write((s + "\n").getBytes)
    }
    inputStream.get.close()
  }


}
