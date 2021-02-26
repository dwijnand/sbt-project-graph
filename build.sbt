val sbtprojectgraph = project.in(file(".")).settings(name := "sbt-project-graph")

organization := "com.dwijnand"
    licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
 description := "An sbt plugin to help visualise inter-project dependencies"
  developers := List(Developer("dwijnand", "Dale Wijnand", "dale wijnand gmail com", url("https://dwijnand.com")))
   startYear := Some(2015)
    homepage := scmInfo.value map (_.browseUrl)
     scmInfo := Some(ScmInfo(url("https://github.com/dwijnand/sbt-project-graph"), "scm:git:git@github.com:dwijnand/sbt-project-graph.git"))

enablePlugins(SbtPlugin)
Global / sbtVersion  := "1.0.0" // must be Global, otherwise ^^ won't change anything
    crossSbtVersions := List("1.0.0")

scalaVersion := "2.12.13"

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
  (pluginCrossBuild / sbtBinaryVersion).value,
  (update / scalaBinaryVersion).value
)

Test /              fork := false
Test /       logBuffered := false
Test / parallelExecution := true

scriptedLaunchOpts ++= Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
scriptedBufferLog := true

def toSbtPlugin(m: ModuleID) = Def.setting(
  Defaults.sbtPluginExtra(m, (pluginCrossBuild / sbtBinaryVersion).value, (update / scalaBinaryVersion).value)
)

mimaPreviousArtifacts := Set(toSbtPlugin("com.dwijnand" % "sbt-project-graph" % "0.4.0").value)

Global / cancelable := true
