
ThisBuild / version := "0.1.0-SNAPSHOT"

val _scalaVersion = "3.7.1"

name := "binarytree"
licenses := Seq("GPL 3.0" -> url("https://www.gnu.org/licenses/gpl-3.0.en.html"))
ThisBuild / scalaVersion := _scalaVersion
ThisBuild / versionScheme := Some("semver-spec")
Test / scalaVersion := _scalaVersion

val standardOptions = Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-encoding",
  "utf8"
)

val scala3Options = Seq(
  "-explain",
  "-Wsafe-init",
  "-Ycheck-all-patmat",
  "-Wunused:imports"
)

developers := List(
  Developer(
    "makingthematrix",
    "Maciej Gorywoda",
    "makingthematrix@protonmail.com",
    url("https://github.com/makingthematrix"))
)


lazy val root = (project in file("."))
  .settings(
    name := "binarytree",
    libraryDependencies ++= Seq(
      //Test dependencies
      "org.scalameta" %% "munit" % "1.1.1" % "test"
    ),
    scalacOptions ++= standardOptions ++ scala3Options
  )

testFrameworks += new TestFramework("munit.Framework")