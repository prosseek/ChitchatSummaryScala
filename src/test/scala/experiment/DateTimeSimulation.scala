package experiment

import grapevineType._
import org.scalatest.FunSuite
import util.conversion.experiment.Simulation

/**
 * Created by smcho on 8/22/14.
 */
class DateTimeSimulation extends FunSuite {

  val dateType = new DateType((2014,8,23))
  val timeType = new TimeType((13,0))

  def recentMe(d:DateType, range:Int) = {
    val distance = util.distance.Util.getDateDistance(dateType, d)
    if (math.abs(distance) < range) true
    else false
  }

  def futureMe(d:DateType, range:Int = -1) = {
    val distance = util.distance.Util.getDateDistance(dateType, d)
    if (distance >= 0) // check only future
      recentMe(d, range)
    else
      false
  }

  def getDate(ba:Array[Byte]) :Option[DateType] = {
    val d = new DateType
    if (d.fromByteArray(ba) == BottomType.NoError) {
      Some(d)
    }
    else None
  }

  def getRandomDate(size:Int = 2) :Option[DateType] = {
    getDate(Simulation.getRandomByteArray(size))
  }

  def getTime(ba:Array[Byte]) :Option[TimeType] = {
    val d = new TimeType
    if (d.fromByteArray(ba) == BottomType.NoError) {
      Some(d)
    }
    else None
  }

  def getRandomTime(size:Int = 2) :Option[TimeType] = {
    getTime(Simulation.getRandomByteArray(size))
  }

  test("Date") {
    var bottom = 0
    var non_bottom = 0
    var recent_me = 0
    var future_me = 0

    (1 to 100000).foreach { i =>
      val rd = getRandomDate(2)
      if (rd.isEmpty)
        bottom += 1
      else {
        non_bottom += 1
        if (recentMe(rd.get, 10)) {
          recent_me += 1
        }
        if (futureMe(rd.get, 10)) {
          //println(s"FUTURE TIME - ${rd.get.get}")
          future_me += 1
        }
      }
    }
    println(s"Date check: BOTTOM - ${bottom}, NON-BOTTOM - ${non_bottom}, NEAR_ME - ${recent_me}, FUTURE_ME - ${future_me}")
 }

  test("Time") {
    var bottom = 0
    var non_bottom = 0
    var near_me = 0

    (1 to 100000).foreach { i =>
      val rd = getRandomTime()
      if (rd.isEmpty)
        bottom += 1
      else {
        non_bottom += 1

      }
    }
    println(s"Time check: BOTTOM - ${bottom}, NON-BOTTOM - ${non_bottom}, NEAR_ME - ${near_me}")
  }
}
