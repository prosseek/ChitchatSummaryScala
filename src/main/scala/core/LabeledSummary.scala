package core

import grapevineType.BottomType._
import util.conversion.Util

/**
 * Created by smcho on 8/10/14.
 */
class LabeledSummary extends GrapevineSummary {

  def getKeys(): List[String] = {
    getMap().keySet.toList
  }

  override def getSize(): Int = {
    (0 /: dataStructure) { (acc, value) => acc + value._2.getSize } + // sum value size
    (0 /: dataStructure.keys) {(acc, value) => acc + value.size}     // sum of keys
    // dataStructure.size     // 1 byte is used for identifying the type
  }
  def getCompleteSize(): Int = {
    val size1 = (0 /: dataStructure) { (acc, value) => acc + value._2.getSize }
    val size2 = Util.getByteSizeFromSize(dataStructure.size)
    size1 + size2
  }

  override def get(key: String): Any = {
    val r = getValue(key)
    if (r.nonEmpty)
    //MMap[String, Tuple2[GrapevineType, Object]]()
      r.get
    else
      throw new RuntimeException(s"No matching value for key ${key}")
  }

  override def check(key: String): BottomType = {
    if (getValue(key).isEmpty) Bottom // this is structural check to return Buttom
    else NoError
  }
}