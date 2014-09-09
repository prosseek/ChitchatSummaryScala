package app

import java.io.File

import core.BloomierFilterSummary
import grapevineType.GrapevineType
import util.gen.Summary

import scala.collection.mutable.{Set => MSet}
import scala.io.Source
import scala.util.Random

/**
 * Created by smcho on 8/24/14.
 */
object GenerateContexts {
  //simulation.Util.generateRandomContextSummary()
  def getTempContextName() = {
    var tempFile = File.createTempFile("temp", "%06d".format(1))
    tempFile
  }

  def readFromDictionary() :Array[String] = {
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

  def execute(configuration:Map[String, Any], f:(Int, BloomierFilterSummary) => Unit) = {
    // var mapMap = Map[String, GrapevineType]("latitude" -> LatitudeType((30, 17, 14, 0)), "longitude" -> LongitudeType((-97, 44, 11, 6)))
    var nullMap = Map[String, GrapevineType]() // ("latitude" -> null, "longitude" -> null)
    val mapMap: Map[String, GrapevineType] = configuration("map").asInstanceOf[Map[String, GrapevineType]]
    val iteration:Int = configuration.getOrElse("iteration", 10000).asInstanceOf[Int]
    val number:Int = configuration.getOrElse("number", 1).asInstanceOf[Int]
    val m:Int = configuration.getOrElse("m", -1).asInstanceOf[Int]
    val k:Int = configuration.getOrElse("k", 3).asInstanceOf[Int]
    val byteWidth:Int = configuration.getOrElse("byteWidth", 4).asInstanceOf[Int]
    val complete:Boolean = configuration.getOrElse("complete", false).asInstanceOf[Boolean]

    var mp: Map[String, GrapevineType] = nullMap
    val strs = GenerateContexts.readFromDictionary()

    (1 to iteration).foreach { i =>
      //println(s"count -> ${i}")
      if (i >= 1 && i < (1 + number)) {
        mp = mapMap
      } // there should only one location in the contexts
      else {
        mp = nullMap
      }
      val bf = Summary.getRandomBF(strings = strs, summary = mp, m = m, k = k, byteWidth = byteWidth, complete = complete)
      // run the given code
      f(i, bf)
    }
  }
}
