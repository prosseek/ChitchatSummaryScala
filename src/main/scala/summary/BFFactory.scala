package summary

import java.lang.{String => JString}

import filter.Filter

// http://stackoverflow.com/questions/36534705/reuse-objects-apply-functions-in-scala/36535965#36535965
trait BFFactory[T <: FBFSummary] {
  def make(q:Int, filter:Filter): T

  def apply(q:Int, input:Map[JString, Any], filter:Filter) = {
    val bf = make(q = q, filter = filter)
    bf.create(map = input)
    bf
  }

  def apply(q:Int, filePath:JString, filter:Filter) = {
    filePath match {
      case f if f.endsWith(".json") => {
        val bf = make(q = q, filter = filter)
        bf.loadJson(filePath)
        bf
      }
      case f if f.endsWith(".bin") => {
        // we don't care about the q, as it will be overwritten from the byte array
        val bf = make(q = q, filter = filter)
        bf.load(filePath)
        bf
      }
      case _ => throw new RuntimeException(s"Only json or bin can be used for setup ${filePath}")
    }
  }

}
