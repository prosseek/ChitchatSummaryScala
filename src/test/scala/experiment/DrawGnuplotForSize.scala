package experiment

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

    val arrays = SizeExperiment.getSizes(summaryPath="experiment/scenario/s4.txt")
    Gnuplotter.plot(getMapForSize("scenario4"), arrays)
  }

  testIt("Simple test: bytes vs size")
}
