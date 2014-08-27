package simulation

import grapevineType._

/**
 * Created by smcho on 8/22/14.
 */
class LocationSimulation extends Simulation {
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
  def theory_location(lat:Boolean) :Double = {
    (if (lat) 180.0 else 360.0)*60*60*100 / (1.toLong << 32)
  }

  def getFp(near:Int, bytes:Int = 4, lat:Boolean = true) :Map[String, Double] = {
    val RADIUS_OF_EARTH = 6387.1
    val fp :Double = theory_location(lat)
    val fp_pair = theory_location(true) * theory_location(false)
    val fp_near = fp_pair * 0.25 * (near.toDouble/RADIUS_OF_EARTH)*(near.toDouble/RADIUS_OF_EARTH)

    Map[String, Double](
      "theory_fp" -> Util.reduced(fp, totalBytes = bytes, thresholdBytes = LatitudeType.getSize),
      "theory_fp_pair" -> Util.reduced(fp_pair, totalBytes = bytes, thresholdBytes = LatitudeType.getSize),
      "theory_fp_near" -> Util.reduced(fp_near, totalBytes = bytes, thresholdBytes = LatitudeType.getSize)
    )
  }

  def simulate(message:String, byteWidth:Int, size:Int, near:Int) = position(message, byteWidth, size, near, lat = true)
  def longitude(message:String, byteWidth:Int, size:Int, near:Int) = position(message, byteWidth, size, near, lat = false)

  /*
    GENERATE TABLES - RUN TESTS
  */
  def position(message:String, bs:Int, size:Int, near:Int, lat:Boolean) = {
    // println(message)
    var bytes = math.max(bs, FloatType.getSize)
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
            // println(s"Near me: ${la.get.toDouble},${lo.get.toDouble}")
            bottom_relation_near += 1
          }
          else {
            fp_near += 1
          }
        }
      }
    }

    Map[String, Double](
      //"bottom_computation" -> bottom_computation/size.toDouble,
      "fp" -> fp/size.toDouble,
      //"bottom_relation_pair" -> bottom_relation_pair/size.toDouble,
      "fp_pair" -> fp_pair/size.toDouble,
      //"bottom_relation_near" -> bottom_relation_near/size.toDouble,
      "fp_near" -> fp_near/size.toDouble
    ) ++ getFp(near, bytes, lat)
  }

  override def simulate(message:String, width:Int, m: Map[String, Int]) :Map[String,Double] = {
    simulate(message = message,
      byteWidth = width,
      size = m("size"),
      near = m("near"))
  }
}

object LocationSimulation extends App {
  val ls = new LocationSimulation
  val m = Map[String,Int]("size" -> 10000, "near" -> 10)

  (3 to 5).foreach { i =>
    val res = ls.simulate("Latitude", width = i, m)
    println(res.mkString("", "\n", "") + "\n")
  }
}