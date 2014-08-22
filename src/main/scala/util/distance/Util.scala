package util.distance

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
}
