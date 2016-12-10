package app

import java.lang.{String => JString}
import java.io._

/**
  * Created by smcho on 12/9/16.
  */
object JSONGenerator {

  def toFile(content:JString, path:JString) = {
    val pw = new PrintWriter(new File(path))
    pw.write(content)
    pw.close
  }

  def getRandomInt(minimum:Int=1, maximum:Int) = {
    val r = scala.util.Random
    r.nextInt(maximum - minimum) + minimum
  }

  def generateString(length: Int) = {
    val r = new scala.util.Random
    val sb = new StringBuilder
    for (i <- 1 to length) {
      sb.append(r.alphanumeric.filter(_.isLetter).head)
    }
    sb.toString
  }

  def generateArray(length:Int) = {
    val r = new scala.util.Random
    val sb = new StringBuilder
    sb.append("[")
    for (i <- 1 to length) {
      sb.append(r.nextInt(100).toString + ",")
    }
    sb.toString.slice(0, sb.length - 1) + "]"
  }

  def generateValue(valueType:Int) = {
    var r = new scala.util.Random
    val ret = valueType match  {
      case 0 => r.nextInt(1000).toString
      case 1 => generateArray(getRandomInt(2,5))
      case 2 => generateString(getRandomInt(2,10))
    }
    ret.toString

//    r.nextInt(1000).toString
//    generateArray(10)
  }

  def generateNonSchemaSummary(length:Int, path:JString) = {
    val sb = new StringBuilder
    var r = new scala.util.Random

    sb.append("{\n")
    for (i <- 1 to length) {
      val key = generateString(getRandomInt(5, 10))
      val r2 = r.nextInt(3)

      val x  = r2 match {
        case 2 => getStringElement(key, generateValue(r2))
        case 0 => getElement(key + "_i", generateValue(r2)) // integer
        case 1 => getElement(key + "_a", generateValue(r2)) // integer
      }
      sb.append(x)
    }
    val res = sb.toString.slice(0, sb.length - 2) + "\n}\n"
    toFile(res, path)
  }

  def getElement(key:JString, value:JString) = {
    "  \"" + key + "\" : " + value + ",\n"
  }
  def getStringElement(key:JString, value:JString) = {
    "  \"" + key + "\" : \"" + value + "\",\n"
  }

  def generateSchemaSummary(schemaType:Int, path:JString, length:Int) = {
    def getDateTime = {
      getElement("date", "[11, 12, 11]") + getElement("time", "[12, 14]")
    }
    def getLocation = {
      getElement("latitude", "[1, 2, 3, 4]") + getElement("longitude", "[1, 2, 3, 4]")
    }
    def getSender = {
      // sender = (name, id?) | ("gchat id")
      val r = scala.util.Random
      val r2 = if (r.nextInt(1) == 0) getStringElement("id", generateString(getRandomInt(5, 10))) else ""
      r.nextInt(1) match {
        case 0 => getStringElement("name", generateString(getRandomInt(5, 10))) + r2
        case 1 => getStringElement("gchat id", generateString(getRandomInt(5, 10)))
      }
    }
    def getUnit(i:Int = 0) = {
      val r = scala.util.Random
      val unitName = if (i == 0) "unit" else "unit" + i.toString
      r.nextInt(1) match {
        case 0 => getStringElement(unitName, "lux")
        case 1 => getStringElement(unitName, "km")
      }
    }
    def getName(i:Int = 0) = {
      val r = scala.util.Random
      val unitName = if (i == 0) "name" else "name" + i.toString
      getStringElement(unitName, generateString(getRandomInt(5, 10)))
    }
    def getId(i:Int = 0) = {
      val r = scala.util.Random
      val unitName = if (i == 0) "id" else "id" + i.toString
      getStringElement(unitName, generateString(getRandomInt(5, 10)))
    }
    def getNews = {
      val r = scala.util.Random
      r.nextInt(1) match {
        case 0 => getStringElement("event", generateString(getRandomInt(7, 15)))
        case 1 => getStringElement("advertisement", generateString(getRandomInt(10, 20)))
      }
    }
    def getCount = {
      getElement("count", getRandomInt(maximum = 1000, minimum = 5).toString)
    }
    def getValue(i:Int = 0) = {
      val valueName = if (i == 0) "value" else "value" + i.toString
      getElement(valueName, getRandomInt(maximum = 10000, minimum = 5).toString)
    }
    def getComment = {
      getStringElement("comment", generateString(getRandomInt(10, 30)))
    }

    def generateType1() = {
      // (sender news datetime location)
      val sb = new StringBuilder
      sb.append(getSender)
      sb.append(getNews)
      sb.append(getDateTime)
      sb.append(getLocation)

      sb.toString.slice(0, sb.size - 2)
    }

    def generateType2() = {
      val sb = new StringBuilder
      sb.append(getSender)
      sb.append(getDateTime)
      sb.append(getValue())
      sb.append(getCount)
      sb.append(getUnit())
      sb.append(getComment)

      sb.toString.slice(0, sb.size - 2)
    }

    def get_name_id_value_unit(i:Int) = {
      val sb = new StringBuilder
      sb.append(getName(i))
      sb.append(getId(i))
      sb.append(getValue(i))
      sb.append(getUnit(i))

      sb.toString.slice(0, sb.size - 1) + "\n"
    }

    def generateType3(length:Int) = {
      val sb = new StringBuilder
      var r = scala.util.Random

      sb.append(getCount)
      sb.append(getDateTime)

      if (r.nextInt(1) == 0) sb.append(getLocation)
      for (i <- 1 to length) {
        sb.append(get_name_id_value_unit(i))
      }
      sb.toString.slice(0, sb.size - 2)
    }

    schemaType match {
      case 1 => toFile("{\n" + generateType1() + "\n}\n", path)
      case 2 => toFile("{\n" + generateType2() + "\n}\n", path)
      case 3 => toFile("{\n" + generateType3(length) + "\n}\n", path)
      case _ => new RuntimeException("Only 1,2,3 type is allowed")
    }

  }
}
