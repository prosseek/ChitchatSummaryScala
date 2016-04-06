package util.header

object Header {
  def size = 1
  def serialize(name:String) = {
    name match {
      case name if name == summary.JsonSummary.name => Array[Byte](0)
      case name if name == summary.CompresseJSonSummary.name => Array[Byte](1)
      case "labeled" => Array[Byte](2)
      case "cbf" => Array[Byte](3)
      case "fbf" => Array[Byte](4)
      case "complete" => Array[Byte](5)
      case _ => throw new RuntimeException(s"Wrong name ${name}")
    }
  }
  def deserialize(value: Array[Byte]) : String = {
    val v = value(0)
    v match {
      case 0 => summary.JsonSummary.name
      case 1 => summary.CompresseJSonSummary.name
      case 2 => "labeled"
      case 3 => "cbf"
      case 4 => "fbf"
      case 5 => "complete"
      case _ => throw new RuntimeException(s"Not supported value ${value}")
    }
  }
}
