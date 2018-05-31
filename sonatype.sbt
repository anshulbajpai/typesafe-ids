publishMavenStyle := true

licenses := Seq("MIT" -> url("https://mit-license.org/license.txt"))

import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("anshulbajpai", "typesafe-ids", "bajpai.anshul@gmail.com"))