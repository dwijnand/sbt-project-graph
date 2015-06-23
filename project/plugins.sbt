// For sbt-github-release
resolvers += "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com"

addSbtPlugin("org.brianmckenna"  % "sbt-wartremover"    % "0.13")
addSbtPlugin("me.lessis"         % "bintray-sbt"        % "0.3.0")
addSbtPlugin("ohnosequences"     % "sbt-github-release" % "0.3.0")
addSbtPlugin("com.github.gseitz" % "sbt-release"        % "1.0.0")
