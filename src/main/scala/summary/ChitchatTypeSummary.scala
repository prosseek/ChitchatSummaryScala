package summary

import java.lang.{String => JString}

import util.json.Json
import chitchat.typetool.TypeInference

abstract class ChitchatTypeSummary(val typeInference: TypeInference) extends Summary {
  var map:Map[JString, Any] = null

  // create
  override def create(map: Map[JString, Any]): Unit = {
    this.map = map
  }

  def saveJson(filePath:JString) : Unit = {
    _saveJsonMap(filePath, this.map)
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