import Dependencies._
import com.typesafe.sbt.GitBranchPrompt
import ReleaseTransformations._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.anshulbajpai",
      scalaVersion := "2.12.6",
      libraryDependencies += scalaTest % Test,
      crossScalaVersions := Seq("2.10.7", "2.11.12", scalaVersion.value),
      releaseCrossBuild := true,
      releaseProcess := Seq[ReleaseStep](
        checkSnapshotDependencies,
        inquireVersions,
        runClean,
        runTest,
        setReleaseVersion,
        commitReleaseVersion,
        tagRelease,
        releaseStepCommandAndRemaining("+publishSigned"),
        setNextVersion,
        commitNextVersion,
        releaseStepCommand("sonatypeReleaseAll"),
        pushChanges
      )
    )),
    ThisBuild / publishTo  := sonatypePublishTo.value,
    Test / test := {},
    publishArtifact  := false,
  ).enablePlugins(GitBranchPrompt)
  .aggregate(core)

lazy val core = project
  .settings(
    name := "typesafe-ids",
    libraryDependencies += playJson
  )
