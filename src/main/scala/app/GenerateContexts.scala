package app

import java.io.File

import core.BloomierFilterSummary
import grapevineType.GrapevineType
import util.gen.Summary

import scala.actors.Actor._
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

  def parallelExecute(configuration:Map[String, Any], f:(Int, BloomierFilterSummary) => Unit) = {
    // var mapMap = Map[String, GrapevineType]("latitude" -> LatitudeType((30, 17, 14, 0)), "longitude" -> LongitudeType((-97, 44, 11, 6)))
    var nullMap = Map[String, GrapevineType]() // ("latitude" -> null, "longitude" -> null)
    val mapMap: Map[String, GrapevineType] = configuration.getOrElse("map", nullMap).asInstanceOf[Map[String, GrapevineType]]
    val iteration:Int = configuration.getOrElse("iteration", 10000).asInstanceOf[Int]
    val number:Int = configuration.getOrElse("number", 1).asInstanceOf[Int]
    val m:Int = configuration.getOrElse("m", -1).asInstanceOf[Int]
    val k:Int = configuration.getOrElse("k", 3).asInstanceOf[Int]
    var byteWidth:Int = configuration.getOrElse("byteWidth", -1).asInstanceOf[Int]
    val complete:Boolean = configuration.getOrElse("complete", false).asInstanceOf[Boolean]
    var bf:BloomierFilterSummary = configuration.getOrElse("bf", null).asInstanceOf[BloomierFilterSummary]

    var mp: Map[String, GrapevineType] = nullMap
    val strs = Summary.getDictionaryStrings()

    val caller = self
    (1 to iteration).foreach { i =>
      actor {
        caller ! {
          if (i >= 1 && i < (1 + number)) {
            mp = mapMap
          } // there should only one location in the contexts
          else {
            mp = nullMap
          }

          if (bf == null) {
            if (byteWidth == -1) {
              byteWidth = Random.nextInt(6) + 1
            }
            val newBf = Summary.getRandomBF(strings = strs, summary = mp, m = m, k = k, byteWidth = byteWidth, complete = complete)
            // run the given code
            f(i, newBf)
          }
          else {
            f(i, bf)
          }
        }
      }
    }

    (1 to iteration).foreach { i =>
      receive {
        case msg => msg
      }
    }
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
    var bf:BloomierFilterSummary = configuration.getOrElse("bf", null).asInstanceOf[BloomierFilterSummary]

    var mp: Map[String, GrapevineType] = nullMap
    val strs = Summary.getDictionaryStrings()

    (1 to iteration).foreach { i =>
      if (i >= 1 && i < (1 + number)) {
        mp = mapMap
      } // there should only one location in the contexts
      else {
        mp = nullMap
      }

      if (bf == null) {
        val newBf = Summary.getRandomBF(strings = strs, summary = mp, m = m, k = k, byteWidth = byteWidth, complete = complete)
        // run the given code
        f(i, newBf)
      }
      else {
        f(i, bf)
      }
    }
  }
}
