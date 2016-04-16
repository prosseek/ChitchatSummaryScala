package api

import java.lang.{String => JString}

import chitchat.typefactory.TypeDatabase
import filter.ChitchatFilter
import summary._

/**
  * Created by smcho on 4/16/16.
  */
object API {

  // create group

  def create_json_summary(jsonSummary:JString, Q:Int) = {
    // todo: filter should not be null
    val typeInference = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(typeInference)
    JsonSummary(source = jsonSummary) // set the q even though it's not used, as it's a part of interface
  }

  def create_compresssed_json_summary(jsonSummary:JString, Q:Int) = {
    // todo: filter should not be null
    CompressedJsonSummary(source = jsonSummary) // set the q even though it's not used, as it's a part of interface
  }

  def create_fbf_summary(jsonSummary:JString, Q:Int) = {
    // todo: filter should not be null
    val typeInference = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(typeInference)
    FBFSummary(q = Q*8, source = jsonSummary, filter = filter)
  }

  def create_cbf_summary(jsonSummary:JString, Q:Int) = {
    // todo: filter should not be null
    val typeInference = TypeDatabase() // () to use apply
    val filter = ChitchatFilter(typeInference)
    CBFSummary(q = Q*8, source = jsonSummary, filter = filter) // set the q even though it's not used, as it's a part of interface
  }

  def create_complete_summary(jsonSummary:JString, Q:Int) = {
    // todo: filter should not be null
    CompleteSummary(source = jsonSummary) // set the q even though it's not used, as it's a part of interface
  }

  // serialize group
  def serialize(summary:Summary) = {
    summary.serialize
  }

  // I/O grouop
  def save(summary:Summary, filePath:JString) = {
    summary.save(filePath = filePath)
  }

}
