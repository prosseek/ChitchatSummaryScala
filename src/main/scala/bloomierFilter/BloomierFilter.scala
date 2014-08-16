package bloomierFilter

import util.conversion.Util._

import scala.collection.mutable.{Map => MMap}
/**
 * Created by smcho on 6/2/14.
 */

object BloomierFilter {
  //var caseSensitive: Boolean = true
  var maxTry: Int = 5
  var initialSeed: Int = 0
  var useBottomFilter: Boolean = true
  var allowDuplicateHash : Boolean = true

  def checkZeroElement(element: Seq[Byte]) : Boolean = {
    element.forall(_ == 0)
  }

  def checkAllZeroElementsInTable(neighbors: Seq[Int], table: Array[Array[Byte]]) : Boolean = {
    for (n <- neighbors) {
      if (!checkZeroElement(table(n))) return false
    }
    true
  }
}

class BloomierFilter(map:Map[String, Array[Byte]], m:Int, k:Int, q:Int) {

  val hasher = BloomierHasher(m = m, k = k, q = q, hashSeed = BloomierFilter.initialSeed)
  val oamf = new OrderAndMatchFinder(map = map, m = m, k = k, q = q, maxTry = BloomierFilter.maxTry, initialHashSeed = BloomierFilter.initialSeed)
  val orderAndMatch = oamf.find()
  val byteSize = getByteSize(q)

  val table = Array.ofDim[Byte](m, byteSize)
  val hashSeed = -1 // orderAndMatch.hashSeed
  //create(map, orderAndMatch)

  /**
   * Gvien two byteArrays return the xor one by one
   * @param a
   * @param b
   */
  def byteArrayXor(a:Array[Byte], b:Array[Byte]) = {
    if (a.size != b.size) {
      throw new Exception(s"Array size is not the same: ${a.size} != ${b.size}")
    }
    val newArray = new Array[Byte](a.size)
    var i = 0
    for (ba <- a) {
      newArray(i) = (ba ^ b(i)).toByte
      i += 1
    }
    newArray
  }

  def getDepth() = this.oamf.getDepthCount()
  def analyze(keysDict:Map[String, Any]) = {
    println(oamf.getOrderHistory())
    for ((key, value) <- keysDict) {
      print(key);print(">> ")
      val neighbors = hasher.getNeighborhood(key, hashSeed)
      for (n <- neighbors) {
        print(n)
        print(":")
      }
      println()
    }
    println()
    print(orderAndMatch.toString())
  }

  def get(keyInput: String) : Option[Any] = {
    val key = keyInput
    val neighbors = hasher.getNeighborhood(key, hashSeed)
    val mask = hasher.getM(key).toArray.map(_.toByte)
    var valueToRestore = mask

    // If all of the row data in the table is zero, it means it's garbage data
    // Let's not be so smart for a while.
    // This is the routine to get the BOTTOM
    if (BloomierFilter.useBottomFilter && BloomierFilter.checkAllZeroElementsInTable(neighbors, table)) {
    //if (false) {
      //None // Bottom calculation
      Some("Bottom")
    } else {
      for (n <- neighbors) {
        valueToRestore = byteArrayXor(valueToRestore, table(n))
      }
      //decoder.decode(key, valueToRestore, byteSize)
      null
    }
  }

  def create(map:Map[String, Array[Byte]], orderAndMatch:OrderAndMatch) = {

    for ((key, i) <- orderAndMatch.piList.zipWithIndex) {
      //val value : Int = keysDict(key).asInstanceOf[Int]
      val neighbors = hasher.getNeighborhood(key, hashSeed)
      val mask = hasher.getM(key).toArray.map(_.toByte)
      val l = orderAndMatch.tauList(i)
      val L = neighbors(l) // L is the index in the table to store the value

      // only check when the encoded value is null
      if (map(key) != null) {
        val encodedValue = map(key)
        var valueToStore = byteArrayXor(mask, encodedValue)

        for ((n, j) <- neighbors.zipWithIndex) {
          if (j != l) {
            valueToStore = byteArrayXor(valueToStore, table(n))
          }
        }
        table(L) = valueToStore
      }
    }
  }

  /**
   * Get the size of r (m that is not zero)
   * @return
   */
  def getSize() = {
    m - this.table.count(p => p.forall(_ == 0))
  }

  def size() = {
    getSize() * byteSize
  }

  def printContents() : Unit = printContents(map.keys)

  def printContents(keys: Iterable[String]) : Unit = {
    println("BL---------------------------------------------")
    println(s"Size in bytes : ${size()}")
    for (key <- keys) {
      println(s"KEY($key) => ${get(key).getOrElse(null)}")
    }
    println("---------------------------------------------BL")
  }
}
