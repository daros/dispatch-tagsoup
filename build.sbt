name := "dispatch-tagsoup"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "net.databinder" %% "dispatch-http" % "0.8.6",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)