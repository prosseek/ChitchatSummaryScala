package summary

import java.io._

import util.header.Header
import util.json.Json

abstract class Summary {
  // create
  def create(map: Map[String, Any]) : Unit

  // query
  def get(label:String) : Option[Any]
  def schema: Option[Set[String]]
  def size: Int

  // modify
  def update(label:String, value:Any) : Boolean
  def add(label:String, value:Any) : Boolean
  def delete(label:String) : Boolean

  // transform
  def serialize : Array[Byte]
  def deserialize(ba: Array[Byte]) : Map[String, Any]

  // I/O
  def saveJson(filePath:String) : Unit
  def loadJson(filePath:String) : Any
  def save(filePath:String) : Unit
  def load(filePath:String) : Any

  // ID
  def name : String

  // protected APIs
  protected def serializedContent : Array[Byte]

  // service functions
  protected def _save(filePath:String, byteArray:Array[Byte]) = {
    val bos = new BufferedOutputStream(new FileOutputStream(filePath))
    Stream.continually(bos.write(byteArray))
    bos.close()
  }

  protected def _load(filePath:String) : Array[Byte] = {
    if (new File(filePath).exists) {
      val bis = new BufferedInputStream(new FileInputStream(filePath))
      Stream.continually(bis.read).takeWhile(_ != -1).map(_.toByte).toArray
    }
    else
      throw new RuntimeException(s"No file ${filePath} exists")
  }

  protected def _saveJsonMap(filePath: String, map:Map[String, Any]): Unit  = {
    val file = new PrintWriter(new java.io.File(filePath))
    file.write(Json.build(map).toString)
    file.close()
  }

  protected def _loadJsonContent(filePath: String): String = {
    if (new File(filePath).exists) {
      Json.loadJsonContent(filePath)
    }
    else
      throw new RuntimeException(s"No file ${filePath} exists")
  }

  protected def _serialize(name:String, byteArray:Array[Byte]) : Array[Byte] = {
    Header.serialize(name) ++ byteArray
  }

  protected def _deserialize(byteArray:Array[Byte]) : (String, Array[Byte]) = {
    val header = byteArray.slice(0, Header.size)
    val name = Header.deserialize(header)
    val content = byteArray.slice(Header.size, byteArray.size)
    (name, content)
  }

  protected def _toMMap(map:scala.collection.immutable.Map[String,Any]) = {
    scala.collection.mutable.Map(map.toSeq:_*)
  }
}