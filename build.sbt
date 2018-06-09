import Dependencies._
import com.typesafe.sbt.GitBranchPrompt
import ReleaseTransformations._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.anshulbajpai",
      name := "typesafe-ids",
      scalaVersion := "2.12.6"
    )),
    libraryDependencies += scalaTest % Test,
    crossScalaVersions := Seq("2.10.7", "2.11.12", scalaVersion.value),
    publishTo  := sonatypePublishTo.value,
    releaseCrossBuild := true,
    releaseVersionBump := sbtrelease.Version.Bump.Minor,
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
  ).enablePlugins(GitBranchPrompt)