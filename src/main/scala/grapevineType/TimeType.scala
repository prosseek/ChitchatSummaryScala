package grapevineType
/**
 * This is 3 bytes (17 bits/full) implementation of time data
 * For 2 bytes (16 bits) implementation
 *     hour + minute (5 + 6 = 11 bits)
 * For 1 byte (8 bits) implementation
 *     hour + half/minute (5 + 3 = 8 bits) are used
 * Created by smcho on 8/11/14.
 */
class TimeType extends DoubleBitsType((5, 0, 23), (6, 0, 59)) {
  override def getId = 5
}
