# sbt-project-graph

  [sbt-project-graph]: https://github.com/dwijnand/sbt-project-graph
  [sbt-dependency-graph]: https://github.com/jrudolph/sbt-dependency-graph

[sbt-project-graph] is an sbt plugin to helps visualise inter-project dependencies.

Inspired by [sbt-dependency-graph].

## Setup

  [Global plugins]: http://www.scala-sbt.org/0.13/tutorial/Using-Plugins.html#Global+plugins

Much like `sbt-dependency-graph`, `sbt-project-graph` is an informational plugin and isn't ever a requirement of
any build.

As such it's convenient to install `sbt-project-graph` as a [global plugin][Global plugins] so that no project's
setup needs to be modified (and `sbt` reloaded) in order to use `sbt-project-graph`.

Therefore, add the dependency to `~/.sbt/0.13/plugins/plugins.sbt` (create one if necessary):

    addSbtPlugin("com.dwijnand.sbtprojectgraph" % "sbt-project-graph" % "0.1.0")

As `sbt-project-graph` is an auto plugin that is all that is required.

## Tasks

  [DOT]: https://en.wikipedia.org/wiki/DOT_(graph_description_language)

* `projectsGraphDot`: Generates a [DOT] file (`target/projects-graph.dot`) with the projects graph.

## Licence

Copyright 2015 Dale Wijnand

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
