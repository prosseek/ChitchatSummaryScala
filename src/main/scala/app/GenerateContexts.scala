package app

import java.io.File

import grapevineType._
import util.conversion.Util
import util.gen.Summary

import scala.io.Source
import scala.util.Random
import scala.collection.mutable.{Set => MSet}

/**
 * Created by smcho on 8/24/14.
 */
object GenerateContexts extends App {
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

  var mapMap = Map[String, GrapevineType]("latitude" -> LatitudeType((30, 17, 14, 0)), "longitude" -> LongitudeType((-97, 44, 11, 6)))
  var nullMap = Map[String, GrapevineType]("latitude" -> null, "longitude" -> null)

  //var nullMap = Map[String, GrapevineType]()
  var mp: Map[String, GrapevineType] = nullMap
  //var mp = Map[String, GrapevineType]()
  val strs = readFromDictionary()

  var countFp = 0
  var countFp_r1 = 0

  val javascript1 = new StringBuilder()
  val javascript2 = new StringBuilder()

  (1 to 1000).foreach { i =>
    //println(s"count -> ${i}")
    if (i == 1) {
      mp = mapMap
    } // there should only one location in the contexts
    else {
      mp = nullMap
    }
    val bf = Summary.getRandomBF(strings = strs, summary = mp, m = -1, k = 3, byteWidth = 4, complete = true)


    //bf.save("experiment/tmp/random.txt")
    if (bf.check("latitude") == BottomType.NoError) {
      countFp += 1
      val lat = bf.get("latitude")

      if (bf.check("longitude") == BottomType.NoError) {
        countFp_r1 += 1
        val long = bf.get("longitude")
        println(s"${countFp}/${countFp_r1}: (${Util.dms2dd(lat)}, ${Util.dms2dd(long)})")
        bf.save(s"experiment/tmp/random${countFp_r1}.txt")

        javascript1.append(s"latlngArray[${countFp_r1-1}] = new google.maps.LatLng(${Util.dms2dd(lat)}, ${Util.dms2dd(long)});\n")
        javascript2.append(s"var marker${countFp_r1-1} = new google.maps.Marker({position : latlngArray[${countFp_r1-1}], map : map});\n")
      }
    }
  }
  println(javascript1.toString)
  println(javascript2.toString)
  //println(bf.getMap)
  //println(bf.check("hello"))
  //println(bf.get("hello"))
}
