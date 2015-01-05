package app

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.{Map => MMap}
import scala.io.Source

/**
 * Created by smcho on 9/3/14.
 */
object GetSizeEfficiency extends App{

  var maxReducBF: Double = Double.MinPositiveValue
  var minReducBF: Double = Double.MaxValue
  var maxGainBF: Double = Double.MinPositiveValue
  var minGainBF: Double = Double.MaxValue

  var maxReducCBF: Double = Double.MinPositiveValue
  var minReducCBF: Double = Double.MaxValue
  var maxGainCBF: Double = Double.MinPositiveValue
  var minGainCBF: Double = Double.MaxValue

  val result = MMap[String, String]()
  def getSizeEfficiency(filePath:String, count:Int = 0) = {
    val fileName = filePath.split("/")(filePath.split("/").size - 1)
    val res = Source.fromFile(filePath).getLines()

    //println(Source.fromFile(filePath).getLines())
    val bf = ArrayBuffer[(Int, Int)]()
    val cbf = ArrayBuffer[(Int, Int)]()
    val labeled = ArrayBuffer[Int]()
    val complete = ArrayBuffer[Int]()

    res.foreach { v =>
      val values = v.split("\\s+")
      val i = values(0).toInt
      bf += (i -> values(1).toDouble.toInt)
      cbf += (i -> values(3).toDouble.toInt)
      labeled += values(2).toInt
      complete += values(4).toInt
    }
    val labeledSize = labeled(0)
    val completeSize = complete(0)
    assert(labeled.forall(_ == labeledSize))
    assert(complete.forall(_ == completeSize))

    // find the maximum value in bf with an index
    val bf_minimal = bf.sortBy(_._2)
    val cbf_minimal = cbf.sortBy(_._2)
    val file_name = filePath.split("/").last

    val size_efficiency_bf = (1.0 - bf_minimal(0)._2.toFloat/labeledSize)*100
    val size_efficiency_cbf = (1.0 - cbf_minimal(0)._2.toFloat/labeledSize)*100

    val size_gain_bf = -(1.0 - bf_minimal(0)._2.toFloat/completeSize)*100
    val size_gain_cbf = -(1.0 - cbf_minimal(0)._2.toFloat/completeSize)*100

    println(s"${file_name}")
    println(s"size efficiency for bf - ${size_efficiency_bf} - ${size_gain_bf}")
    println(s"size efficiency for cbf - ${size_efficiency_cbf} - ${size_gain_cbf}")
    println("")
    result(s"${count}") = "s%d & %4.2f & %4.2f && %4.2f & %4.2f \\\\".format(count,
      size_efficiency_bf, size_efficiency_cbf, size_gain_bf, size_gain_cbf)

    if (!(fileName.startsWith("string") || fileName.startsWith("no"))) {
      if (size_efficiency_bf > maxReducBF) maxReducBF = size_efficiency_bf
      if (size_efficiency_cbf > maxReducCBF) maxReducCBF = size_efficiency_cbf
      if (size_efficiency_bf < minReducBF) minReducBF = size_efficiency_bf
      if (size_efficiency_cbf < minReducCBF) minReducCBF = size_efficiency_cbf

      if (size_gain_bf > maxGainBF) maxGainBF = size_gain_bf
      if (size_gain_cbf > maxGainCBF) maxGainCBF = size_gain_cbf
      if (size_gain_bf < minGainBF) minGainBF = size_gain_bf
      if (size_gain_cbf < minGainCBF) minGainCBF = size_gain_cbf

    }

  }

  val files = Array[String]("s1_1", "s1_2", "s2_1", "s2_2", "s3_1", "s3_2", "string_only","no_string")
  //val files = Array[String]("s1_1", "s1_2", "s2_1", "s2_2", "s3_1", "s3_2")
  var count = 1
  files.foreach { f =>
    getSizeEfficiency(s"/Users/smcho/research/contextSummary/experiment/size/${f}.data", count)
    count += 1
  }
  // sort the keys
  val keys = result.keys.toList.sorted
  for (k <- keys) {
    println(s"${result(k)}")
  }
  println("REDUC BF max:%4.2f min:%4.2f".format(maxReducBF, minReducBF))
  println("REDUC CBF max:%4.2f min:%4.2f".format(maxReducCBF, minReducCBF))

  println("GAIN BF max:%4.2f min:%4.2f".format(maxGainBF, minGainBF))
  println("GAIN CBF max:%4.2f min:%4.2f".format(maxGainCBF, minGainCBF))
}
