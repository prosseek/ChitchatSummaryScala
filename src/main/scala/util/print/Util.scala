package util.print

import util.conversion.ByteArrayTool._
/**
 * Created by smcho on 8/16/14.
 */

object Util {
  def printTable(t: Array[Array[Byte]]) : Unit = {
    println(s"TABLE HEIGHT ${t.size} WIDTH ${t(0).size}")
    (0 until t.size).foreach { i =>
      println(s"${i}:${t(i).mkString(":")}")
    }
    println("---------------------------")
  }
  def getString(map:Map[String, Array[Byte]], showInChar:Boolean = false) : String = {
    val sb = new StringBuilder
    val keys = map.keys.toList.sorted
    keys.map {key =>
      if (showInChar) {
        sb.append(s"${key}-${byteArrayToStringNoInterpret(map(key))}\n")
      }
      else
        sb.append(s"${key}-(${map(key).mkString(":")})\n")
    }
    sb.toString()
  }
}
