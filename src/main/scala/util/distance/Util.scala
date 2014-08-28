package util.distance

import java.util.Date

import grapevineType._

/**
 * http://www.movable-type.co.uk/scripts/latlong.html
 * Pythagoras’ theorem can be used on an equirectangular projection:
 */
object Util {
  val R = 6371.0 // R of earth in kilo meters
  def getDistance(latitude1:LatitudeType, longitude1:LongitudeType, latitude2:LatitudeType, longitude2:LongitudeType) :Double = {
    getDistance(latitude1.toDouble, longitude1.toDouble, latitude2.toDouble, longitude2.toDouble)
  }

  def getDistance(latitude1:Double, longitude1:Double, latitude2:Double, longitude2:Double) :Double = {
    val longitudeDiff = math.abs(longitude1 - longitude2).toRadians
    val latitudeDiff = math.abs(latitude1 - latitude2).toRadians
    val latitudeAverage = (latitude1 + latitude2).toRadians/2
    val x = (longitudeDiff) * math.cos(latitudeAverage)
    val y = (latitudeDiff)
    math.sqrt(x*x + y*y) * R
  }

  def getDistance(latitude1:LatitudeType, latitude2:LatitudeType) :Double = {
    getDistance(latitude1.toDouble, 0.0, latitude2.toDouble, 0.0)
  }
  def getDistance(longitude1:LongitudeType, longitude2:LongitudeType) :Double = {
    getDistance(0.0, longitude1.toDouble, 0.0, longitude2.toDouble)
  }

  def toMile(kilo:Double) = 0.6214 * kilo

  def isWithinDays(standard:DateType, date:DateType, days:Int, both_directions:Boolean = false) = {
    val diff = getDateDistance(standard, date)

    var dateDiff = diff
    if (both_directions) {
      dateDiff = math.abs(diff)
      if (dateDiff <= days) true
      else false
    }
    else { // one direction
      if (dateDiff < 0) false
      else if (dateDiff <= days) true else false
    }

  }

  def getDateDistance(standard:DateType, date:DateType) = {
    val s = standard.value.asInstanceOf[(Int, Int, Int)]
    val d = date.value.asInstanceOf[(Int, Int, Int)]
    val diff = (new Date(d._1, d._2, d._3)).getTime() - (new Date(s._1, s._2, s._3)).getTime()
    diff / (24*60*60*1000)
  }

  def getTimeDistance(standard:TimeType, time:TimeType) = {
    val s = standard.value.asInstanceOf[(Int, Int)]
    val d = time.value.asInstanceOf[(Int, Int)]
    (d._1 * 60 + d._2) - (s._1 * 60 + s._2)
  }
}
