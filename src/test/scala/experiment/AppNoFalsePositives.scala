package experiment

import core.ContextSummary
import grapevineType.BottomType._
import grapevineType._
import util.gen.Summary

/**
 * Created by smcho on 8/21/14.
 */
object AppNoFalsePositives extends App {
    def getValueFromBF(bf:ContextSummary, key:String): Any = {
      if (bf.check(key) == NoError)
        bf.get(key)
      else {
        bf.check(key)
      }
    }

    def check(summaryPath:String, byteWidth:Int) = {
      val bf = Summary.getBF(summaryPath, m = -1, k = 3, byteWidth = byteWidth, complete=false)
      val ls = Summary.getLabeledSummary(summaryPath)
      ls.getKeys().foreach { key =>
        if (GrapevineType.getTypeFromKey(key).get == classOf[FloatType]) {
          //println(math.abs(getValueFromBF(bf, key).asInstanceOf[Float] - ls.get(key).asInstanceOf[Float]))
          assert(math.abs(getValueFromBF(bf, key).asInstanceOf[Float] - ls.get(key).asInstanceOf[Float]) <= 0.01)
        }
        else
          assert(getValueFromBF(bf, key) == ls.get(key), s"key (${key}) BF - ${getValueFromBF(bf, key)} vs Labeled - ${ls.get(key)}")
      }
    }
//  (1 to 10).foreach { check("experiment/contextsForTest/summary1.txt", _) }
//  (1 to 10).foreach { check("experiment/contextsForTest/summary2.txt", _) }
//  (1 to 10).foreach { check("experiment/scenario/s1.txt", _)}
//  (1 to 10).foreach { check("experiment/scenario/s5.txt", _)}
  (1 to 10).foreach { check("experiment/scenario/s3_2.txt", _)}
}
