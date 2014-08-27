package experiment

import simulation.FalsePositiveProbability

/**
 * Created by smcho on 8/25/14.
 */
object PrintFalsePositiveProbability extends App {
  def longitude (message:String) {
    println(message)
    (1 until 10).foreach { i =>
      println(s"${i} - ${FalsePositiveProbability.longitude(i)}")
    }
  }
  def latitude (message:String) {
    println(message)
    (1 until 10).foreach { i =>
      println(s"${i} - ${FalsePositiveProbability.latitude(i)}")
    }
  }
  longitude("longitude")
  latitude("latitude")
}
