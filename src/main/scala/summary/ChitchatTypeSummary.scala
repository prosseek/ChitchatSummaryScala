package summary

import java.lang.{String => JString}

import chitchat.typefactory.TypeDatabase
import util.json.Json

import collection.mutable.{Map => MMap}

abstract class ChitchatTypeSummary(val typeDatabase: TypeDatabase) extends Summary {
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