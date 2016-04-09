package summary

import java.lang.{String => JString}

import util.json.Json
import chitchat.typetool.TypeInference
import collection.mutable.{Map => MMap}

abstract class ChitchatTypeSummary(val typeInference: TypeInference) extends Summary {
  var map:MMap[JString, Any] = MMap[JString, Any]()

  // create
  override def create(map: Map[JString, Any]): Unit = {
    this.map.clear()
    this.map ++= map
  }

  // modify
  def update(label:String, value:Any) : Boolean = {
    if (schema.contains(label)) {
      map(label) = value
      true
    }
    else
      false
  }
  def add(label:String, value:Any) : Boolean = {
    if (!schema.contains(label)) {
      map(label) = value
      true
    }
    else
      false
  }
  def delete(label:String) : Boolean = {
    if (schema.contains(label)) {
      map -= label
      true
    }
    else
      false
  }

  def saveJson(filePath:JString) : Unit = {
    _saveJsonMap(filePath, this.map.toMap)
  }

  def loadJson(filePath:JString) : Any = {
    val content = _loadJsonContent(filePath)
    val jsonMap = Json.parse(content)
    if (Json.isSimpleJson((jsonMap))) {
      create(jsonMap)
    } else {
      throw new RuntimeException(s"This JSON ${filePath}/(${content}) is not converted into chitchat type summary")
    }
  }
}