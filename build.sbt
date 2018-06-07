import Dependencies._
import com.typesafe.sbt.GitBranchPrompt
import ReleaseTransformations._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.anshulbajpai",
      scalaVersion := "2.12.6",
      libraryDependencies += scalaTest % Test,
      crossScalaVersions := Seq("2.11.12", scalaVersion.value),
      releaseCrossBuild := true,
      releaseUseGlobalVersion := false,
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
  .aggregate(core, playjson)

lazy val core = project
  .settings(
    name := "typesafe-ids",
    releaseVersionFile := file(s"${baseDirectory.value}/version.sbt")
  )

lazy val playjson = (project in file("json/play"))
  .settings(
    name := "typesafe-ids-json-play",
    libraryDependencies += playJson,
    releaseVersionFile := file(s"${baseDirectory.value}/version.sbt")
  )
  .dependsOn(core)
