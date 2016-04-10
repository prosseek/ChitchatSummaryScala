package filter
import summary.FBFSummary

class LocationFilter extends Filter {
  override def check(fbf: FBFSummary, key: String, keys: Seq[String]): Boolean = {
    true
  }
}
