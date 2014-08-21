package util.io

import core.LabeledSummary

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
 * Created by smcho on 8/19/14.
 */
object File {
  def fileToSummary(filePath:String) = {
    val summaries = ArrayBuffer[LabeledSummary]()

    val r = Source.fromFile(filePath).mkString("")
    r.split("\n\n+").foreach { b =>
      summaries += String.parseSummary(b)
    }
    summaries.toArray
  }
}
