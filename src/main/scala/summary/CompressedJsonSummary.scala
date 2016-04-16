package summary

import filter.Filter
import util.compression.Compressor
import util.json.Json

object CompressedJsonSummary extends ChitchatSummaryFactory[JsonSummary] {
  def name = "compressed_json"

  def make(q: Int, filter:Filter) =
    new CompressedJsonSummary()
}

class CompressedJsonSummary extends JsonSummary {
  override def name = CompressedJsonSummary.name

  override def serialize : Array[Byte] = {
    val compressed = Compressor.compress(super.serializedContent)
    _serialize(CompressedJsonSummary.name, compressed)
  }
  override def deserialize(ba: Array[Byte]) : Map[String, Any] = {
    val deserialized = _deserialize(ba)
    if (deserialized.get("name").get == CompressedJsonSummary.name) {
      val decompressed = Compressor.decompress(deserialized.get("content").get.asInstanceOf[Array[Byte]])
      val str = new String(decompressed)
      Json.parse(str)
    }
    else
      throw new RuntimeException(s"Wrong serialized file format: ${CompressedJsonSummary.name} required")
  }
}
