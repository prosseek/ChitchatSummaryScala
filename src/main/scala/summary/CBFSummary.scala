package summary

import chitchat.typetool.TypeInference

/**
  * Created by smcho on 4/5/16.
  */
class CBFSummary(override val q:Int, override val typeInference: TypeInference)
  extends FBFSummary(q=q, typeInference = typeInference, force_m_multiple_by_four = true) {

}
