package filter

import chitchat.typefactory.TypeDatabase
import summary.FBFSummary

abstract class Filter {
  def check(fbf:FBFSummary, label:String) : Boolean
  def getTypeDatabase : TypeDatabase
}
