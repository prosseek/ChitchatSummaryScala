package util.io

import core.LabeledSummary
import grapevineType._

import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/19/14.
 */
object String {
  def parseTuple(i:String) = {
    val input = i.replaceAll("""\s+""", "")
    if (input(0) == '(' && input(input.size-1) == ')') {
        input.substring(1, input.size-1).split(",").map(Integer.parseInt) match {
          case Array(a) => (a)
          case Array(a,b) => (a,b)
          case Array(a,b,c) => (a,b,c)
          case Array(a,b,c,d) => (a,b,c,d)
          case _ => throw new RuntimeException(s"${input} cannot be translated into tuples")
        }
    } else {
      throw new RuntimeException(s"Input(${input}) should start with (, and end with )")
    }
  }

  def parseValue(key:String, value:String) = {
    GrapevineType.getTypeFromKey(key) match {
      case Some(c) if (c == classOf[FloatType]) => value.toFloat
      case Some(c) if (c == classOf[StringType]) => value
      case Some(c) if (c == classOf[ByteType] || c == classOf[UnsignedByteType]) => Integer.parseInt(value)
      case _ => if (value.trim.startsWith("(")) parseTuple(value)
                else Integer.parseInt(value)
    }
  }

  def parseLine(input:String) = {
    input.split("->") map {i => i.trim} match {
      case Array(l, r) => (l, parseValue(l, r))
      case _ => throw new RuntimeException(s"${input} is not correctly formed")
    }
  }

  def parseSummary(input:String) = {
    val map = MMap[String, Any]()
    val ls = new LabeledSummary

    input.split("\n").foreach { i =>
      //val s = i.replaceAll("""\s+""", "")
      if (i.trim.size > 0)
        parseLine(i.trim) match {
          case (key, value) => map(key) = value
        }
    }

    ls.create(map.toMap)
    ls
  }
}
