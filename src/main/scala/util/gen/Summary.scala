package util.gen

import core.{BloomierFilterSummary, ContextSummary}
import grapevineType.GrapevineType
import util.io.File

/**
 * Created by smcho on 8/21/14.
 */
object Summary {
  def getBF(summary:Map[String, GrapevineType], m:Int = -1, k:Int = 3, byteWidth:Int=4, complete:Boolean = false) = {
    val bfs = new BloomierFilterSummary
    bfs.create(summary, m = m, k = k, q = byteWidth*8, complete=complete)
    bfs
  }

  def getLabeledSummary(summaryPath:String) = {
    val summaries = File.fileToSummary(summaryPath)
    summaries(0)
  }

  def getBF(summaryPath:String, m:Int, k:Int, byteWidth:Int, complete:Boolean) :ContextSummary = {
    val summaries = File.fileToSummary(summaryPath)
    val summary = summaries(0)

    getBF(summary.getMap, m = m, k = k, byteWidth = byteWidth, complete = complete)
  }
}
