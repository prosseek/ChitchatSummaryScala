package summary

import chitchat.typetool.TypeInference
import util.header.Header
import java.lang.{String => JString}

import chitchat.types.{Encoding, Range, Float, String}

import scala.collection.mutable.{ArrayBuffer, Map => MMap}

object LabeledSummary {
  def name = "labeled"

  def apply(typeInference: TypeInference) = {
    new LabeledSummary(typeInference = typeInference)
  }
}

class LabeledSummary(override val typeInference:TypeInference) extends ChitchatTypeSummary(typeInference = typeInference)
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
    while (index < contentByteArray.size) {
      val size = contentByteArray(index)
      val key = new JString(contentByteArray.slice(index+1, index + 1 + size))

      index += (1 + size)
      val instance = typeInference.get(key)
      // todo: too many duplications
      // http://stackoverflow.com/questions/36511206/merging-multiple-case-in-match-case-in-scala
      if (instance.isEmpty) throw new RuntimeException(s"No instance for label ${key}")
      instance match {
        case Some(v:Range)  => {
          val sizeInBytes = util.conversion.Util.getBytesForBits(v.size)
          val value = v.decode(contentByteArray.slice(index, index + sizeInBytes))
          index += sizeInBytes
          res(key) = value.get
        }
        case Some(v:Encoding) => {
          val sizeInBytes = util.conversion.Util.getBytesForBits(v.size)
          val value = v.decode(contentByteArray.slice(index, index + sizeInBytes))
          index += sizeInBytes
          res(key) = value.get
        }
        case Some(v:Float) => {
          val sizeInBytes = util.conversion.Util.getBytesForBits(v.size)
          val value = v.decode(contentByteArray.slice(index, index + sizeInBytes))
          index += sizeInBytes
          res(key) = value.get
        }
        // Only differs here
        case Some(v:String) => {
          val size = contentByteArray(index)
          val value = new JString(contentByteArray.slice(index+1, index + 1 + size))

          index += (1 + size)
          res(key) = value
       }
        case Some(_) | None => throw new RuntimeException(s"Error in deserialize function ${instance.get.name}")

      }
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
        val baValue = typeInference.encode(label, value).get
        buffer ++=  (baLabel ++ baValue)
      }
    }
    buffer.toArray
  }
}