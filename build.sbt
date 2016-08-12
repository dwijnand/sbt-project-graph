lazy val `sbt-project-graph` = project in file(".")

organization := "com.dwijnand.sbtprojectgraph"
     version := "0.1.1-SNAPSHOT"
    licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
   startYear := Some(2015)
   sbtPlugin := true
 description := "An sbt plugin to help visualise inter-project dependencies"

       maxErrors := 5
triggeredMessage := Watched.clearWhenTriggered

scalacOptions ++= Seq("-encoding", "utf8")
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")
scalacOptions  += "-language:higherKinds"
scalacOptions  += "-language:implicitConversions"
scalacOptions  += "-language:postfixOps"
scalacOptions  += "-Xfuture"
scalacOptions  += "-Yinline-warnings"
scalacOptions  += "-Yno-adapted-args"
scalacOptions  += "-Ywarn-dead-code"
scalacOptions  += "-Ywarn-numeric-widen"
scalacOptions  += "-Ywarn-value-discard"

wartremoverWarnings += Wart.Any                     // bans f-interpolator #158
wartremoverWarnings += Wart.Any2StringAdd
wartremoverWarnings += Wart.AsInstanceOf
wartremoverWarnings += Wart.EitherProjectionPartial
wartremoverWarnings += Wart.FinalCaseClass
wartremoverWarnings += Wart.IsInstanceOf
wartremoverWarnings += Wart.ListOps
wartremoverWarnings += Wart.JavaConversions
wartremoverWarnings += Wart.MutableDataStructures
wartremoverWarnings += Wart.NonUnitStatements       // bans this.type #118
wartremoverWarnings += Wart.Null
wartremoverWarnings += Wart.OptionPartial
wartremoverWarnings += Wart.Return
wartremoverWarnings += Wart.TryPartial
wartremoverWarnings += Wart.Var

initialCommands in console += "\nimport com.dwijnand.sbtprojectgraph._"

         fork in run := true
cancelable in Global := true

GithubRelease.repo := s"dwijnand/${name.value}"

val createGithubRelease =
  Def setting
    ReleaseStep(
      check  = releaseStepTaskAggregated(checkGithubCredentials in thisProjectRef.value),
      action = releaseStepTaskAggregated(       releaseOnGithub in thisProjectRef.value)
    )

releaseProcess := {
  import ReleaseTransformations._

  Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    publishArtifacts,
    setNextVersion,
    commitNextVersion,
    pushChanges,
    createGithubRelease.value
  )
}

watchSources ++= (baseDirectory.value * "*.sbt").get
watchSources ++= (baseDirectory.value / "project" * "*.scala").get
