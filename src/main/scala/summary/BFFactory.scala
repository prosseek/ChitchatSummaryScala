package summary

import chitchat.typetool.TypeInference

import java.lang.{String => JString}

// http://stackoverflow.com/questions/36534705/reuse-objects-apply-functions-in-scala/36535965#36535965
trait BFFactory[T <: FBFSummary] {

  def make(q:Int, typeInference: TypeInference): T

  def apply(q:Int, input:Map[JString, Any], typeInference: TypeInference) = {
    val bf = make(q = q, typeInference = typeInference)
    bf.create(map = input)
    bf
  }

  def apply(q:Int, filePath:JString, typeInference: TypeInference) = {
    filePath match {
      case f if f.endsWith(".json") => {
        val bf = make(q = q, typeInference = typeInference)
        bf.loadJson(filePath)
        bf
      }
      case f if f.endsWith(".bin") => {
        // we don't care about the q, as it will be overwritten from the byte array
        val bf = make(q = q, typeInference = typeInference)
        bf.load(filePath)
        bf
      }
      case _ => throw new RuntimeException(s"Only json or bin can be used for setup ${filePath}")
    }
  }

}
