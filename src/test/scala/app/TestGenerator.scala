package app

/**
  * Created by smcho on 12/9/16.
  */
object TestGenerator {

  def testFor(testCount:Int, directory:String, fileName:String) = {
    for (i <- 1 to testCount) {
      val r = scala.util.Random
      val filePath = directory + fileName + i.toString
      val schemaType = r.nextInt(3) + 1

      fileName match {
        case "schema" => JSONGenerator.generateSchemaSummary(path = filePath, length = r.nextInt(15) + 2, schemaType = schemaType)
        case "non-schema" => JSONGenerator.generateNonSchemaSummary(path = filePath, length = r.nextInt(15) + 2)
        case _ => new RuntimeException("Wrong name " + fileName)
      }
    }
  }
}
