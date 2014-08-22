package ontology

import core.BloomierFilterSummary
import grapevineType.BottomType._

/**
 * Created by smcho on 8/21/14.
 */
class Relation(val bfs:BloomierFilterSummary) {

  def check(key:String) : BottomType = {
    NoError
  }
  def get(key:String) : Any = {
    1
  }

}
