sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("com.dwijnand" % "sbt-project-graph" % x)
  case _       => sys.error("""|The system property 'plugin.version' is not defined.
                               |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}

libraryDependencies += "com.googlecode.java-diff-utils" % "diffutils" % "1.3.0"
