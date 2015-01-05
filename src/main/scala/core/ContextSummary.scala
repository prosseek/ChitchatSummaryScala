package core

import grapevineType.BottomType.BottomType

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
  def getSize() : (Int, Int, Int);

  /**
   * Returns the value from the input key
   * The returned value can be null, so Option type is used.
   *
   * @param key
   * @return
   */
  def get(key:String): Any
  def check(key:String): BottomType

  /**
   * create a context summary from dictionary.
   * From the dict (Map of String -> Object), it generates a summary
   * wholeDict is used for complete dictionary
   *
   *
   * @param dict
   */
  def create(dict:Map[String, Any]);

  def load(filePath:String);
  def save(filePath:String);

  // def zip(): Array[Byte];
  def serialize(): Array[Byte];
}
