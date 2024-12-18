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

pluginCrossBuild / sbtVersion := {
  scalaBinaryVersion.value match {
    case "2.12" =>
      (pluginCrossBuild / sbtVersion).value
    case _ =>
      "2.0.0-M3"
  }
}

scalaVersion := "2.12.20"

crossScalaVersions += "3.6.2"

maxErrors := 15

scalacOptions ++= Seq("-encoding", "utf8")
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlint")
scalacOptions  += "-Xfuture"
scalacOptions  += "-Yno-adapted-args"
scalacOptions  += "-Ywarn-dead-code"
scalacOptions  += "-Ywarn-numeric-widen"
scalacOptions  += "-Ywarn-value-discard"
scalacOptions ++= {
  scalaBinaryVersion.value match {
    case "2.12" =>
      Seq("-Xsource:3")
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

TaskKey[Unit]("scriptedTestSbt2") := Def.taskDyn {
  val values = sbtTestDirectory.value
    .listFiles(_.isDirectory)
    .flatMap { dir1 =>
      dir1.listFiles(_.isDirectory).map { dir2 =>
        dir1.getName -> dir2.getName
      }
    }
    .toList
  // TODO
  // https://github.com/dwijnand/sbt-project-graph/issues/145
  // https://github.com/sbt/sbt/issues/7738
  val exclude: Set[(String, String)] = Set(
    "regr" -> "gh019",
  )
  val args = values.filterNot(exclude).map { case (x1, x2) => s"${x1}/${x2}" }
  val arg = args.mkString(" ", " ", "")
  streams.value.log.info("scripted" + arg)
  scripted.toTask(arg)
}.value
