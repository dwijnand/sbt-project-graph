val sbtprojectgraph = project.in(file(".")).settings(name := "sbt-project-graph")

organization := "com.dwijnand"
    licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
 description := "An sbt plugin to help visualise inter-project dependencies"
  developers := List(Developer("dwijnand", "Dale Wijnand", "dale wijnand gmail com", url("https://dwijnand.com")))
   startYear := Some(2015)
    homepage := scmInfo.value map (_.browseUrl)
     scmInfo := Some(ScmInfo(url("https://github.com/dwijnand/sbt-project-graph"), "scm:git:git@github.com:dwijnand/sbt-project-graph.git"))

enablePlugins(SbtPlugin)
Global / sbtVersion  := {  // must be Global, otherwise ^^ won't change anything
  if (sys.env.isDefinedAt("GITHUB_ACTIONS") && scala.util.Properties.isJavaAtLeast("21")){
    (Global / sbtVersion).value
  } else {
    "1.0.0"
  }
}

pluginCrossBuild / sbtVersion := {
  scalaBinaryVersion.value match {
    case "2.12" =>
      (pluginCrossBuild / sbtVersion).value
    case _ =>
      "2.0.0-RC6"
  }
}

scalaVersion := "2.13.17"

crossScalaVersions += "3.7.3"

maxErrors := 15

scalacOptions ++= Seq("-encoding", "utf8")
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")
scalacOptions ++= {
  scalaBinaryVersion.value match {
    case "2.12" =>
      Seq(
        "-Xsource:3",
        "-Xfuture",
        "-Yno-adapted-args",
        "-Ywarn-dead-code",
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard"
      )
    case _ =>
      Nil
  }
}

Test /              fork := false
Test /       logBuffered := false
Test / parallelExecution := true

scriptedLaunchOpts ++= Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
scriptedBufferLog := true

def toSbtPlugin(m: ModuleID) = Def.setting(
  Defaults.sbtPluginExtra(m, (pluginCrossBuild / sbtBinaryVersion).value, (update / scalaBinaryVersion).value)
)

mimaPreviousArtifacts := Set(toSbtPlugin("com.dwijnand" % "sbt-project-graph" % "0.4.0").value)

Global / cancelable := true
