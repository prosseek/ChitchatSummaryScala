import util.conversion.BitSetTool._
import scala.collection.mutable

val b = mutable.BitSet()
val index = 10
val bs = intToBitSet(index, shift = 5)
b ++ bs

