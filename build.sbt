name := "dispatch-tagsoup"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "net.databinder" % "dispatch-core_2.9.1" % "0.8.5",
  "net.databinder" % "dispatch-http_2.9.1" % "0.8.5",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  "org.jsoup" % "jsoup" % "1.6.1",
  "org.scala-tools.testing" % "specs_2.9.1" % "1.6.9" % "test->default",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)