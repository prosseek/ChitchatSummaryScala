package core

import grapevineType.BottomType._
import util.compression.CompressorHelper._
import util.conversion.ByteArrayTool._
import util.conversion.BitSetTool._
import util.conversion.Util

import scala.collection.mutable

/**
 * Created by smcho on 1/5/15.
 */
class CompleteSummary  extends GrapevineSummary {

  def setDataStructure(l:LabeledSummary) = {
    this.dataStructure.empty
    this.dataStructure ++= l.dataStructure
  }

//  def maxBits(size:Int) = {
//    math.ceil(log2(size)).toInt
//  }
//
  def log2(x : Double) = {
    math.log10(x)/math.log10(2.0)
  }

  // This should be the bit size, not byte
  def getTheorySize(): Int = {
    val size1 = (0 /: dataStructure) { (acc, value) => acc + value._2.getSize }
    //val size2 = Util.getByteSizeFromSize(math.ceil(dataStructure.size * log2(dataStructure.size.toDouble)).toInt)
    val size2 = Util.getByteSizeFromSize(dataStructure.size)
    //val size2 = math.ceil(dataStructure.size * log2(dataStructure.size.toDouble)).toInt
    size1 + size2
  }

  override def getSize() = {
    val serial = serialize()
    val compressed = compress(serial)

    (getTheorySize(), serial.size, compressed.size)
  }

  /* It should not be accessed from the key, but the index */

  override def get(key: String): Any = {
     throw new RuntimeException(s"No matching value for key ${key}")
  }

  override def check(key: String): BottomType = {
    if (getValue(key).isEmpty) Bottom // this is structural check to return Buttom
    else NoError
  }

  override def serialize(): Array[Byte] = {

    val size = dataStructure.size
    val sizeByteArray = shortToByteArray(size.toShort)

    val bitsForSize = math.ceil(log2(size.toDouble)).toInt
    var ab = Array[Byte]()
    // get the contents
    var bitSet = mutable.BitSet()

    dataStructure.zipWithIndex.foreach { case (ds, index) =>
      val bs = intToBitSet(index, shift = index * bitsForSize)
      bitSet ++= bs
      val value = ds._2
      val byteArrayValue = value.toByteArray()
      ab ++= byteArrayValue
    }
    val goalBitSetSize = Util.getByteSizeFromSize(dataStructure.size)
    return sizeByteArray ++ bitSetToByteArray(bitSet, goalBitSetSize) ++ ab
  }
}