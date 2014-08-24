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

  def getRandomLatitude() :Option[LatitudeType] = {
    getLatitude(Simulation.getRandomByteArray(4))
  }

  def getRandomLongitude() :Option[LongitudeType] = {
    getLongitude(Simulation.getRandomByteArray(4))
  }

//  test ("getRandomByteArray") {
//    println(getRandomByteArray(4).mkString(":"))
//  }


  def nearMe(la:LatitudeType, lo:LongitudeType, range:Int) = {
    val distance = util.distance.Util.getDistance(latitudeHere, longitudeHere, la, lo)
    if (math.abs(distance) < range) true
    else false
  }

  def testIt(message:String) { // } ("") {
    println(message)

    var bottom = 0
    var non_bottom = 0
    var near_me = 0

    (1 to 500000).foreach { i =>
      val la = getRandomLatitude()
      val lo = getRandomLongitude()
      if (la.isEmpty || lo.isEmpty)
        bottom += 1
      else {
        non_bottom += 1
        if (nearMe(la.get, lo.get, 100)) {
          println(s"Near me: ${la.get.toDouble},${lo.get.toDouble}")
          near_me += 1
        }
      }
    }
    println(s"BOTTOM - ${bottom}, NON-BOTTOM - ${non_bottom}, NEAR_ME - ${near_me}")
  }
  testIt("Bottom number check")
}
