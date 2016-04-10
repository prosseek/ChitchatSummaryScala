package summary

import chitchat.typetool.TypeInference
import java.lang.{String => JString}

object CBFSummary extends BFFactory[CBFSummary] {
  val name = "cbf"

  def make(q:Int, typeInference: TypeInference) = new CBFSummary(q = q, typeInference = typeInference)
}

class CBFSummary(override val q:Int, override val typeInference: TypeInference)
  extends FBFSummary(q=q, typeInference = typeInference, force_depth_count_1 = true) {

}
