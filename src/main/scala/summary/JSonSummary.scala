package summary

import java.io.{File, PrintWriter}

import util.json.Json

import scala.collection.mutable.{Map => MMap}

object JSonSummary {
  def apply(map: Map[String, Any]) = {
    val summary = new JSonSummary
    summary.create(map)
    summary
  }
  def apply(filePath: String) = {
    val summary = new JSonSummary
    summary.create(filePath)
    summary
  }
}

class JSonSummary extends ChitchatSummary {
  // JSon summary is three things - map/filePath/contents
  var map = Map[String, Any]()
  private var filePath:String = ""
  private var contents:String = ""

  private def reset = {
    filePath = ""
    contents = ""
  }
  // create
  override def create(map: Map[String, Any]): Unit = {
    this.map = map
    reset
  }
  override def create(filePath: String): Unit = {
    this.contents = Json.loadJsonContents(filePath)
    this.map = Json.parse(this.contents)
    this.filePath = filePath
  }

  // get/set
  override def get(label: String): Option[Any] = {
    if (map.keySet.contains(label)) Some(map(label))
    else None
  }

  // get information
  override def getSchema: Set[String] = {
    map.keySet
  }

  override def size: Int = {
    if (contents == "") -1
    else {
      contents.length
    }
  }

  // transform
  override def serialize: Array[Byte] = {
    if (contents == "")
      throw new RuntimeException(s"No contents to serialize")
    else
      contents.getBytes
  }

  // I/O
  override def load(filePath: String): Any = {
    create(filePath)
    this.map
  }

  // I/O
  override def save(filePath: String, content: Any = null): Unit  = {
    var outContent:String = ""
    if (content == null) {
      if (this.contents == null)
        if (this.map != null)
          outContent = Json.mapToString(this.map)
        else
          throw new RuntimeException("contents/filePath/input are all null")
      else
        outContent = this.contents
    }
    else {
      if (content.isInstanceOf[String])
        outContent = content.asInstanceOf[String]
      else if (content.isInstanceOf[Map[_, _]])
        outContent = Json.mapToString(content.asInstanceOf[Map[String, Any]])
      else
        throw new RuntimeException(s"Can't print out the value ${content}")
    }

    val file = new PrintWriter(new java.io.File(filePath))
    file.write(contents)
    file.close()
  }
}
