package grapevineType

import util.conversion.Util
/**
 * Created by smcho on 8/22/14.
 */
trait FormatChanger {
  def dms2dd(v:Any) : Double = {
    Util.dms2dd(v)
  }
  def dms2dd(d:Int, m:Int, s1:Int, s2:Int) = {
    Util.dms2dd(d,m,s1,s2)
  }
}
