package experiment

import grapevineType._
import util.experiment.Simulation

/**
 * Created by smcho on 8/22/14.
 */
object LocationSimulation extends App {
  val latitudeHere = new LatitudeType((30, 25, 7, 1))
  val longitudeHere = new LongitudeType((-97, 53, 24, 9))

  def getLatitude(ba:Array[Byte]) :Option[LatitudeType] = {
    val latitude = new LatitudeType
    if (latitude.fromByteArray(ba) == BottomType.NoError) {
      Some(latitude)
    }
    else None
  }

  def getLongitude(ba:Array[Byte]) :Option[LongitudeType] = {
    val longitude = new LongitudeType
    if (longitude.fromByteArray(ba) == BottomType.NoError) {
      Some(longitude)
    }
    else None
  }

  def getRandomLatitude(byteWidth:Int = 4) :Option[LatitudeType] = {
    getLatitude(Simulation.getRandomByteArray(byteWidth))
  }

  def getRandomLongitude(byteWidth:Int = 4) :Option[LongitudeType] = {
    getLongitude(Simulation.getRandomByteArray(byteWidth))
  }

  def nearMe(la:LatitudeType, lo:LongitudeType, range:Int) = {
    val distance = util.distance.Util.getDistance(latitudeHere, longitudeHere, la, lo)
    if (math.abs(distance) < range) true
    else false
  }

  def latitude(message:String, byteWidth:Int, size:Int, near:Int) = {
    println(message)

    var bottom_computation = 0
    var fp_computation = 0
    var bottom_relation_pair = 0
    var fp_relation_pair = 0
    var bottom_relation_near = 0
    var fp_relation_near = 0

    (1 to size).foreach { i =>
      val la = getRandomLatitude(byteWidth)
      val lo = getRandomLongitude(byteWidth)
      if (la.isEmpty)
        bottom_computation += 1 // get bottom_computation
      else {
        fp_computation += 1 // survived the bottom

        if (lo.isEmpty) { // if longitude is None, we know it's bottom
          bottom_relation_pair += 1
        }
        else {
          fp_relation_pair += 1

          // we check if the survived location is near me

          if (!nearMe(la.get, lo.get, near)) {
            // println(s"Near me: ${la.get.toDouble},${lo.get.toDouble}")
            bottom_relation_near += 1
          }
          else {
            fp_relation_near += 1
          }
        }
      }
    }

    Map[String, Double](
      "bottom_computation" -> bottom_computation/size.toDouble,
      "fp_computation" -> fp_computation/size.toDouble,
      "bottom_relation_pair" -> bottom_relation_pair/size.toDouble,
      "fp_relation_pair" -> fp_relation_pair/size.toDouble,
      "bottom_relation_near" -> bottom_relation_near/size.toDouble,
      "fp_relation_near" -> fp_relation_near/size.toDouble
    )
  }
  val res = latitude("Latitude check", byteWidth=4, size=100000, near = 10)
  println(res.mkString("","\n",""))
}
