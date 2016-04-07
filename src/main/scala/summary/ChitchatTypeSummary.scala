package summary

import scala.collection.mutable.{Map => MMap}
import chitchat.types._
import java.lang.{String => JString}

import util.types.TypeInference

import scala.{Byte => SByte, Int => SInt}

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

abstract class ChitchatTypeSummary extends Summary {
  var map:MMap[JString, Any] = _
  var mapChitchatype: MMap[JString, Base[_]] = _


  // create
  override def create(map: Map[JString, Any]): Unit = ???

  def saveJson(filePath:String) : Unit = {

  }

  def loadJson(filePath:String) : Any = {

  }

  override def load(filePath: JString): Any = {
    null
  }

  override def save(filePath: JString): Unit = {

  }

  override def schema: Option[Set[JString]] = {
    null
  }

  // helper API
  def anyToByteArray(inputMap: Map[JString, Any], typeInference: TypeInference): Map[JString, Array[SByte]] = {
    def convert(key:JString, value:Any) : Array[Byte] = {
      value match {
        case value:JString => null
        case value:Double => null
        case value:SInt => null
        case value:Seq[_] => null
        case _ => throw new RuntimeException(s"value ${value} cannot be transformed into byte array")
      }
    }
    inputMap map {case (key, value) => key -> convert(key, value)}
  }
}