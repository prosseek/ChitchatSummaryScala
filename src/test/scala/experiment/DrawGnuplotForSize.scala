package experiment

import org.scalatest._
import util.conversion.experiment.Run
import util.gnuplot.Gnuplotter

/**
 * Created by smcho on 8/20/14.
 */
class DrawGnuplotForSize extends FunSuite with BeforeAndAfter {
  def getMapForSize(summaryName:String): Map[String, String] = {
    Map[String, String](
      "GNUPLOTFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/size/${summaryName}.txt",
      "GIFFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/size/${summaryName}.gif",
      "TITLE" -> "Size comparison",
      "XLABEL" -> "Data width in bytes",
      "YLABEL" -> "Summary size",
      "C2" -> "BF",
      "C3" -> "Labeled",
      "C4" -> "BF Complete",
      "C5" -> "Complete",
      "DATAFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/size/${summaryName}.data"
    )
  }

  test ("Simple test: bytes vs size") {
    //var arrays = Run.getSizes(summaryPath="experiment/contextsForTest/summary1.txt")
    //Gnuplotter.plot(getMapForSize("summary1"), arrays)

    val arrays = Run.getSizes(summaryPath="experiment/contextsForTest/summary2.txt")
    Gnuplotter.plot(getMapForSize("summary2"), arrays)
  }
}
