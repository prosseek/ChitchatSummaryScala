package summary

import chitchat.typetool.TypeInference
import java.lang.{String => JString}

object CBFSummary {
  val name = "cbf"

  def apply(q:Int, input:Map[JString, Any], typeInference: TypeInference) = {
    val fbf = new CBFSummary(q = q, typeInference = typeInference)
    fbf.create(map = input)
    fbf
  }

  def apply(q:Int, filePath:JString, typeInference: TypeInference) = {
    filePath match {
      case f if f.endsWith(".json") => {
        val fbf = new CBFSummary(q = q, typeInference = typeInference)
        fbf.loadJson(filePath)
        fbf
      }
      case f if f.endsWith(".bin") => {
        // we don't care about the q, as it will be overwritten from the byte array
        val fbf = new CBFSummary(q = q, typeInference = typeInference)
        fbf.load(filePath)
        fbf
      }
      case _ => throw new RuntimeException(s"Only json or bin can be used for setup ${filePath}")
    }
  }
}

class CBFSummary(override val q:Int, override val typeInference: TypeInference)
  extends FBFSummary(q=q, typeInference = typeInference, force_depth_count_1 = true) {

}
