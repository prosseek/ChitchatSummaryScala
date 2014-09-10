package app

import core.BloomierFilterSummary
import grapevineType.{BottomType, GrapevineType, LatitudeType, LongitudeType}
import util.conversion.Util

import scala.collection.mutable.{Map => MMap}

/**
 * Created by smcho on 9/9/14.
 */
object LocationJavascriptGenerator extends App {
  var count = 0
  var countFp = 0
  var countFp_r1 = 0

  var mapMap = Map[String, GrapevineType]("latitude" -> LatitudeType((30, 17, 14, 0)), "longitude" -> LongitudeType((-97, 44, 11, 6)))
  val conf = MMap[String, Any]()
  conf("iteration") = 1000000
  conf("map") = mapMap

  def javaScript(i:Int, bf: BloomierFilterSummary) : Unit = {
    count += 1

    if (count % 1000 == 0)
      println(s"${count}")
    if (bf.check("latitude") == BottomType.NoError) {
      countFp += 1
      val lat = bf.get("latitude")

      if (bf.check("longitude") == BottomType.NoError) {
        countFp_r1 += 1
        val long = bf.get("longitude")
        println(s"${countFp}/${countFp_r1}: (${Util.dms2dd(lat)}, ${Util.dms2dd(long)})")
        bf.save(s"experiment/tmp/random${countFp_r1}.txt")

        println(s"latlngArray[${countFp_r1-1}] = new google.maps.LatLng(${Util.dms2dd(lat)}, ${Util.dms2dd(long)});\n")
        println(s"var marker${countFp_r1-1} = new google.maps.Marker({position : latlngArray[${countFp_r1-1}], map : map});\n")
      }
    }
  }

  //GenerateContexts.execute(configuration = conf.toMap, javaScript)
  GenerateContexts.parallelExecute(configuration = conf.toMap, javaScript)
  //println(bf.getMap)
  //println(bf.check("hello"))
  //println(bf.get("hello"))
}
