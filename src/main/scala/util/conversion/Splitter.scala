package util.conversion

import grapevineType.GrapevineType
import collection.mutable.{Map => MMap}
/**
 * Created by smcho on 8/17/14.
 */
class Splitter {
  /**
   *
   * "key" -> "Hello" 2 bytes split example
   * "key1" -> "He"
   * "key2" -> "ll"
   * "key3" -> "o"
   * "key4" .. maps to garbage to be detected as Bottom
   *
   * @param key
   * @param value
   * @param byteSize
   * @return
   */
  def split(key:String, value:GrapevineType, byteSize:Int) : Map[String, Array[Byte]] = {
    split(key, value.toByteArray(), byteSize)
  }

  def split(key:String, value:Array[Byte], byteSize:Int) : Map[String, Array[Byte]] = {
    /**
     * 10, 3 => (3,3,3,1)
     * Given 10, 3 -> 4 (total division), 2 (3 - 1) (the number of padding for the last element)
     *                                    0 (3 - 0 => 3)
     *
     * @param valueSize
     * @param byteWidth
     * @return
     */
    def getCount(valueSize:Int, byteWidth:Int) = {
      val q1 = valueSize/byteWidth
      val q2 = valueSize % byteWidth
      val q3 = byteWidth - q2 // for the padding calculation

      if (q2 == 0) (q1,     0)
      else         (q1 + 1, q3)
    }

    if (value.size <= byteSize) {
      Map[String, Array[Byte]](key -> (value ++ new Array[Byte](byteSize - value.size)))
    } else {
      val (count, padding) = getCount(value.size, byteSize)
      val map = MMap[String, Array[Byte]]()
      (0 until count - 1).foreach { index =>
        val slicedBytes = value.slice(byteSize*index, byteSize*(index + 1))
        map += (s"${key}${index}" -> slicedBytes)
      }
      // fill in the last element
      map += (s"${key}${count-1}" -> (value.slice(byteSize*(count-1),value.size) ++ new Array[Byte](padding)))
      map.toMap
    }
  }
}
