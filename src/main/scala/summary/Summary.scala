package summary

import java.io._


import collection.mutable.{Map => MMap}

import util.header.Header
import util.json.Json

abstract class Summary {
  var map:MMap[String, Any] = MMap[String, Any]()

  // create
  def create(map: Map[String, Any]): Unit = {
    this.map.clear()
    // when create the object from serialized data, the map can be null
    if (map != null)
      this.map ++= map
  }

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
  def saveJson(filePath:String, map:Map[String, Any] = null) : Unit
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

  protected def _serialize(name:String, byteArray:Array[Byte], version:Int=1) : Array[Byte] = {
    Header(version).encode(name).get ++ byteArray
  }

  protected def _deserialize(byteArray:Array[Byte], version:Int = 1) : Map[String, Any] = {
    val header = Header(version).decode(byteArray)
    val size = header.get("size").get.asInstanceOf[Int]
    val name = header.get("name").get.asInstanceOf[String]

    val res = _toMMap(header)
    res("content") = byteArray.slice(size, byteArray.size)
    res.toMap
  }

  protected def _getContent(byteArray:Array[Byte]) : Array[Byte] = {
    _deserialize(byteArray).get("content").get.asInstanceOf[Array[Byte]]
  }

  protected def _getVersion(byteArray:Array[Byte]) = {
    _deserialize(byteArray).get("version").get
  }

  protected def _getName(byteArray:Array[Byte]) = {
    _deserialize(byteArray).get("name").get
  }

  protected def _toMMap(map:scala.collection.immutable.Map[String,Any]) = {
    scala.collection.mutable.Map(map.toSeq:_*)
  }
}