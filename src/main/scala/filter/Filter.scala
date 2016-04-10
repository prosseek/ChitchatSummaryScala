package filter

import summary.FBFSummary

abstract class Filter {
  def check(fbf:FBFSummary, label:String) : Boolean
}
