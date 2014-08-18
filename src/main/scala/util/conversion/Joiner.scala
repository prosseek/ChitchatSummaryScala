package util.conversion

import bloomierFilter.ByteArrayBloomierFilter
import grapevineType._

/**
 * Created by smcho on 8/17/14.
 */
class Joiner {
  def getInterpretableString(value:Array[Byte]) : String = {
    val sb = new StringBuilder

    value.foreach {v =>
      if (StringType.isPrintable(v.toChar)) sb.append(v.toChar)
      else
        return sb.toString
    }
    sb.toString
  }

  def isInterpretableString(value:Array[Byte]) = {
    value.forall(v => StringType.isPrintable(v.toChar))
  }
  def joinStringFromKeys(bbf:ByteArrayBloomierFilter, key:String) : Option[Array[Byte]] = {
    val sb = new StringBuilder

    (0 until 256).foreach { i =>
      val newKey = s"${key}${i}"
      val res = bbf.get(newKey)

      // when there is no value with key0 assigned, it's None
      if (i == 0) {
        if (res.isEmpty || (res.isDefined && !isInterpretableString(res.get)))
          return None
      }
      if (res.isDefined && isInterpretableString(res.get)) {
        sb.append(ByteArrayTool.byteArrayToString(res.get))
      }
      else {
        sb.append(getInterpretableString(res.get))
        return Some(ByteArrayTool.stringToByteArray(sb.toString))
      }
    }
    throw new RuntimeException(s"Substrings exceed maximum 256")
  }

  def joinString(bbf:ByteArrayBloomierFilter, key:String) : Option[Array[Byte]] = {
    // check if key can be iterpreted itself
    val res = bbf.get(key)
    if (res.isDefined && isInterpretableString(res.get)) res
    else joinStringFromKeys(bbf, key)
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
       case Some(c) if c == classOf[LatitudeType] => joinFromBloomierFilter(bbf, key, LatitudeType.getSize)
       //case Some(c : Class[ByteType]) => None
       case None => null
     }
  }
}
