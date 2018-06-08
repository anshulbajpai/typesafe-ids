import Dependencies._
import com.typesafe.sbt.GitBranchPrompt
import ReleaseTransformations._
import xerial.sbt.Sonatype._


lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.anshulbajpai",
      scalaVersion := "2.12.6",
      libraryDependencies += scalaTest % Test,
      crossScalaVersions := Seq("2.11.12", scalaVersion.value),
    )),
    Test / test := {},
    publishArtifact  := false,
    releaseProcess := Seq[ReleaseStep](),
  ).enablePlugins(GitBranchPrompt)
  .aggregate(core, playjson)

lazy val core = project
  .settings(
    name := "typesafe-ids",
    commonPublishSettings,
    commonReleaseSettings,
    releaseVersionFile := file(s"${baseDirectory.value}/version.sbt")
  )

lazy val playjson = (project in file("json/play"))
  .settings(
    name := "typesafe-ids-json-play",
    libraryDependencies += playJson,
    commonPublishSettings,
    commonReleaseSettings,
    releaseVersionFile := file(s"${baseDirectory.value}/version.sbt")
  )
  .dependsOn(core)


lazy val commonReleaseSettings = Seq(
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
)

lazy val commonPublishSettings = Seq(
  publishTo  := sonatypePublishTo.value,
  publishMavenStyle := true,
  licenses := Seq("MIT" -> url("https://mit-license.org/license.txt")),
  sonatypeProjectHosting := Some(GitHubHosting("anshulbajpai", "typesafe-ids", "bajpai.anshul@gmail.com"))
)