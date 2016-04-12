package summary

import util.header.Header
import java.lang.{String => JString}

import chitchat.typefactory.TypeDatabase
import chitchat.types.{Encoding, Float, Range, String}

import scala.collection.mutable.{ArrayBuffer, Map => MMap}

object LabeledSummary {
  def name = "labeled"

  def apply(typeDatabase: TypeDatabase) = {
    new LabeledSummary(typeDatabase = typeDatabase)
  }
}

class LabeledSummary(override val typeDatabase:TypeDatabase) extends ChitchatTypeSummary(typeDatabase = typeDatabase)
{
  // create
  override def create(map: Map[JString, Any]) : Unit = {
    super.create(map)
  }

  // query
  def get(label:JString) : Option[Any] = {
    map.get(label)
  }

  def schema: Option[Set[JString]] = {
    Some(map.keys.toSet)
  }

  def size: Int = {
    serialize.size
  }

  // transform
  def serialize : Array[Byte] = {
    Header(version=1).encode(LabeledSummary.name).get ++ serializedContent
  }
  def deserialize(ba: Array[Byte]) : Map[JString, Any] = {
    // get the content from the byte array
    val version = _getVersion(ba)
    val nameId = _getName(ba)

    if (nameId != LabeledSummary.name) throw new RuntimeException(s"Wrong summary type ${nameId}, expected ${LabeledSummary.name}")
    val contentByteArray:Array[Byte] = _getContent(ba)

    val res = MMap[JString, Any]()
    var index = 0
    var sizeInBytes = 0
    var value:Any = null
    while (index < contentByteArray.size) {
      val size = contentByteArray(index)
      val key = new JString(contentByteArray.slice(index+1, index + 1 + size))

      index += (1 + size)
      val instance = typeDatabase.get(key).get

      var sizeInBytes = instance.sizeInBytes
      var value:Any = null
      if (sizeInBytes == 0) {         // string type
        val size = contentByteArray(index)
        value = new JString(contentByteArray.slice(index + 1, index + 1 + size))
        sizeInBytes = (1 + size)
      }
      else
        value = instance.decode(contentByteArray.slice(index, index + sizeInBytes)).get

      index += sizeInBytes
      res(key) = value
    }
    res.toMap
  }

  // I/O
  def save(filePath:JString) : Unit = {
    val ba = serialize
    _save(filePath = filePath, byteArray = ba)
  }
  def load(filePath:JString) : Any = {
    val res = deserialize(_load(filePath))
    create(res)
  }

  // ID
  def name : JString = LabeledSummary.name

  // Internal
  protected def serializedContent : Array[Byte] = {
    val buffer = ArrayBuffer[Byte]()
    map foreach {
      case (label, value) => {
        val baLabel = util.conversion.ByteArrayTool.stringToByteArray(label)
        val baValue = typeDatabase.encode(label, value).get
        buffer ++=  (baLabel ++ baValue)
      }
    }
    buffer.toArray
  }
}