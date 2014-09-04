package util.conversion

import bloomierFilter.ByteArrayBloomierFilter
import grapevineType._

import scala.collection.mutable.ArrayBuffer

/**
 * Created by smcho on 8/17/14.
 */
class Joiner {
  def filterString(value:Array[Byte], f:Byte => Boolean) :String = {
    val sb = new StringBuilder

    value.foreach {v =>
      if (f(v)) sb.append(v.toChar)
      else
        return sb.toString
    }
    sb.toString
  }
  def getInterpretableString(value:Array[Byte]) : String = {
    filterString(value, v => StringType.isPrintable(v.toChar))
  }
//
//  def getUptoQuote(value:Array[Byte]) = {
//    filterString(value, v => v.toChar != '\"')
//  }

  def isAllString(value:Array[Byte], f:Byte => Boolean) = {
    value.forall(v => f(v))
  }

  def isInterpretableString(value:Array[Byte]) = {
    isAllString(value, v => StringType.isPrintable(v.toChar))
  }

  def joinStringFromKeys(bbf:ByteArrayBloomierFilter, key:String) : Option[Array[Byte]] = {
    val ba = ArrayBuffer[Byte]()

    var res = bbf.get(s"${key}0")
    if (res.isEmpty) return None

    val size = 0xFF & (res.get)(0)
    val totalSize = size + 1
    val width = bbf.getWidth()

    if (width >= totalSize) return None // in this case, there should be no joining byte arrays
    val iteration = Util.ceil(totalSize, width)

    // process the first (0th) result
    ba ++= res.get

    // process everything in between
    (1 until (iteration - 1)).foreach { i =>
      val newKey = s"${key}${i}"
      val res = bbf.get(newKey)
      if (res.isEmpty) return None
      if (!isInterpretableString(res.get)) return None
      ba ++= res.get
    }

    // process the last one
    res = bbf.get(s"${key}${iteration-1}")
    if (res.isEmpty) return None
    var lastItem = totalSize % width
    if (lastItem == 0) lastItem = width
    val lastRes = res.get.slice(0, lastItem)
    if (!isInterpretableString(lastRes)) return None
    ba ++= lastRes

    Some(ba.toArray)
  }

  def joinString(bbf:ByteArrayBloomierFilter, key:String) : Option[Array[Byte]] = {
    var str1:String = ""
    var str2:String = ""

    val res1 = bbf.get(key) // just an Array[Byte]
    if (res1.isDefined) {
      val ba = res1.get
      val width = bbf.getWidth()
      val size = ba(0) & 0xFF
      if (width >= (size + 1))
        str1 = ByteArrayTool.byteArrayToString(ba)
    }
    val res2 = joinStringFromKeys(bbf, key)
    if (res2.isDefined) {
      str2 = ByteArrayTool.byteArrayToString(res2.get)
    }

    if (res1.isEmpty && res2.isEmpty) return None
    if (res1.isEmpty) {
      if (str2 == "") None else str2
    }
    if (res2.isEmpty) {
      if (str1 == "") None else str1
    }
    if (str2.size > str1.size) return res2
    return res1
  }

  def joinFromBloomierFilter(bbf:ByteArrayBloomierFilter, key:String, dataWidth:Int) : Option[Array[Byte]] = {
//    def getIterationNumber(dataWidth:Int, tableWidth:Int) = {
//      val q1 = dataWidth / tableWidth
//      val q2 = dataWidth % tableWidth
//      if (q2 == 0) q1
//      else q1 + 1
//    }

    val tableWidth = bbf.getWidth()
    if (tableWidth >= dataWidth) bbf.get(key)
    else {
      val range = Range(0, Util.ceil(dataWidth, tableWidth))
      val res = (Array[Byte]() /: range) { (acc, i) =>
        val newKey = s"${key}${i}"
        val value = bbf.get(newKey)
        if (value.isEmpty) return None
        else {
          acc ++ value.get
        }
      }
      Some(res)
    }
  }

  def join(bbf:ByteArrayBloomierFilter, key:String) : Option[Array[Byte]] = {
     GrapevineType.getTypeFromKey(key) match {
       case Some(c) if c == classOf[StringType] => joinString(bbf, key)
       case Some(c) if c == classOf[ByteType] => joinFromBloomierFilter(bbf, key, ByteType.getSize)
       case Some(c) if c == classOf[UnsignedByteType] => joinFromBloomierFilter(bbf, key, UnsignedByteType.getSize)
       case Some(c) if c == classOf[FixedPointType] => joinFromBloomierFilter(bbf, key, FixedPointType.getSize)
       case Some(c) if c == classOf[FloatType] => joinFromBloomierFilter(bbf, key, FloatType.getSize)
       case Some(c) if c == classOf[DateType] => joinFromBloomierFilter(bbf, key, DateType.getSize)
       case Some(c) if c == classOf[TimeType] => joinFromBloomierFilter(bbf, key, TimeType.getSize)
       case Some(c) if c == classOf[LatitudeType] => joinFromBloomierFilter(bbf, key, LatitudeType.getSize)
       case Some(c) if c == classOf[LongitudeType] => joinFromBloomierFilter(bbf, key, LongitudeType.getSize)
       case Some(c) if c == classOf[AgeType] => joinFromBloomierFilter(bbf, key, AgeType.getSize)
       case Some(c) if c == classOf[SpeedType] => joinFromBloomierFilter(bbf, key, SpeedType.getSize)
       case Some(c) if c == classOf[TemperatureType] => joinFromBloomierFilter(bbf, key, TemperatureType.getSize)
       case Some(c) if c == classOf[LevelType] => joinFromBloomierFilter(bbf, key, LevelType.getSize)
       case Some(c) if c == classOf[BitType] => joinFromBloomierFilter(bbf, key, BitType.getSize)
       case Some(c) if c == classOf[UnsignedShortType] => joinFromBloomierFilter(bbf, key, UnsignedShortType.getSize)
       case None => None // Bottom
       case _ => throw new RuntimeException(s"${key} not implemented")
     }
  }
}
