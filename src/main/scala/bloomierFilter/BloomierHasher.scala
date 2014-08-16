package bloomierFilter

import util.hash.Hash
import util.conversion.Util
/**
 * Created by smcho on 5/31/14.
 */
//case class BloomierParameter(val m:Int, val k:Int, val q:Int, val hashSeed:Int)

object BloomierHasher {
  var uniqueNeighbors = false
}

case class BloomierHasher(val m:Int, val k:Int = 3, val q:Int = 32, val hashSeed:Int = 0) {
  val byteSize = Util.getByteSize(q)

//  def this(p:BloomierParameter) = {
//    this(p.m, p.k, p.q, p.hashSeed)
//  }

  /**
   * Just create one byteSize (8 * byteSize bits) of random 0s and 1s
   * The startSeed = hashSeed*123 is just to create random values
   *
   * @param key
   * @return
   */
  def getM(key: String, hashSeed:Int = 1) = {
    Hash.getUniqueHashes(key = key, count = this.byteSize, maxVal = 256, startSeed = hashSeed*123)
  }

  // This can be non-unique filters to get smaller m
  def getNeighborhood(key:String, hashSeed:Int = hashSeed) = {
    if (BloomierHasher.uniqueNeighbors)
      Hash.getUniqueHashes(key = key, count = this.k, maxVal = this.m, startSeed = hashSeed)
    else {
      // [2014/08/16] bug fix, there should be no duplication
      // for example, [1,2,2] -> makes 2 as non-singleton in the orderAndMatchFinder
      Hash.getHashes(key = key, count = this.k, maxVal = this.m, startSeed = hashSeed).toSet.toList
    }
  }
}
