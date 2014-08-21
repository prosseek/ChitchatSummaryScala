package util.gnuplot

import org.scalatest.FunSuite

/**
 * Created by smcho on 8/20/14.
 */
class TestGnuplot extends FunSuite {
  /**
   * Just plot interactive way
   */
  test ("Simple") {
    val m = Map[String, String]("outputFilepath" -> "experiment/tmp/hi.gif",
      "filepath" -> "experiment/tmp/p.txt", "label1"->"Hello", "label2"->"World")
    Gnuplotter.plot(m)
  }

  /**
   * Execute from command file
   */
  test ("executeGnuplot") {
    Gnuplotter.executeGnuplot("experiment/tmp/identification_rate.txt")
  }

  /**
   * Get plot command
   */
  test ("getPlotCommand") {
    val map = Map[String, String]("C1" -> "Hello", "C2" -> "World")
    assert(Gnuplotter.getPlotCommand("Hello", map) == """plot "Hello" using 1:1 title "Hello" w lp, using 1:2 title "World" w lp""")
  }

  /**
   * Generate gnuplot file from a map
   */
  test ("generateGif") {
    val smap = Map[String, String](
      "OUTPUT" -> "experiment/tmp/t1.gif",
      "TITLE" -> "Dense meshes",
      "XLABEL" -> "Node size",
      "YLABEL" -> "Identification rate (%)",
      "C3" -> "aggregates",
      "FILEPATH" -> "experiment/tmp/identification_rate.txt.data"
    )
    println(Gnuplotter.generateGnuplotCommands(smap))
  }
}
