package experiment

import grapevineType._
import org.scalatest.FunSuite
import util.conversion.experiment.Simulation

/**
 * Created by smcho on 8/22/14.
 */
class OneBitSimulation extends FunSuite {

  val oneBit = new BitType

  def getOneBit(ba:Array[Byte]) :Option[BitType] = {
    val d = new BitType
    if (d.fromByteArray(ba) == BottomType.NoError) {
      Some(d)
    }
    else None
  }

  def getRandomBit(size:Int = 1) :Option[BitType] = {
    getOneBit(Simulation.getRandomByteArray(size))
  }

  test("OneBit") {
    var bottom = 0
    var non_bottom = 0
    (1 to 100000).foreach { i =>
      val rd = getRandomBit(1)
      if (rd.isEmpty)
        bottom += 1
      else {
        non_bottom += 1
      }
    }
    println(s"Date check: BOTTOM - ${bottom}, NON-BOTTOM - ${non_bottom}")
  }
}