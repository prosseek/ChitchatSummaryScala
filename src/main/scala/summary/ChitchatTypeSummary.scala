package summary

import scala.collection.mutable.{Map => MMap}
import chitchat.types._
import java.lang.{String => JString}
import util.types.TypeInference

import scala.{Byte => SByte, Int => SInt}

import value.Value

//object ChitchatTypeSummary {
//  def apply(map: Map[JString, Any]) = {
//    val summary = new ChitchatTypeSummary
//    summary.create(map)
//    summary
//  }
//  def apply(filePath: JString) = {
//    val summary = new ChitchatTypeSummary
//    summary.loadJson(filePath)
//    summary
//  }
//  def name = "chitchattype"
//}

abstract class ChitchatTypeSummary(val typeInference: TypeInference) extends Summary {
  var map:Map[JString, Any] = null
  var mapChitchatype: Map[JString, Value] = null

  // create
  override def create(map: Map[JString, Any]): Unit = {
    this.map = map
    this.mapChitchatype = anyToChitchatValue(this.map, typeInference)
  }

  def saveJson(filePath:String) : Unit = {

  }

  def loadJson(filePath:String) : Any = {

  }

  override def load(filePath: JString): Any = {
    null
  }

  override def save(filePath: JString): Unit = {

  }

  override def schema: Option[Set[JString]]

  // helper API
  def anyToChitchatValue(inputMap: Map[JString, Any], typeInference: TypeInference): Map[JString, Value] = {
    inputMap map {case (label, value) => label -> Value(label = label, value = value, typeInference = typeInference)}
  }
}