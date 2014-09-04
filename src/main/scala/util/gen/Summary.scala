package util.gen

import core.{BloomierFilterSummary, ContextSummary}
import grapevineType._
import util.io.File
import scala.collection.mutable.{Map => MMap}
import scala.util.Random

/**
 * Created by smcho on 8/21/14.
 */
object Summary {

  def getRandomString(strings:Array[String]) :String = {
    val index = Random.nextInt(strings.size)
    strings(index)
  }

  def getRandomBF(strings:Array[String], summary:Map[String, GrapevineType], m:Int, k:Int, byteWidth:Int, complete:Boolean) = {
    val mp = MMap[String, Any]() ++= summary

    // +5 should be present to prevent making size 0 map
    (0 until Random.nextInt(10) + 5).foreach { i =>
      mp += (getRandomString(strings) -> StringType(getRandomString(strings)))
    }

    val bfs = new BloomierFilterSummary
    bfs.create(mp.toMap, m = -1, k = k, q = byteWidth*8, complete=complete)
    bfs
  }

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
