package summary

import util.compression.Compressor
import util.json.Json

object CompresseJsonSummary {
  def name = "compressed_json"
  def apply(filePath:String) = {
    val c = new CompresseJsonSummary
    c.loadJson(filePath)
    c
  }
}

class CompresseJsonSummary extends JsonSummary {
  override def name = CompresseJsonSummary.name

  override def serialize : Array[Byte] = {
    val compressed = Compressor.compress(super.serializedContent)
    _serialize(CompresseJsonSummary.name, compressed)
  }
  override def deserialize(ba: Array[Byte]) : Map[String, Any] = {
    val (name, content) = _deserialize(ba)
    if (name == CompresseJsonSummary.name) {
      val decompressed = Compressor.decompress(content)
      val str = new String(decompressed)
      Json.parse(str)
    }
    else
      throw new RuntimeException(s"Wrong serialized file format: ${CompresseJsonSummary.name} required")
  }
}
