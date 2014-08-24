package experiment

import org.scalatest.FunSuite
import util.io.File

/**
 * Created by smcho on 8/23/14.
 */
class SizeCheck extends FunSuite {
  test ("Simple") {
    val summaryPath = "experiment/scenario/s1.txt"
    val summaries = File.fileToSummary(summaryPath)
    val summary = summaries(0)

  }
}
