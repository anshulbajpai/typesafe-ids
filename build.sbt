import Dependencies._
import com.typesafe.sbt.GitBranchPrompt

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.anshulbajpai",
      name := "typesafe-ids",
      scalaVersion := "2.12.6",
      version := "0.1.0"
    )),
    name := "typesafe-ids",
    libraryDependencies += scalaTest % Test,
    crossScalaVersions := Seq("2.10.7", "2.11.12", scalaVersion.value),
  ).enablePlugins(GitBranchPrompt)
