package simulation.grapevineType

import grapevineType._
import simulation.theoryFalsePositives.Time
import util.distance.{Util => DUtil}

/**
 * Created by smcho on 8/22/14.
 */
class TimeSimulation(config:Map[String, Int]) extends Simulation(config) {
  val dateType = new DateType((2014,8,23))
  val timeType = new TimeType((13,0))

  /*
    RELATIONAL CONSTRAINTS
   */
  def recentDate(d:DateType, range:Int) = {
    val distance = util.distance.Util.getDateDistance(dateType, d)
    if (math.abs(distance) < range) true
    else false
  }

  def futureDate(d:DateType, range:Int) = {
    val distance = util.distance.Util.getDateDistance(dateType, d)
    if (distance >= 0) // check only future
      recentDate(d, range)
    else
      false
  }

  /*
    GET RANDOM DATE/TIME
   */
  def getDate(ba:Array[Byte]) :Option[DateType] = {
    val d = new DateType
    if (d.fromByteArray(ba) == BottomType.NoError) {
      Some(d)
    }
    else None
  }

  def getRandomDate(size:Int = TimeType.getSize) :Option[DateType] = {
    getDate(Simulation.getRandomByteArray(size))
  }

  def getTime(ba:Array[Byte]) :Option[TimeType] = {
    val d = new TimeType
    if (d.fromByteArray(ba) == BottomType.NoError) {
      Some(d)
    }
    else None
  }

  def getRandomTime(size:Int = TimeType.getSize) :Option[TimeType] = {
    getTime(Simulation.getRandomByteArray(size))
  }

  /*
    GET THEORETICAL FP TIME/DATE
   */
  def getTheory(bytes:Int, near:Int, both_directions:Boolean) = {
    Map[String, Double](
      "theory_fp" -> Time.fp(bytes),
      "theory_fp_pair"-> Time.fp_pair(bytes),
      "theory_fp_near"-> Time.fp_near(bytes = bytes, near = near, both_directions = both_directions))
  }

  override def simulate(bytes:Int = 2) :Map[String, Double] = {
    var size = config("size")
    var bottom = 0
    var fp = 0
    var fp_pair = 0
    var fp_near = 0

    val interest_days = config("interest_days")
    val both_directions = config("both_directions") == 1
    var bts = math.max(bytes, TimeType.getSize)
    
    (1 to size).foreach { i =>
      val tm = getRandomTime(bts)
      val dt = getRandomDate(bts)
      if (tm.isEmpty)
        bottom += 1
      else {
        fp += 1
        if (dt.isDefined) {
          fp_pair +=1
          if (DUtil.isWithinDays(dateType, dt.get, interest_days , both_directions = both_directions)) {
            fp_near += 1
          }
        }
      }
    }
    Map[String, Double](
      "fp" -> fp.toDouble/size,
      "fp_pair"->fp_pair.toDouble/size,
      "fp_near"->fp_near.toDouble/size
    )  ++ getTheory(bytes = bts, near = interest_days, both_directions = both_directions)
  }
}

object TimeSimulation extends App {
  var m = Map[String,Int]("size" -> 100000, "interest_days" -> 60, "iteration" -> 1, "verbose" ->1, "both_directions" -> 1)
  var ls = new TimeSimulation(m)
  val f = (i :Int) => ls.simulate(bytes = i)

  (1 to 1).foreach { i =>
    println(s"SIMULATION TIME - FOR WIDTH ${i}")
    println(s"${Util.map2string(f(i))}")
  }

//  m = Map[String,Int]("size" -> 100000, "interest_days" -> 60, "iteration" -> 3, "verbose" ->0, "both_directions" -> 1)
//  ls = new TimeSimulation(m)
//  val res = ls.simulateOverWidth(1,1)
//  println(res.mkString("\n\n"))

//  (1 to 1).foreach { i =>
//    val res =ls.simulate(bytes = i)
//    println(res.mkString("", "\n", "") + "\n")
//  }
}