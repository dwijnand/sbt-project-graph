// For sbt-github-release
resolvers += "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com"

addSbtPlugin("me.lessis"     % "bintray-sbt"        % "0.3.0")
addSbtPlugin("ohnosequences" % "sbt-github-release" % "0.3.0")
