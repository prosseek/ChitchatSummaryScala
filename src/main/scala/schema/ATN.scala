package schema

import summary.FBFSummary

import scala.collection.mutable.{Map => MMap}
import scala.util.control.Breaks._

class ATN(fbf:FBFSummary) {
  val grammar = Map[String, Seq[String]](
    "notification" -> Seq("sender", "event", "datetime", "location"),
    "sensor" -> Seq("count", "datetime", "location", "value+")
  )

  val alias = Map[String, Seq[String]](
    "count" -> Seq("ubyte")
  )

  val typedefs = Map[String, Seq[String]](
    "sender" -> Seq("name", "id?", "location?"),
    "location" -> Seq("longitude", "latitude"),
    "datetime" -> Seq("date", "time"),
    "value" -> Seq("name", "id?", "value", "unit")
  )

  def processQuestionMark(key:String) = {
    val newMap = MMap[String, Any]()
    val res = atnForKey(key)
    if (res != null) res
    else MMap[String, Any]() // add nothing even though not fully recovered
  }

  /**
    * Returns retrieved set of (key -> value)
    *
    * ==== Example ====
    * {{{
    * X+ and X = (a, b, c), and fbf has 5 items (a1 - a5), (b1 - b5), (c1 - c5)
    *
    * returns
    *   a1 -> value
    *   b1 -> value
    *   c1 -> value
    *   ...
    *   a5 -> value
    *   ...
    * }}}
    *
    * @param key
    * @return
    */
  def processPlusMark(key:String): MMap[String, Any] = {
    val temp = MMap[String, Any]()
    /**
      * With typedef X+ and X = (a, b, c)
      * Iterate over (a,b,c) with attached count
      *
      * @param types
      * @param count
      */
    def getRelatedTypes(types: Seq[String], count: Int) : MMap[String, Any] = {
      val temp = MMap[String, Any]()
      types foreach {
        t => {
          val key = t + count.toString
          val result = fbf.get(key)
          if (result.isDefined) temp += (key -> result.get)
          else return null
        }
      }
      temp
    }
    val defs = typedefs.get(key)
    if (defs.isEmpty) return null

    var count = 1
    breakable {
      do {
        val result = getRelatedTypes(defs.get, count)
        if (result == null) break
        else
          temp ++= result
      }
      while (true)
    }
    temp
  }

  /**
    * returns map when retrieved elements, returns null when error
    *
    * @param key
    * @return
    */
  def atnForKey(key:String) :  MMap[String, Any]= {
    val temp = MMap[String, Any]()

    val newKey = if (key.endsWith("?") || key.endsWith("+")) key.slice(0, key.size-1) else key
    val relatedTypes = typedefs.get(newKey)
    if (relatedTypes.isEmpty) {
      val result = fbf.get(newKey)
      if (result.isEmpty) return null
      else temp += (key -> result.get)
    }
    else {
      val types = relatedTypes.get
      types foreach {
        t => t match {
          case t if (t.endsWith("?")) => {
            val result = processQuestionMark(t.slice(0, t.size - 1))
            temp ++= result
          }
          case t if (t.endsWith("+")) => {
            val result = processPlusMark(t.slice(0, t.size - 1))
            if (result == null) return null // error in t no output
          }
          case t => {
            val result = atnForKey(t)
            if (result == null) return null
            else temp ++= result
          }
        }
      }
    }
    temp
  }

  /**
    * given a grammar, retrieve all the elements in the map
    *
    * @param inputGrammar
    * @return null if there is no matching grammar elements in the fbf
    */
  def atn(inputGrammar: String): Map[String, Any] = {
    val registers = MMap[String, Any]()

    val relatedTypes = grammar.get(inputGrammar)
    if (relatedTypes.isEmpty) return null
    relatedTypes.get foreach { element =>
      {
        val result = atnForKey(element)
        if (result == null) return null
        registers ++= result
      }
    }
    registers.toMap
  }
}
