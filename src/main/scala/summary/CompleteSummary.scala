package summary

import chitchat.typetool.TypeInference
import java.lang.{String => JString}

import scala.{Byte => SByte, Int => SInt}
import chitchat.types._
import util.header.Header

import collection.mutable.{ArrayBuffer, Map => MMap}

object CompleteSummary {
  def name = "complete"

  def apply(typeInference: TypeInference) = {
    new CompleteSummary(typeInference = typeInference)
  }
}

class CompleteSummary (override val typeInference: TypeInference) extends ChitchatTypeSummary(typeInference){
  var orderedLabel:Seq[JString] = _

  // create
  override def create(map: Map[JString, Any]) : Unit = {
    super.create(map)
    orderedLabel = map.keys.toList.sorted
  }

  // query
  def get(label:JString) : Option[Any] = {
    map.get(label)
  }

  def schema: Option[Set[JString]] = {
    Some(orderedLabel.toSet)
  }

  def size: Int = {
    serialize.size
  }

  // transform
  def serialize : Array[Byte] = {
    Header(version=1).encode(CompleteSummary.name).get ++ serializedContent
  }

  def deserialize(ba: Array[Byte], orderedLabel:Seq[JString]) : Map[JString, Any] = {
    // todo: Duplication with Labeled summary
    // get the content from the byte array
    val version = _getVersion(ba)
    val nameId = _getName(ba)

    if (nameId != CompleteSummary.name)
      throw new RuntimeException(s"Wrong summary type ${nameId}, expected ${CompleteSummary.name}")
    val contentByteArray:Array[Byte] = _getContent(ba)

    val res = MMap[JString, Any]()
    var index = 0
    var sizeInBytes = 0
    var value:Any = null
    orderedLabel foreach {
      key => {
        val instance = typeInference.get(key)
        // todo: too many duplications
        // http://stackoverflow.com/questions/36511206/merging-multiple-case-in-match-case-in-scala
        if (instance.isEmpty) throw new RuntimeException(s"No instance for label ${key}")

        val v = instance.get
        var sizeInBytes = v.sizeInBytes
        var value:Any = null

        if (sizeInBytes == 0) { // string
          val size = contentByteArray(index)
          value = new JString(contentByteArray.slice(index+1, index + 1 + size))
          sizeInBytes = (1 + size)
        }
        else
          value = v.decode(contentByteArray.slice(index, index + sizeInBytes)).get

        index += sizeInBytes
        res(key) = value

//        instance match {
//          case Some(v:Range)  => {
//            sizeInBytes = util.conversion.Util.getBytesForBits(v.size)
//            value = v.decode(contentByteArray.slice(index, index + sizeInBytes)).get
//          }
//          case Some(v:Encoding) => {
//            sizeInBytes = util.conversion.Util.getBytesForBits(v.size)
//            value = v.decode(contentByteArray.slice(index, index + sizeInBytes)).get
//          }
//          case Some(v:Float) => {
//            sizeInBytes = util.conversion.Util.getBytesForBits(v.size)
//            value = v.decode(contentByteArray.slice(index, index + sizeInBytes)).get
//          }
//          // Only differs here
//          case Some(v:String) => {
//            val size = contentByteArray(index)
//            value = new JString(contentByteArray.slice(index+1, index + 1 + size))
//            sizeInBytes = (1 + size)
//          }
//          case Some(_) | None => throw new RuntimeException(s"Error in deserialize function ${instance.get.name}")
//        }
//        index += sizeInBytes
//        res(key) = value
      }
    }
    res.toMap
  }

  // I/O
  def save(filePath:JString) : Unit = {
    val ba = serialize
    _save(filePath = filePath, byteArray = ba)
  }
  def load(filePath:JString, orderedLabel:Seq[JString]) : Any = {
    val res = deserialize(_load(filePath), orderedLabel)
    create(res)
  }

  // ID
  def name : JString = CompleteSummary.name

  // Internal
  protected def serializedContent : Array[Byte] = {
    val buffer = ArrayBuffer[Byte]()
    orderedLabel foreach { label => {
        val value = map.get(label).get
        val baValue = typeInference.encode(label, value).get
        buffer ++=  baValue
      }
    }
    buffer.toArray
  }

  override def deserialize(ba: Array[SByte]): Map[JString, Any] = {
    throw new RuntimeException(s"Use deserialize(ba, schema) instead")
  }
  override def load(filePath: JString): Any = {
    throw new RuntimeException(s"Use deserialize(ba, schema) instead")
  }
}
