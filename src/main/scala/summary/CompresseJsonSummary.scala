package summary

object CompresseJSonSummary {
  def name = "compressed_json"
  def apply(filePath:String) = {
    val c = new CompresseJSonSummary
    c.loadJson(filePath)
    c
  }
}

class CompresseJSonSummary extends JsonSummary {
  override def name = CompresseJSonSummary.name
  override def serialize = {
    util.compression.Compressor.compress(super.serialize)
  }
  override def deserialize(ba: Array[Byte]) = ???
}
