package simulation.grapevineType

import grapevineType.BitType
import grapevineType.BottomType
import simulation.theoryFalsePositives.Bit

/**
 * Created by smcho on 9/5/14.
 */
class BitSimulation (config:Map[String, Int]) extends Simulation(config) {
  def getBit(ba:Array[Byte]) = {
    val bit = new BitType
    if (bit.fromByteArray(ba) == BottomType.NoError) {
      Some(bit)
    }
    else None
  }

  def getRandomBit(byteWidth:Int = 1) = {
    getBit(Simulation.getRandomByteArray(byteWidth))
  }

  /*
    GET THEORETICAL FP TIME/DATE
  */
  def getTheory(bytes:Int = BitType.getSize) :Map[String, Double] = {
    Map[String, Double](
      "theory_fp" -> Bit.fp(bytes)
    )
  }
  /*
    GENERATE TABLES - RUN TESTS
  */
  def run(width:Int, size:Int, near:Int, lat:Boolean) = {
    var bytes = math.max(width, BitType.getSize)
    var bottom_computation = 0
    var fp = 0
    var bottom_relation_pair = 0
    var fp_pair = 0
    var bottom_relation_near = 0
    var fp_near = 0

    (1 to size).foreach { i =>
      val la = getRandomBit(bytes)
      if (la.isEmpty)
        bottom_computation += 1 // get bottom_computation
      else
        fp += 1 // survived the bottom
    }

    Map[String, Double](
      "fp" -> fp/size.toDouble
    ) ++ getTheory(bytes)
  }

  override def simulate(width:Int) :Map[String,Double] = {
    val iteration = config("iteration")
    // -1 means I'm not using it
    runAndAverage(iteration = iteration, f = () => run(width = width, size = config("size"), near = -1, lat = true))
  }
}

// Just test code to print out
object BitSimulation extends App {
  var m = Map[String,Int]("size" -> 10000, "iteration" -> 10, "verbose" -> 0)
  var ls = new BitSimulation(m)
  val f = (i :Int) => ls.simulate(width = i)

  (1 to 4).foreach { i =>
    println(s"SIMULATION FOR BIT TYPE ${i}")
    println(s"${Util.map2string(f(i))}")
  }

  m = Map[String,Int]("size" -> 100, "near" -> 10, "iteration" -> 10, "verbose" ->0)

  val res = ls.simulateOverWidth(1,10)
  println(res.mkString("\n\n"))
}