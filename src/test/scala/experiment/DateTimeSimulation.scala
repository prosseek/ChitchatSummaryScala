package experiment

import grapevineType._
import util.distance.Util
import util.experiment.Simulation

/**
 * Created by smcho on 8/22/14.
 */
object DateTimeSimulation extends App {

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

  /*
    GET THEORETICAL FP TIME/DATE
   */

  def theory_fp_date(years:Int) :Double = {
    (years.toDouble*365) / (1.toLong << 16)
  }
  def theory_fp_time :Double = {
    (24.0*60.0) / (1.toLong << 16)
  }
  def theory_withinDays(range:Int, bothDirection:Boolean=false) :Double = {
    val res = range.toDouble/(365.0*10.0)
    if (bothDirection) res*2 else res
  }

  def getFpTime() = {
    val theory_fp :Double = theory_fp_time
    val theory_pair = theory_fp * theory_fp_date(10)
    val theory_near = theory_fp * 7.0/(1.toLong << 16) // theory_withinDays(7)

    Map[String, Double](
      "theory_fp" -> theory_fp,
      "theory_pair"->theory_pair,
      "theory_near"->theory_near
    )
  }

//  def testDate(size:Int = 100000):Unit = {
//    var bottom = 0
//    var fp = 0
//    var fp_pair = 0
//    var fp_near = 0
//
//    (1 to size).foreach { i =>
//      val rd = getRandomDate(2)
//      if (rd.isEmpty)
//        bottom += 1
//      else {
//        fp += 1
//        if (recentDate(rd.get, 10)) {
//          recent_me += 1
//        }
//        if (futureDate(rd.get, 10)) {
//          //println(s"FUTURE TIME - ${rd.get.get}")
//          future_me += 1
//        }
//      }
//    }
//    println(s"Date check: BOTTOM - ${fp.toDouble/size}, NON-BOTTOM - ${theory_fp_date(10)}")
// }

  def testTime(size:Int = 100000) :Map[String, Double] = {
    var bottom = 0
    var fp = 0
    var fp_pair = 0
    var fp_near = 0

    (1 to size).foreach { i =>
      val tm = getRandomTime()
      val dt = getRandomDate()
      if (tm.isEmpty)
        bottom += 1
      else {
        fp += 1
        if (dt.isDefined) {
          fp_pair +=1
//          val fd = new DateType
//          fd.set(dt.get)
          // check only 7 days
          if (Util.isWithinDays(dateType, dt.get, 7, bothDirection = false)) {
            fp_near += 1
            println(dt.get.get)
          }
//          else {
//            println(dt.get.get)
//          }
        }
      }
    }
    Map[String, Double](
      "fp" -> fp.toDouble/size,
      "fp_pair"->fp_pair.toDouble/size,
      "fp_near"->fp_near.toDouble/size
    ) ++ getFpTime
  }

  //testDate()
  val res = testTime(1000000)
  println(res.mkString("","\n",""))
}
