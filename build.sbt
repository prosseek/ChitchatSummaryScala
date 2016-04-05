name := "chitchatsummary"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
target in Compile in doc := baseDirectory.value / "doc/api"

libraryDependencies += "chitchattype" %% "chitchattype" % "0.1"
libraryDependencies += "bloomierfilter" %% "bloomierfilter" % "0.1"