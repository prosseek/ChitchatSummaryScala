package grapevineType

import GrapevineType._
import org.scalatest._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by smcho on 8/10/14.
 */
class TestGrapevineType extends FunSuite {

  test ("Simple test") {
    val a = Integer
    assert(a == Integer)
    //assert(GrapevineType.values.size == 2)
  }

  test ("getTypeFromKey test") {
    val keys = Array("age of john", "speed of a car")
    val values = Array(Some(Age), Some(Speed))
    keys.zipWithIndex.foreach { case(key, index) =>
        val v = values(index)
        assert(getTypeFromKey(key) == v)
    }
  }

  test ("getTypeFromValue test") {
    val vs = new ArrayBuffer[Object]()
    vs += 1.asInstanceOf[Object]
    vs += (1.2).asInstanceOf[Object]
    val values = Array(Some(Integer), Some(FloatingPoint))

    vs.zipWithIndex.foreach { case(v, index) =>
      val va = values(index)
      assert(getTypeFromValue(v.asInstanceOf[Object]) == va)
    }
  }

}
