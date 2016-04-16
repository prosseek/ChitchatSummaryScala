package summary

import java.lang.{String => JString}

import filter.Filter

object CBFSummary  extends ChitchatSummaryFactory[FBFSummary] {
  val name = "cbf"

  def make(q:Int, filter:Filter) =
    new CBFSummary(q = q, filter = filter)
}

class CBFSummary(override val q:Int = 4*8, override val filter:Filter = null)
  extends FBFSummary(q=q,
    filter = filter,
    force_depth_count_1 = true) {

}
