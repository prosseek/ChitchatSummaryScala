name := "chitchatsummary"

version := "0.1"

scalaVersion := "2.11.8"

// http://stackoverflow.com/questions/26691750/using-util-parsing-in-scala-2-11
// libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
target in Compile in doc := baseDirectory.value / "doc/api"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
  "chitchattype" %% "chitchattype" % "0.1",
  "bloomierfilter" %% "bloomierfilter" % "0.1"
  //"chitchatvm" %% "chitchatvm" % "0.1"
)

//libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2"
//libraryDependencies += "chitchattype" %% "chitchattype" % "0.1"
//libraryDependencies += "bloomierfilter" %% "bloomierfilter" % "0.1"

// META-INF discarding
//mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
//  {
//    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
//    case x => MergeStrategy.first
//  }
//}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
