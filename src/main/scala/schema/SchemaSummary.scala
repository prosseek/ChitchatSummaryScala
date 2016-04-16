package schema

import summary.{FBFSummary, JsonSummary}
import collection.mutable.{Map => MMap}

object SchemaSummary {
  def apply() = {
    new SchemaSummary()
  }
}

class SchemaSummary {

  // given FBF/CBF byte array, recover
  def recover(fbf:FBFSummary, grammarName:String) : JsonSummary = {
    val g = new ATN(fbf = fbf)
    val result = g.atn(grammarName)
    if (result == null) return null
    else {
      JsonSummary(q = 0, input=result, filter = null)
    }
  }
}
