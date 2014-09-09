package app

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

/**
 * Created by smcho on 9/3/14.
 */
object GetSizeEfficiency extends App{

  def getSizeEfficiency(filePath:String) = {
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
  }

  val files = Array[String]("string_only","no_string","s1_1", "s1_2", "s2_1", "s2_2", "s3_1", "s3_2")
  //val files = Array[String]("s1_1", "s1_2", "s2_1", "s2_2", "s3_1", "s3_2")
  files.foreach { f =>
    getSizeEfficiency(s"/Users/smcho/code/contextSummary/experiment/size/${f}.data")
  }
}
