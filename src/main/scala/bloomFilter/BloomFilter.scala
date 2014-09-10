package bloomFilter

import java.nio.file.{Files, Paths}

import util.hash.Hash

import scala.collection.mutable.{BitSet => MBitSet}
import scala.collection.mutable.{Set => MSet}
import scala.io.Source

object BloomFilter {
  def checkInput(bf:BloomFilter, input:String) = {
    val inputs = input.split("[\\s+,;.]").filterNot(_ == "")
    inputs.forall(bf.get(_))
  }

  def getSet(path:String) = {
    val keys = MSet[String]()

    // check if file exists
    if (Files.exists(Paths.get(path))) {
      Source.fromFile(path).getLines().foreach { l =>
        keys += l.trim
      }
    }
    else throw new RuntimeException(s"no file ${path} exits")
    keys.toSet
  }

  /**
   * (1 - (1 - 1/m)^kn)^k
   *
   * @param m
   * @param k
   * @param n
   * @return
   */
  def getFalsePositiveProbability(m:Int, k:Int, n:Int) = {
    val p1 = math.pow((1 - 1.0/m), k*n)
    math.pow((1 - p1), k)
  }
}

/**
 * Created by smcho on 6/30/14.
 */
case class BloomFilter(keys:Set[String], m: Int, k: Int, seed: Int = 0) {
  val bitSet = MBitSet()

  keys.foreach(key => Hash.get(key = key, m = m, k = k, seed = seed).foreach(bitSet.add(_)))
  def get(key:String) = Hash.get(key = key, m = m, k = k, seed = seed).map(bitSet(_)).forall(identity)
  def debug() = println(bitSet.mkString(":"))
  def getFp() = {
    BloomFilter.getFalsePositiveProbability(m=getM, k = getK, n = getN)
  }
  def getM() = m
  def getK() = k
  def getN() = keys.size

  def this(filePath:String, m:Int, k:Int, seed:Int) = {
    this(BloomFilter.getSet(filePath), m, k, seed)
  }
}
