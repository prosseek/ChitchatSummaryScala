package grapevineType

import org.scalatest._

/**
 * Created by smcho on 8/13/14.
 */
class TestDateType extends FunSuite {
  test ("simple test") {
    val t = new DateType
    t.set((2013, 10, 22))
    assert(t.get() == (2013, 10, 22))
    t.set(2013, 10, 22)
    assert(t.get() == (2013, 10, 22))
  }

  test ("range error test") {
    val t = new DateType
    intercept[RuntimeException] {
      t.set(1013, 10, 25)
    }
    intercept[RuntimeException] {
      t.set(2013, 16, 25)
    }
    intercept[RuntimeException] {
      t.set(2013, 10, 44)
    }
  }

  test("to/from byteArray") {
    val t = new DateType
    t.set(2014, 10, 11)
    val b = t.toByteArray(2)
    t.fromByteArray(b)
    println(t.get())
  }
}
