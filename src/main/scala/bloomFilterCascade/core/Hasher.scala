package core

import util.hash.Hash

object Hasher {
  var useUnique = false

  def get(key:String, m:Int, k:Int, seed:Int) = {
    if (Hasher.useUnique)
      Hash.getUniqueHashes(key, count = k, maxVal = m, startSeed = seed)
    else
      Hash.getHashes(key, count = k, maxVal = m, startSeed = seed)
  }

}

