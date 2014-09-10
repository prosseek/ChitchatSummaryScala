package util.gen

import core.{BloomierFilterSummary, ContextSummary}
import grapevineType._
import util.io.File
import scala.collection.mutable.{Map => MMap}
import scala.io.Source
import scala.util.Random
import scala.collection.mutable.{Set => MSet}

/**
 * Created by smcho on 8/21/14.
 */
object Summary {

  def bottomTheory(m:Int, n:Int, k:Int) = {
    (1.0 /: (0 until k)) { (acc, value) =>
      acc * (m - n - value).toDouble / (m - value).toDouble
    }
  }

  def getDictionaryStrings() :Array[String] = {
    val lines = Source.fromFile("experiment/data/words.txt").getLines()
    //var count = lines.size
    val s = MSet[String]()

    lines.foreach { l =>
      val randValue = Random.nextInt(20)
      if (l.size > 4 && randValue == 0) {
        s += l
        if (s.size >= 1000) return s.toArray
      }
    }
    assert(s.size > 0)
    s.toArray
  }

  def getBFfromMNK(m:Int, n:Int, k:Int, byteWidth:Int=4, a:Array[String] = null, t:String = "string") = {
    var strs = a
    if (a == null)
      strs = getDictionaryStrings()

    val mp = getRandomMap(strs, n, t)
    val bfs = new BloomierFilterSummary

    bfs.create(map=mp, m = m, k = k, q = byteWidth * 8, complete = false)
    bfs
    //bfs.create()
  }

  def getRandomString(strings:Array[String]) :String = {
    val index = Random.nextInt(strings.size)
    strings(index)
  }

  def getRandomByte() = {
    Random.nextInt(1 << 8).toByte
  }

  def getRandomMap(strings:Array[String], size:Int, t:String = "string") = {
    val mp = MMap[String, Any]()

    (0 until size).foreach { i =>
      val bt = ByteType(getRandomByte())
      val res:Any = if (t == "string") StringType(getRandomString(strings)) else bt
      val key:String = if (t == "string") getRandomString(strings) else s"level of ${getRandomString(strings)}"
      mp += (key -> res)
    }
    mp.toMap
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
