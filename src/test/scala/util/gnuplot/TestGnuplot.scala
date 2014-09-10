package util.gnuplot

import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 8/20/14.
 */
class TestGnuplot extends FunSuite with BeforeAndAfter {
  var smap:Map[String, String] = _
  before {
    smap = Map[String, String](
      "GNUPLOTFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/tmp/gnuplot.txt",
      "PNGFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/tmp/t1.gif",
      "TITLE" -> "Dense meshes",
      "XLABEL" -> "Node size",
      "YLABEL" -> "Identification rate (%)",
      "C3" -> "aggregates",
      "DATAFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/tmp/data.txt.data"
    )
  }

  test ("generateData") {
    val data = Array(Array(1,2,3), Array(2,3,4), Array(3,4,5))
    val result ="1 2 3 \n2 3 4 \n3 4 5 \n"
    assert(Gnuplotter.generateData(data) == result)
  }
  /**
   * Just plot interactive way
   */
 test ("Simple") {
    val data = Array(Array(1,2,3), Array(2,3,4), Array(3,4,5))
    Gnuplotter.plot(smap, data)
  }

  /**
   * Execute from command file
   */
  test ("executeGnuplot") {
    Gnuplotter.executeGnuplot("experiment/tmp/gnuplot.txt")
  }

  /**
   * Get plot command
   */
  test ("getPlotCommand") {
    val map = Map[Int, String](1 -> "Hello", 2 -> "World")
    //println(Gnuplotter.getPlotCommand("Hello", map))
    assert(Gnuplotter.getPlotCommand("Hello", map) == """plot  "Hello" using 1:1 title "Hello" w lp,  "Hello" using 1:2 title "World" w lp""")
  }

  /**
   * Generate gnuplot file from a map
   */
  test ("generateGif") {
    println(Gnuplotter.generateGnuplotCommands(smap))
  }
}
