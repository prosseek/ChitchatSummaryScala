package app

import java.io.File

import grapevineType._
import util.gen.Summary

/**
 * Created by smcho on 8/24/14.
 */
object GenerateContexts extends App {
  //simulation.Util.generateRandomContextSummary()
  def getTempContextName() = {
    var tempFile = File.createTempFile("temp", "%06d".format(1))
    tempFile
  }

  val m = Map[String, GrapevineType]("abc" -> StringType("Good"))
  val bf = Summary.getBF(summary = m)
  println(bf.getMap)
}
