package core

/**
 * The summary contains a key -> value set.
 * The key is a string, and value is gv type
 */
abstract class ContextSummary {
  /**
   * Returns the size of the summary
   *
   * @return
   */
  def getSize() : Int;

  /**
   * Returns the value from the input key
   * The returned value can be null, so Option type is used.
   *
   * @param key
   * @return
   */
  def get(key:String) : Option[AnyVal] ;

  /**
   * create a context summary from dictionary.
   * From the dict (Map of String -> Object), it generates a summary
   * wholeDict is used for complete dictionary
   *
   *
   * @param dict
   * @param wholeDict = the additional information for summary creation
   */
  def create(dict:Map[String, AnyVal], wholeDict:Set[String]);
  def create(dict:Map[String, AnyVal]);
}
