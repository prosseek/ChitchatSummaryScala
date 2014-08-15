package grapevineType

/**
 * DMSS format
 *
 * Degree  (-180 - 180) 2**9 = 512 => 9 bits
 * Minute  (0 - 60) 2**6 = 64 => 6 bits
 * Second  (0 - 60) 2**6 = 64 => 6 bits
 * Second' (0 - 99) 2**7 = 128 => 7 bits
 */
class LatitudeType extends QuadrupleBitsType((9, -180, 180), (6, 0, 59), (6, 0, 59), (7, 0, 99)) {
  override def getId = 6
}
