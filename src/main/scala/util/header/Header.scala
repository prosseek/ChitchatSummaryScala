package util.header

import collection.mutable.{Map => MMap}
import chitchat.types.{Encoding, Range}

object Header {
  def apply(version:Int) = {
    new Header(version)
  }
}

/**
  * The default header is one byte, but you can add more bytes depending on
  * the version
  */
class Header(version:Int) {
  val instance = create

  private val nameToNumberMap = Map(
    summary.JsonSummary.name -> 0,
    summary.CompressedJsonSummary.name -> 1,
    summary.LabeledSummary.name -> 2,
    summary.CBFSummary.name -> 3,
    summary.FBFSummary.name -> 4,
    summary.CompleteSummary.name -> 5
  )

  def nameToNumber(name:String) = {
    val res = nameToNumberMap.getOrElse(name, -1)
    if (res == -1)
      throw new RuntimeException(s"name ${name} is not supported summary type")
    res
  }

  def numberToName(value: Int) : String = {
    val res = nameToNumberMap.find(_._2 == value).getOrElse(("",-1))._1
    if (res == "")
      throw new RuntimeException(s"chitchat.value ${value} does not have corresponding key")
    res
  }

  def size (version:Int = 1) : Int = {
    instance.sizeInBytes
  }

  def encode(summary_type_name:String) = {
    val summary_type = nameToNumber(summary_type_name)
    version match {
      case 1 => instance.encode(Seq[Int](version, summary_type)) // version 1
      case _ => throw new RuntimeException(s"Version ${version} not supported")
    }
  }

  /**
    * We don't know the header size, so we need the whole byte array
    * for encoding
    *
    * Return format is (version:Int, summary_type_name:String, hint:Int, size:Int)
    * We return -1 for hint when we don't have any.
    *
    * @param byteArray
    */
  def decode(byteArray:Array[Byte]) = {
    val decoded = instance.decode(byteArray.slice(0, 1)).get
    val version = decoded(0)
    val name = numberToName(decoded(1))
    val sz = size(version)

    version match {
      case 1 => Map[String, Any]("version" -> version, "name" -> name, "size" -> sz)
      case 2 => {
        val hint = byteArray.slice(1,2)(0).toInt
        Map[String, Any]("version" -> version, "name" -> name, "hint" -> hint, "size" -> sz)
      }
    }
  }

  def create  = {
    if (version == 1) {
      new Encoding(name="header", Seq[Range](
        new Range(name="version", size = 4, signed = false, min = 1, max = 15),
        new Range(name="name", size = 4, signed = false, min = 0, max = 15)))
    }
    else
      throw new RuntimeException(s"version ${version} not supported")
  }
}
