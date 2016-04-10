package filter

import summary.FBFSummary

abstract class Filter {
  def check(fbf:FBFSummary, key:String, keys:Seq[String]) : Boolean
}
