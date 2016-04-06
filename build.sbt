name := "chitchatsummary"

version := "0.1"

scalaVersion := "2.11.8"

// http://stackoverflow.com/questions/26691750/using-util-parsing-in-scala-2-11
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
target in Compile in doc := baseDirectory.value / "doc/api"


libraryDependencies += "chitchattype" %% "chitchattype" % "0.1"
libraryDependencies += "bloomierfilter" %% "bloomierfilter" % "0.1"