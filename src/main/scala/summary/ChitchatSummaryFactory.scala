package summary

import util.json.Json
import filter.Filter

trait ChitchatSummaryFactory[T <: Summary] {
  def make(q:Int = 0, filter:Filter = null): T

  def apply(q:Int, input:Map[String, Any], filter:Filter) = {
    val bf = make(q = q, filter = filter)
    bf.create(map = input)
    bf
  }

  def apply(q:Int = 0, source:String, filter:Filter = null) : T = {
    source.trim match {
      case f if (f.startsWith("{") && f.endsWith("}")) => {
        val input = Json.parse(source)
        apply(q, input, filter)
      }
      case f if f.endsWith(".json") => {
        val bf = make(q=q, filter = filter)
        bf.loadJson(source)
        bf
      }
      case f if f.endsWith(".bin") => {
        // we don't care about the q, as it will be overwritten from the byte array
        val bf = make(q=q, filter = filter)
        bf.load(source)
        bf
      }
      case _ => throw new RuntimeException(s"Only json or bin can be used for setup ${source}")
    }
  }
}
