package grapevineType

/**
 * Age uses 1 byte (8 bits: 0 - 255)
 */
class AgeType extends RangeType(0, 120) {

  override def fromByteArray(b: Array[Byte]): Boolean = {
    // Just check if all the header bits (when byte size is more than one) are zero
    if (super.fromByteArray(b)) {
      // checking at this level means Bottom_r in that it checks the relationship
      if (check(value)) true
      else false // if not in range false is returned
    }
    else false
  }
}
