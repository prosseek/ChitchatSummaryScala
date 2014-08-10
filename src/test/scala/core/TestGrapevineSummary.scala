package core

import org.scalatest._

// Grapevine is an abstract class, so we need to make a concrete class just to test GrapevineSummary
class A extends GrapevineSummary {
  override def getSize(): Int = 0
  override def get(key: String): Option[AnyVal] = None
  override def create(dict: Map[String, AnyVal], wholeDict: Set[String]): Unit = {}
}

/**
 * Created by smcho on 8/10/14.
 */

class TestGrapevineSummary extends FunSuite  {
  test ("Test create") {
    val m = Map[String, AnyVal]("A"->10, "B"->20.5)
    val a = new A()
    a.create(m)
    println(a.get("A"))
  }
}
