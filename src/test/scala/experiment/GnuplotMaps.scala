package experiment

import util.gnuplot.Gnuplotter

/**
 * Created by smcho on 8/25/14.
 */
trait GnuplotMaps {
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
}
