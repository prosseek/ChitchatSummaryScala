package util.io

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

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

  def resultsToFile(filePath:String, results:String) = {
    Files.write(Paths.get(filePath), results.getBytes(StandardCharsets.UTF_8))
  }
}
