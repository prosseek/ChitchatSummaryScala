package app

import util.measure.Summary
import util.gnuplot.Gnuplotter

/**
 * Created by smcho on 8/20/14.
 */
object DrawGnuplotForSize extends App with GnuplotMaps {

  def testIt(message:String) { // } () {
    println(message)
    //var arrays = Run.getSizes(summaryPath="experiment/contextsForTest/summary1.txt")
    //Gnuplotter.plot(getMapForSize("summary1"), arrays)

//    val arrays = Run.getSizes(summaryPath="experiment/contextsForTest/summary2.txt")
//    Gnuplotter.plot(getMapForSize("summary2"), arrays)

    val files = Array[String]("string_only", "no_string","s1_1", "s1_2", "s2_1", "s2_2", "s3_1", "s3_2")
//    val files = Array[String]("s1_2")

    files.foreach { f =>
      val file = s"experiment/scenario/${f}.txt"
      println("Working on %s".format(file))
      val arrays = Summary.getSizes(summaryPath=file, start = 1, stop = 11)
      //val array2 = Summary.getZippedSizes(summaryPath=file, start = 1, stop = 11)
      Gnuplotter.plot(getMapForSize(s"${f}"), arrays)
    }
  }

  testIt("Simple test: bytes vs size")
}
