name := "21s"

version := "0.1"

scalaVersion := "2.13.1"

scalacOptions ++= Seq(
	"-Wdead-code",
	"-Xlint:unused",
	"-Xlint:deprecation"
)

lazy val testDependencies = Seq(
	"org.scalactic" %% "scalactic" % "3.1.1" % Test,
	"org.scalatest" %% "scalatest" % "3.1.1" % Test,
	"org.scalacheck" %% "scalacheck" % "1.14.1" % Test,
	"org.scalatestplus" %% "scalacheck-1-14" % "3.1.1.1" % Test,
	"com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.3" % Test
)

lazy val shapeless = Seq("com.chuusai" %% "shapeless" % "2.3.3")

lazy val cats = Seq(
	"org.typelevel" %% "cats-effect" % "2.1.2",
	"org.typelevel" %% "cats-core" % "2.0.0"
)

libraryDependencies ++=
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
  ) ++ testDependencies ++ cats ++ shapeless