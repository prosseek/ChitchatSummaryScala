package grapevineType

/**
 * Created by smcho on 8/11/14.
 */
trait RangeChecker {
  def check[T <: AnyVal](value:T, mini:T, maxi:T) (implicit ord: Ordering[T]) : Boolean = {
    import ord._
    (value >= mini && value <= maxi)
  }
}
