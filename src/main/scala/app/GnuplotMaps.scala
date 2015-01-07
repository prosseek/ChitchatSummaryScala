package app

import util.gnuplot.Gnuplotter

/**
 * Created by smcho on 8/25/14.
 */
trait GnuplotMaps {
  def getMapForSize(summaryName:String): Map[String, String] = {
    Map[String, String](
      "GNUPLOTFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/size/${summaryName}.txt",
      "PNGFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/size/${summaryName}.png",
      "TITLE" -> "Size comparison",
      "XLABEL" -> "Data width in bytes",
      "YLABEL" -> "Summary size",
      "C3" -> "Labeled",
      "C4" -> "Labeled (zipped)",
      "C7" -> "BF",
      "C10" -> "CBF",
      "C13" -> "Complete",
      "DATAFILEPATH" -> s"${Gnuplotter.getCurrentPath}/experiment/size/${summaryName}.data"
    )
  }
}
