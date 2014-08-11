package util

import grapevineType.GrapevineType._
/**
 * Created by smcho on 8/10/14.
 */
class GrapevineTypeChecker {
  def check(input: GrapevineType, value: Object) : Boolean = {
    input match {
      case Age =>
        true
    }
  }
}
