package bloomierFilter

/**
 * Created by smcho on 6/1/14.
 */
import scala.collection.mutable.{Map => MMap, ListBuffer}
import util.hash.Hash

case class OrderAndMatch(val hashSeed:Int, val piList:List[String], val tauList:List[Int])

class OrderAndMatchFinder(val map:Map[String, Any],
                          val m:Int, val k:Int, val q:Int, val maxTry:Int = 5, val initialHashSeed:Int = 0) {

  var kMap = MMap[String, List[Int]]()
  var piList = List[String]()
  var tauList = List[Int]()
  var hashSeed = 0

  // for debugging purposes
  private var depthCount = 0
  private var orderHistory : MMap[Int, Any] = _

  /**
   * This is for debugging purposes
   * @param key
   */
  def getNeighbors(key:String) = {
    Hash.getUniqueHashes(key = key, count = k, maxVal = m, startSeed = hashSeed)
  }

  def findMatch(remainingKeysDict:MMap[String, Any],
                hasher:BloomierHasher): Boolean = {

    // when there is no dictionary for Pi and Tau left, it means everything is ordered
    if (remainingKeysDict.size == 0) return true

    var piTemp = ListBuffer[String]()
    var tauTemp = ListBuffer[Int]()

    val tweaker = new SingletonFindingTweaker(remainingKeysDict.toMap, hasher)

    // find
    for (k <- remainingKeysDict.keys) {
      // find the key has k that is not occupied by others
      val res = tweaker.tweak(k)
      // not -1 means that they found singular, so delete them.
      if (res != SingletonFindingTweaker.NONSINGLETON) { // when the result is **singleton**
        piTemp += k
        tauTemp += res
      }
    }

    // none of the keys in remainingKeys have empty spot, so return false and try again
    if (piTemp.size == 0) return false

    // remove all piTemp members from the remaining key and try again.
    for (pi <- piTemp) remainingKeysDict -= pi

    if (remainingKeysDict.size != 0) {
      if (findMatch(remainingKeysDict, hasher) == false)  return false
    }

    // The deepest level values come this first recursively.
    orderHistory(depthCount) = piTemp
    depthCount += 1

    piList ++= piTemp
    tauList ++= tauTemp
    piTemp.foreach {k => kMap(k) = hasher.getNeighborhood(k)}

    return true
  }

  // API
  // There are only three APIs
  def getDepthCount() = depthCount
  def getOrderHistory() = orderHistory
  def getPiList() = piList
  def getTauList() = tauList
  def getKMap() = kMap

  def find() : Option[OrderAndMatch] = {
    var remainKeys = MMap(this.map.toSeq: _*)
    val hashSeedList = scala.collection.mutable.ListBuffer[Int]()
    for (i <- 0 until maxTry) {
      // when initialHashSeed i 0, the new HashSeed is always 0
      var newHashSeed = (initialHashSeed + 1) * i * 100
      hashSeedList += newHashSeed
      val h = BloomierHasher(hashSeed = newHashSeed, m = m, k = k, q = q)

      orderHistory = MMap[Int, Any]()
      depthCount = 0

      if (findMatch(remainKeys, h)) {
        this.hashSeed = newHashSeed
        return Some(OrderAndMatch(hashSeed = newHashSeed, this.piList, this.tauList))
      } else {
        remainKeys = MMap(this.map.toSeq: _*)
      }
    }
    None
    //throw new RuntimeException(s"Over max attempt ${maxTry} with m ${m}")
  }
}
