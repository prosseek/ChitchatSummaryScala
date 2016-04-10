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
  override def update(label:JString, value:Any) : Boolean = {
    if (schema.contains(label)) {
      map(label) = value
      true
    }
    else
      false
  }
  override def add(label:JString, value:Any) : Boolean = {
    if (!schema.contains(label)) {
      map(label) = value
      true
    }
    else
      false
  }
  override def delete(label:JString) : Boolean = {
    if (schema.contains(label)) {
      map -= label
      true
    }
    else
      false
  }

  override def saveJson(filePath:JString, map:Map[JString, Any] = null) : Unit = {
    var m = map
    if (m == null)
      m = this.map.toMap
    _saveJsonMap(filePath, m)
  }

  override def loadJson(filePath:JString) : Any = {
    val content = _loadJsonContent(filePath)
    val jsonMap = Json.parse(content)
    if (Json.isSimpleJson((jsonMap))) {
      create(jsonMap)
    } else {
      throw new RuntimeException(s"This JSON ${filePath}/(${content}) is not converted into chitchat type summary")
    }
  }

  // added API
  // todo : Add theoretical size
  // def theoreticalSize
}