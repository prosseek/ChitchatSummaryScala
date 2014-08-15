package grapevineType

import BottomType._

/**
 * Created by smcho on 8/11/14.
 */
class LocationType extends GrapevineType {
  override def set(value: Any): Unit = {
    this.value = asInstanceOf[(Int, Int, Int)]
  }
  override def toByteArray(goalSize: Int): Array[Byte] = {
    null
  }
  def fromByteArray(b: Array[Byte]): BottomType = {
    NoError
  }

  /*
  var dd : Double = 0.0
  var d :Int = 0
  var m :Int = 0
  var s1 :Int = 0
  var s2 :Int = 0

  def setDmsDd(d:Int, m:Int, s1:Int, s2:Int) = {
    this.d = d
    this.m = m
    this.s1 = s1
    this.s2 = s2
    this.dd = LocationType.dms2dd(d,m,s1,s2)
  }

  def this(d:Int, m:Int, s1:Int, s2:Int, t:Int) = {
    this(t)
    setDmsDd(d,m,s1,s2)
  }

  def this(dd:Double, t:Int) = {
    this(t)
    this.dd = dd
    val (d, m, s1, s2) = LocationType.dd2dms(dd)
    this.d = d
    this.m = m
    this.s1 = s1
    this.s2 = s2
  }

  def setFromBitSet(bitSet:BitSet, t:Int) = {
    var dd = BitSet()
    var mm = BitSet()
    var ss1 = BitSet()
    var ss2 = BitSet()
    for (i <- bitSet) {
      if (i < t) {
        dd += i
      } else if (i < t + 6) {
        mm += i
      } else if (i < t + 6 + 6) {
        ss1 += i
      } else {
        ss2 += i
      }
    }
    this.d = bitSetToByte(dd, 0)
    this.m = bitSetToByte(mm, t)
    this.s1 = bitSetToByte(ss1, t + 6)
    this.s2 = bitSetToByte(ss2, t + 6 + 6)
    this.dd = LocationType.dms2dd(d,m,s1,s2)
  }

  def this(bitSet:BitSet, t:Int) = {
    this(t)
    setFromBitSet(bitSet, t)
  }

  def this(b:Array[Byte], t:Int) = {
    this(byteArrayToBitSet(b),t)
  }

  def getDms() = (this.d, this.m, this.s1, this.s2)

  def toBitSet() = {
    // translate from d/m/s1/s2 to byte array
    // degree -> +-90 (7 bit) +- 180 (8 bit)
    val dd = byteToBitSet(d.toByte, 0)
    val mm = byteToBitSet(m.toByte, t)
    // minute 60 -> 6 bit (64)
    val ss1 = byteToBitSet(s1.toByte, t + 6)
    val ss2 = byteToBitSet(s2.toByte, t + 6 + 6)
    dd ++ mm ++ ss1 ++ ss2
  }
  def toByteArray() = {
    bitSetToByteArray(toBitSet())
  }
  */
}
