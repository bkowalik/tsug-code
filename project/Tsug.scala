import sbt._
import Keys._

object Tsug extends Build {

	lazy val dependencies = Seq(
		"com.chuusai" %% "shapeless" % "2.2.5",
		"org.scalatest" %% "scalatest" % "2.2.5" % "test"
	)

	lazy val tsug = Project("tsug", file("."))
		.settings(
			scalaVersion := "2.11.7",
			libraryDependencies := dependencies,
			resolvers += Resolver.sonatypeRepo("releases"),
			initialCommands in console :=
  """
    |  import shapeless._
    |  import test._
  """.stripMargin
		)
}