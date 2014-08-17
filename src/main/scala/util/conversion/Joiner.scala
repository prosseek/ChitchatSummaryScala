package util.conversion

import bloomierFilter.ByteArrayBloomierFilter
import grapevineType.StringType

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
  def joinStringFromKeys(bbf:ByteArrayBloomierFilter, key:String) : Option[String] = {
    val sb = new StringBuilder

    (0 until 256).foreach { i =>
      val newKey = s"${key}${i}"
      val res = bbf.get(newKey)
      if (res.isDefined && isInterpretableString(res.get)) {
        sb.append(ByteArrayTool.byteArrayToString(res.get))
      }
      else {
        sb.append(getInterpretableString(res.get))
        return Some(sb.toString)
      }
    }
    throw new RuntimeException(s"Substrings exceed maximum 256")
  }

  def joinString(bbf:ByteArrayBloomierFilter, key:String) : Option[String] = {
    // check if key can be iterpreted itself
    val res = bbf.get(key)
    if (res.isDefined && isInterpretableString(res.get)) Some(ByteArrayTool.byteArrayToString(res.get))
    else joinStringFromKeys(bbf, key)
  }
}
