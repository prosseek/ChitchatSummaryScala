package app

import java.lang.{String => JString}

import api.API

import scala.io.Source

/**
  * Created by smcho on 12/9/16.
  */
object JSONAnalyzer {

  def getSizes(filePath:JString) = {
    val r = Source.fromFile(filePath).mkString("")
    val fbf = API.create_fbf_summary(r, Q = 2)
    val json = API.create_json_summary(r)
    val complete = API.create_complete_summary(r)
    val labeld = API.create_labeled_summary(r)
  }
}
