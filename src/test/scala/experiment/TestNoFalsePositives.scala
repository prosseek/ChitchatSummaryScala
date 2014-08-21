package experiment

import core.BloomierFilterSummary
import grapevineType.BottomType._
import grapevineType._
import org.scalatest.FunSuite
import util.conversion.experiment.Run

/**
 * Created by smcho on 8/21/14.
 */
class TestNoFalsePositives extends FunSuite {
  test ("Simple") {
    def getValueFromBF(bf:BloomierFilterSummary, key:String): Any = {
      if (bf.check(key) == NoError)
        bf.get(key)
      else {
        bf.check(key)
      }
    }

    def check(summaryPath:String, byteWidth:Int) = {
      val bf = Run.getBF(summaryPath, byteWidth = 4)
      val ls = Run.getLabeledSummary(summaryPath)
      ls.getKeys().foreach { key =>
        if (GrapevineType.getTypeFromKey(key).get == classOf[FloatType]) {
          //println(math.abs(getValueFromBF(bf, key).asInstanceOf[Float] - ls.get(key).asInstanceOf[Float]))
          assert(math.abs(getValueFromBF(bf, key).asInstanceOf[Float] - ls.get(key).asInstanceOf[Float]) <= 0.01)
        }
        else
          assert(getValueFromBF(bf, key) == ls.get(key))
      }
    }
    (1 to 10).foreach { check("experiment/contextsForTest/summary1.txt", _) }
    (1 to 10).foreach { check("experiment/contextsForTest/summary2.txt", _) }
  }
}
