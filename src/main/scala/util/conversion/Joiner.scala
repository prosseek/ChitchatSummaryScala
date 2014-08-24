package util.conversion

import bloomierFilter.ByteArrayBloomierFilter
import grapevineType._

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
    val sb = new StringBuilder

    (0 until 256).foreach { i =>
      val newKey = s"${key}${i}"
      val res = bbf.get(newKey)

      // when there is no value with key0 assigned, it's None
      if (i == 0) {
        // bug [2014/08/23]
        // string should start with " character
        if (res.isEmpty) return None
        //if (res.get(0) != '\"') return None
        if (!isInterpretableString(res.get)) return None
      }
      if (res.isDefined && isInterpretableString(res.get)) {
        sb.append(ByteArrayTool.byteArrayToString(res.get))
      }
      else { // this is the last one
        sb.append(getInterpretableString(res.get))
        return Some(ByteArrayTool.stringToByteArray(sb.toString))
      }
    }
    throw new RuntimeException(s"Substrings exceed maximum 256")
  }

  def joinString(bbf:ByteArrayBloomierFilter, key:String) : Option[Array[Byte]] = {
    // [2014/08/21] bug fix
    // In some cases re1 is printable character (UW{1) for this case, we return the maximum string
    val res1 = bbf.get(key)
    val res2 = joinStringFromKeys(bbf, key)

    if (res1.isEmpty) return res2
    if (res2.isEmpty) return res1

    val lenght1 = res1.get.size
    val length2 = res2.get.size

    if (length2 > lenght1) return res2
    res1
  }

  def joinFromBloomierFilter(bbf:ByteArrayBloomierFilter, key:String, dataWidth:Int) : Option[Array[Byte]] = {
    def getIterationNumber(dataWidth:Int, tableWidth:Int) = {
      val q1 = dataWidth / tableWidth
      val q2 = dataWidth % tableWidth
      if (q2 == 0) q1
      else q1 + 1
    }

    val tableWidth = bbf.getWidth()
    if (tableWidth >= dataWidth) bbf.get(key)
    else {
      val range = Range(0, getIterationNumber(dataWidth, tableWidth))
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
