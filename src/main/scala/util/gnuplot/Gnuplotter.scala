package util.gnuplot

import java.io.{File, FileWriter}

import scala.collection.mutable.ArrayBuffer
import scala.sys.process._

/**
 * Created by smcho on 8/20/14.
 */
object Gnuplotter {
  def getCurrentPath = new File(".").getAbsolutePath()

  // plot "/Users/smcho/tmp/imgs/gnuplot/mass/dense_meshes_dir/cohorts.txt.data" using 1:2 title "singles" w lp, "/Users/smcho/tmp/imgs/gnuplot/mass/dense_meshes_dir/cohorts.txt.data" using 1:3 title "aggregates" w lp
  val template = s"""set terminal pngcairo font 'DroidSerif'
                   |set output "#{PNGFILEPATH}"
                   |
                   |set yrange [0:190]
                   |set xtics font ", 15"
                   |set ytics font ", 15"
                   |set key font ",12"
                   |set key top left box
                   |set xlabel "#{XLABEL}" font ",20"
                   |set ylabel "#{YLABEL}" font ",20" offset 1,0
                   |set title "#{TITLE}" font ",25"
                   |set key spacing 1.5
                   |set key below
                   |""".stripMargin

  def getGnuplotPath = {
    if (System.getProperty("os.name").startsWith("Mac OS X"))
      "/usr/local/bin/gnuplot"
    else  // linux (ubuntu)
      "/usr/bin/gnuplot"
  }

  /**
   * smap : 1 -> "hello"
   * extract 1 and hello from the map and create the gnuplot code
   *
   * @param filePath
   * @param smap
   * @return
   */
  def getPlotCommand(filePath:String, smap:Map[Int, String]) : String = {
    // http://stackoverflow.com/questions/4636610/regular-expression-and-pattern-matching-in-scala
    val params = (ArrayBuffer[String]() /: smap) { (acc, elem) =>
      acc += s""" "${filePath}" using 1:${elem._1} title "${elem._2}" w lp lw 3"""
    }.toArray
    s"plot ${params.mkString(", ")}"
  }

  def generateGnuplotCommands(smap:Map[String, String]): String = {
    def selectColumnData() = {
      val pattern = "C([0-9]+)".r
      // find the elements C[0-9]+ format
      smap collect { case (pattern(r), v) => (r.toInt, v) }
    }
    def templateReplacement(template:String, replacements:Map[String, String]) :String = {
      // http://stackoverflow.com/questions/6110062/simple-string-template-replacement-in-scala-and-clojure
      val res = (template /: replacements)((s: String, x: (String, String)) => (s"#\\{${x._1}\\}").r.replaceAllIn(s, x._2))
      res
    }
    templateReplacement(template, smap) + "\n" + getPlotCommand(smap("DATAFILEPATH"), selectColumnData)
  }

  def executeGnuplot(commandFile:String): Unit = {
    val gnuplot = getGnuplotPath
    val p = Process(s"${gnuplot} ${commandFile}")
    p.!!
  }

  def using[A <: {def close() : Unit}, B](resource: A)(f: A => B): B =
    try f(resource) finally resource.close()
  def writeStringToFile(file: File, data: String, appending: Boolean = false) =
    using(new FileWriter(file, appending))(_.write(data))

  // http://stackoverflow.com/questions/7195051/scala-getting-a-callback-when-an-external-process-exits
  // http://stackoverflow.com/questions/6013415/how-does-the-scala-sys-process-from-scala-2-9-work
  // http://stackoverflow.com/questions/4604237/how-to-write-to-a-file-in-scala
  def plot[T <: AnyVal](config:Map[String, String], data:Array[Array[T]]): Unit = {
    // 1. create the gnuplot file
    writeStringToFile(new File(config("DATAFILEPATH")), generateData(data))
    writeStringToFile(new File(config("GNUPLOTFILEPATH")), generateGnuplotCommands(config))
    executeGnuplot(config("GNUPLOTFILEPATH"))
  }

  /**
   *
   * Array(Array(1,2,3),Array(4,5,6),Array(7,8,9)) -> "1 4 7\n 2 5 8\n 3 6 9\n"
   *
   * @param data
   * @return
   */
  def generateData[T <: AnyVal](data:Array[Array[T]]): String = {
    def getValueString[T <: AnyVal](v:Array[T]) = v.map { value => value.toString + " "} reduce {_ + _}

    val sb = new StringBuilder
    data.transpose.foreach { ar => sb.append(s"${getValueString(ar)}\n")}

    sb.toString
  }
}
