package ontology

import core.BloomierFilterSummary
import grapevineType.BottomType._
import grapevineType.{LatitudeType, GrapevineType}

/**
 * Created by smcho on 8/21/14.
 */
class Relation(val bfs:BloomierFilterSummary) {

  def checkLatitude() : BottomType = {
    bfs.check("longitude")
    NoError
  }

  def check(key:String) : BottomType = {
    GrapevineType.getTypeFromKey(key) match {
      case Some(c) if (c == classOf[LatitudeType]) => checkLatitude()
    }
    NoError
  }
}
