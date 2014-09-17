package util.measure

import core.{BloomierFilterSummary, GrapevineSummary}
import util.io.File

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * Created by smcho on 8/21/14.
 */
object Summary {
  def getSizeNormalDetail(summary:GrapevineSummary, byteWidth:Int) = {
    val bfs = new BloomierFilterSummary
    bfs.create(summary.getMap, m = -1, k = 3, q = byteWidth*8, complete=false)
    val x = bfs.getDetailedSize()
    bfs.create(summary.getMap, m = -1, k = 3, q = byteWidth*8, complete=true)
    val y = bfs.getDetailedSize()
    (x, y)
  }

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

  def getSizeAverage(summary:GrapevineSummary, byteWidth:Int, complete:Boolean, count:Int) = {
    val bfs = new BloomierFilterSummary
    val sizes = ArrayBuffer[Int]()
    (0 until count).foreach { c =>
      bfs.create(summary.getMap, m = -1, k = 3, q = byteWidth * 8, initialSeed = c, complete = complete)
      sizes += bfs.getSize
    }
    sizes.sum.toFloat / sizes.size
  }

  def getSizeNormalAverage(summary:GrapevineSummary, byteWidth:Int, count:Int=10) = {
    getSizeAverage(summary = summary, byteWidth = byteWidth, complete = false, count = count)
  }

  def getSizeCompleteAverage(summary:GrapevineSummary, byteWidth:Int, count:Int=10) = {
    getSizeAverage(summary = summary, byteWidth = byteWidth, complete = true, count = count)
  }


  def getSizes(summaryPath:String, start:Int = 1, stop:Int = 10) :Array[Array[AnyVal]] = {
    val summaries = File.fileToSummary(summaryPath)
    val summary = summaries(0)

    val listCount = (start to stop).toList
    var listBFNormal = ListBuffer[Float]()
    var listBFComplte = ListBuffer[Float]()

    val labeledSize = summary.getSize()
    listCount.foreach { i =>
      listBFNormal += getSizeNormal(summary, i) //getSizeNormalAverage(summary, i)
      listBFComplte += getSizeComplete(summary, i) //getSizeCompleteAverage(summary, i)
    }
//    listCount.foreach { i =>
//      println(s"${summaryPath}/${getSizeNormalDetail(summary, i)}")
//    }
    val totalSize = stop - start + 1
    val listLabeled = List.fill(totalSize)(summary.getSize())
    val listComplete = List.fill(totalSize)(summary.getCompleteSize())

    Array[Array[AnyVal]](listCount.toArray, listBFNormal.toArray, listLabeled.toArray, listBFComplte.toArray, listComplete.toArray)
  }
}
