package filter
import chitchat.typefactory.TypeDatabase
import summary.FBFSummary

object ChitchatFilter {
  def apply(typeDatabase: TypeDatabase) = {
    new ChitchatFilter(typeDatabase)
  }
}

class ChitchatFilter(typeDatabase: TypeDatabase) extends Filter {
  override def check(fbf: FBFSummary, key: String): Boolean = {
    val instance = typeDatabase.get(key)
    true
  }

  override def getTypeDatabase = typeDatabase
}
