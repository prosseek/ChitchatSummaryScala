package summary

import util.types.TypeInference

/**
  * Created by smcho on 4/5/16.
  */
class CBFSummary(override val q:Int, override val typeInference: TypeInference) extends FBFSummary(q=q, typeInference = typeInference) {

}
