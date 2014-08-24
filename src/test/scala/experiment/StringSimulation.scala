package experiment

import grapevineType.{BottomType, StringType}
import util.experiment.Simulation

import scala.util.Random

/**
 * Created by smcho on 8/24/14.
 */
object StringSimulation extends App  {
  val random = new Random

  def getString(ba:Array[Byte]) :Option[StringType] = {
    val stringType = new StringType
    if (stringType.fromByteArray(ba) == BottomType.NoError) {
      Some(stringType)
    }
    else None
  }

  def getRandomString() :Option[StringType] = {
    val x = random.nextInt(256)
    getString(Simulation.getRandomByteArray(x))
  }

  def testIt(message:String) {
    println(message)

    var bottom = 0
    var non_bottom = 0
    var near_me = 0

    (1 to 100000).foreach { i =>
      val ra = getRandomString()
      if (ra.isEmpty)
        bottom += 1
      else {
        non_bottom += 1
        println(s"${ra.get.get}")
      }
    }
    println(s"BOTTOM - ${bottom}, NON-BOTTOM - ${non_bottom}, NEAR_ME - ${near_me}")
  }

  testIt("Bottom number check")
}