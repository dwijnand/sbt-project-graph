val sbtprojectgraph = project in file(".")

organization := "com.dwijnand"
        name := "sbt-project-graph"
    licenses := Seq(("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")))
 description := "An sbt plugin to help visualise inter-project dependencies"
  developers := List(Developer("dwijnand", "Dale Wijnand", "dale wijnand gmail com", url("https://dwijnand.com")))
   startYear := Some(2015)
    homepage := scmInfo.value map (_.browseUrl)
     scmInfo := Some(ScmInfo(url("https://github.com/dwijnand/sbt-project-graph"), "scm:git:git@github.com:dwijnand/sbt-project-graph.git"))

enablePlugins(SbtPlugin)
      sbtVersion in Global := "1.0.0" // must be Global, otherwise ^^ won't change anything
crossSbtVersions           := List("1.0.0", "0.13.16")

scalaVersion := (CrossVersion partialVersion (sbtVersion in pluginCrossBuild).value match {
  case Some((0, 13)) => "2.10.6"
  case Some((1, _))  => "2.12.3"
  case _             => sys error s"Unhandled sbt version ${(sbtVersion in pluginCrossBuild).value}"
})

       maxErrors := 15
triggeredMessage := Watched.clearWhenTriggered

scalacOptions ++= Seq("-encoding", "utf8")
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")
scalacOptions  += "-Xfuture"
scalacOptions  += "-Yno-adapted-args"
scalacOptions  += "-Ywarn-dead-code"
scalacOptions  += "-Ywarn-numeric-widen"
scalacOptions  += "-Ywarn-value-discard"

libraryDependencies += Defaults.sbtPluginExtra(
  "com.dwijnand" % "sbt-compat" % "1.2.6",
  (sbtBinaryVersion in pluginCrossBuild).value,
  (scalaBinaryVersion in update).value
)

             fork in Test := false
      logBuffered in Test := false
parallelExecution in Test := true

scriptedLaunchOpts ++= Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
scriptedBufferLog := true

def toSbtPlugin(m: ModuleID) = Def.setting(
  Defaults.sbtPluginExtra(m, (sbtBinaryVersion in pluginCrossBuild).value, (scalaBinaryVersion in update).value)
)

mimaPreviousArtifacts := Set(toSbtPlugin("com.dwijnand" % "sbt-project-graph" % "0.4.0").value)

// TaskKey[Unit]("verify") := Def.sequential(test in Test, scripted.toTask(""), mimaReportBinaryIssues).value

cancelable in Global := true
