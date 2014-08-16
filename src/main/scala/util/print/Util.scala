package util.print

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
}
