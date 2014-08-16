package bloomierFilter

import util.conversion.Util._

/**
 * Created by smcho on 8/16/14.
 */
class ByteArrayBloomierFilter (map:Map[String, Array[Byte]], initialM:Int, k:Int, q:Int, initialSeed:Int, maxTry:Int, allowOrder:Boolean = true) {
  val byteSize = getByteSize(q)

  var hasher : BloomierHasher = _ // BloomierHasher(m = m, k = k, q = q, hashSeed = seed)
   // new OrderAndMatchFinder(map = map, m = m, k = k, q = q, initialHashSeed = seed, maxTry = maxTry)
  //var orderAndMatch : OrderAndMatch = _ // oamf.find()
  var table : Array[Array[Byte]] = _ // Array.ofDim[Byte](m, byteSize)
  var seed : Int = -1  //orderAndMatch.hashSeed
  var m : Int = -1
  private var depth : Int = -1

  // generate the table and set table/seed/m
  createTable(map)

  def find() : Option[OrderAndMatch] = {
    var m = initialM
    var oamf : OrderAndMatchFinder = null

    (0 until maxTry).foreach {i =>
      // try with initial m
      //hasher = BloomierHasher(m = initialM, k = k, q = q, hashSeed = initialSeed)
      oamf = new OrderAndMatchFinder(map = map, m = m, k = k, q = q, initialHashSeed = (1 + initialSeed)*i, maxTry = maxTry)
      var orderAndMatch = oamf.find()

      if (orderAndMatch.isDefined) { // found a solution
        if (allowOrder || (!allowOrder && oamf.getDepthCount() == 1)) {
          this.m = m
          this.depth = oamf.getDepthCount()
          return orderAndMatch
        }
      }
      //WARNING - quick and dirty code, it should be optimized to return the best m
      m = (m*1.5).toInt
//      println(s"m = ${m}")
//      println(s"depth = ${oamf.getDepthCount()}")
//      println(s"history = ${oamf.getOrderHistory()}")
//      println(s"tau = ${oamf.getTauList()}")
//      println(s"pi = ${oamf.getPiList()}")
//      println(s"map = ${oamf.getKMap()}\n\n")
    }
    None
  }

  def createTable(map:Map[String, Array[Byte]]) : Int = {
    val
    // find the solution, so create the table and set the hash
    table = Array.ofDim[Byte](initialM, byteSize)
//    seed = orderAndMatch.hashSeed
    initialM
  }

  def _create(map:Map[String, Array[Byte]], orderAndMatch:OrderAndMatch) = {

    for ((key, i) <- orderAndMatch.piList.zipWithIndex) {
      //val value : Int = keysDict(key).asInstanceOf[Int]
      val neighbors = hasher.getNeighborhood(key, seed)
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

  def getDepth() = this.depth
  def analyze(keysDict:Map[String, Any]) = {
    //println(oamf.getOrderHistory())
    for ((key, value) <- keysDict) {
      print(key);print(">> ")
      val neighbors = hasher.getNeighborhood(key, seed)
      for (n <- neighbors) {
        print(n)
        print(":")
      }
      println()
    }
    println()
    //print(orderAndMatch.toString())
  }

  def get(keyInput: String) : Option[Any] = {
    val key = keyInput
    val neighbors = hasher.getNeighborhood(key, seed)
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



  /**
   * Get the size of r (m that is not zero)
   * @return
   */
  def getSize() = {
    initialM - this.table.count(p => p.forall(_ == 0))
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
