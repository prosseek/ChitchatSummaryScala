package util.measure

import core.{BloomierFilterSummary, GrapevineSummary}
import util.io.File

import scala.collection.mutable.ListBuffer

/**
 * Created by smcho on 8/21/14.
 */
object Summary {
  def getSizeNormal(summary:GrapevineSummary, byteWidth:Int) = {
    val bfs = new BloomierFilterSummary
    bfs.create(summary.getMap, m = -1, k = 3, q = byteWidth*8)
    bfs.getSize
  }
  def getSizeComplete(summary:GrapevineSummary, byteWidth:Int) = {
    val bfs = new BloomierFilterSummary
    bfs.create(summary.getMap, m = -1, k = 3, q = byteWidth*8, maxTry = 20, complete=true)
    bfs.getSize
  }

  def getSizes(summaryPath:String, start:Int = 1, stop:Int = 10) :Array[Array[AnyVal]] = {
    val summaries = File.fileToSummary(summaryPath)
    val summary = summaries(0)

    val listCount = (start to stop).toList
    var listBFNormal = ListBuffer[Int]()
    var listBFComplte = ListBuffer[Int]()

    val labeledSize = summary.getSize()
    listCount.foreach { i =>
      listBFNormal += getSizeNormal(summary, i)
      listBFComplte += getSizeComplete(summary, i)
    }

    val listLabeled = List.fill(10)(summary.getSize())
    val listComplete = List.fill(10)(summary.getCompleteSize())

    Array[Array[AnyVal]](listCount.toArray, listBFNormal.toArray, listLabeled.toArray, listBFComplte.toArray, listComplete.toArray)
  }
}
