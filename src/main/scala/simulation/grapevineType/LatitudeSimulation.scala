package simulation.grapevineType

import grapevineType._
import simulation.theoryFalsePositives.Latitude

import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/22/14.
 */
class LatitudeSimulation(config:Map[String, Int]) extends Simulation(config) {
  /*
    BASE DATA
   */
  val latitudeHere = new LatitudeType((30, 25, 7, 1))
  val longitudeHere = new LongitudeType((-97, 53, 24, 9))

  /*
    GET LATITUDE/LONGITUDE POSITION DATA
  */
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

  /*
    GET RELATIONAL CONSTRAINTS
  */
  def nearMe(la:LatitudeType, lo:LongitudeType, range:Int) = {
    val distance = util.distance.Util.getDistance(latitudeHere, longitudeHere, la, lo)
    if (math.abs(distance) < range) true
    else false
  }

  /*
    GET THEORETICAL FP TIME/DATE
  */
  def getTheory(near:Int, bytes:Int = LatitudeType.getSize) :Map[String, Double] = {
    Map[String, Double](
      "theory_fp" -> Latitude.fp(bytes),
      "theory_fp_pair" -> Latitude.fp_pair(bytes),
      "theory_fp_near" -> Latitude.fp_near(bytes = bytes, near = near)
    )
  }

  //def longitude(message:String, byteWidth:Int, size:Int, near:Int) = position(message, byteWidth, size, near, lat = false)

  /*
    GENERATE TABLES - RUN TESTS
  */
  def position(width:Int, size:Int, near:Int, lat:Boolean) = {
    var bytes = math.max(width, FloatType.getSize)
    var bottom_computation = 0
    var fp = 0
    var bottom_relation_pair = 0
    var fp_pair = 0
    var bottom_relation_near = 0
    var fp_near = 0

    (1 to size).foreach { i =>
      val la = getRandomLatitude(bytes)
      val lo = getRandomLongitude(bytes)
      if (if (lat) la.isEmpty else lo.isEmpty)
        bottom_computation += 1 // get bottom_computation
      else {
        fp += 1 // survived the bottom

        if (if (lat) lo.isEmpty else la.isEmpty) { // if longitude is None, we know it's bottom
          bottom_relation_pair += 1
        }
        else {
          fp_pair += 1

          // we check if the survived location is near me
          if (!nearMe(la.get, lo.get, near)) {
            bottom_relation_near += 1
          }
          else {
            fp_near += 1
          }
        }
      }
    }

    Map[String, Double](
      "fp" -> fp/size.toDouble,
      "fp_pair" -> fp_pair/size.toDouble,
      "fp_near" -> fp_near/size.toDouble
    ) ++ getTheory(near, bytes)
  }

  override def simulate(width:Int) :Map[String,Double] = {
    val iteration = config("iteration")
    runAndAverage(iteration = iteration, f = () => position(width = width, size = config("size"), near = config("near"), lat = true))
  }
}

// Just test code to print out
object LatitudeSimulation extends App {
  var m = Map[String,Int]("size" -> 10000, "near" -> 10, "iteration" -> 10, "verbose" -> 0)
  var ls = new LatitudeSimulation(m)
  val f = (i :Int) => ls.simulate(width = i)

  (1 to 4).foreach { i =>
    println(s"SIMULATION FOR WIDTH ${i}")
    println(s"${Util.map2string(f(i))}")
  }

  m = Map[String,Int]("size" -> 100, "near" -> 10, "iteration" -> 10, "verbose" ->0)
  ls = new LatitudeSimulation(m)
  val res = ls.simulateOverWidth(1,10)
  println(res.mkString("\n\n"))
}

/*
3 - fp:0.015277
theory_fp:0.015087425708770752
fp_near:0.0
theory_fp_near:2.789927142500787E-10
fp_pair:4.37E-4
theory_fp_pair:4.5526082903535325E-4

4 - fp:0.015174
theory_fp:0.015087425708770752
fp_near:0.0
theory_fp_near:2.789927142500787E-10
fp_pair:4.58E-4
theory_fp_pair:4.5526082903535325E-4

5 - fp:6.1E-5
theory_fp:5.893525667488575E-5
fp_near:0.0
theory_fp_near:4.257090976716289E-15
fp_pair:0.0
theory_fp_pair:6.946728958669331E-9

6 - fp:0.0
theory_fp:2.3021584638627246E-7
fp_near:0.0
theory_fp_near:6.495805323358595E-20
fp_pair:0.0
theory_fp_pair:1.059986718546956E-13

in theory, pair/near drops 1.525E-5 for each byte

*/