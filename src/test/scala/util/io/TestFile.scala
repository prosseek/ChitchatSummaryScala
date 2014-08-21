package util.io

import org.scalatest.{BeforeAndAfter, FunSuite}

/**
 * Created by smcho on 8/19/14.
 */
class TestFile extends FunSuite with BeforeAndAfter {
  test ("simple") {
    val filePath = "src/test/scala/util/io/summaries.txt"
    File.fileToSummary(filePath).foreach { s =>
      print(s)
      println("")
    }
    //print(new File(".").getCanonicalPath())
  }

}
