package util.header

import chitchat.types.{Encoding, Range}

object Header {

  def size (version:Int = 1) = {
    val sizeMap = Map(
      1 -> 1,
      2 -> 2
    )
    sizeMap.getOrElse(version, -1)
  }

  private val map = Map(
    summary.JsonSummary.name -> 0,
    summary.CompresseJsonSummary.name -> 1,
    summary.LabeledSummary.name -> 2,
    "cbf" -> 3,
    "fbf" -> 4,
    "complete" -> 5
  )

  private def nameToNumber(name:String) = {
    val res = map.getOrElse(name, -1)
    if (res == -1)
      throw new RuntimeException(s"name ${name} is not supported summary type")
    res
  }

  private def numberToName(value: Int) : String = {
    val res = map.find(_._2 == value).getOrElse(("",-1))._1
    if (res == "")
      throw new RuntimeException(s"chitchat.value ${value} does not have corresponding key")
    res
  }

  def encode(summary_type_name:String, version:Int = 1) = {
    val summary_type = Header.nameToNumber(summary_type_name)
    (new Header).encode(Seq[Int](version, summary_type))
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
    val decoded = (new Header).decode(byteArray.slice(0, 1)).get
    val version = decoded(0)
    val name = numberToName(decoded(1))
    val sz = size(version)

    val hint = if (version == 2) {byteArray.slice(1,2)(0).toInt} else -1

    Map[String, Any]("version" -> version, "name" -> name, "hint" -> hint, "size" -> sz)
  }
}

/**
  * The default header is one byte, but you can add more bytes depending on
  * the version
  */
class Header extends Encoding(name="header", Seq[Range](
  new Range(name="version", size = 4, signed = false, min = 1, max = 15),
  new Range(name="name", size = 4, signed = false, min = 0, max = 15)
))
