# [sbt-project-graph][] [![travis-badge][]][travis] [![gitter-badge][]][gitter]

[sbt-project-graph]:  https://github.com/dwijnand/sbt-project-graph
[travis]:          https://travis-ci.org/dwijnand/sbt-project-graph
[travis-badge]:    https://travis-ci.org/dwijnand/sbt-project-graph.svg?branch=master
[gitter]:              https://gitter.im/dwijnand/sbt-project-graph
[gitter-badge]: https://badges.gitter.im/dwijnand/sbt-project-graph.svg

`sbt-project-graph` is an [sbt](http://www.scala-sbt.org/) plugin to help visualise inter-project dependencies.

Inspired by [sbt-dependency-graph](https://github.com/jrudolph/sbt-dependency-graph).

## Setup

  [Global plugins]: https://www.scala-sbt.org/1.x/docs/Using-Plugins.html#Global+plugins

`sbt-project-graph` is an informational plugin and isn't ever a requirement of any build.

Therefore it is recommended to install it as a [global plugin][Global plugins] so that it's always available.

To do so, add it to `~/.sbt/0.13/plugins/plugins.sbt` or `~/.sbt/1.0/plugins/plugins.sbt` (or both; create the
files if necessary) as such:

    addSbtPlugin("com.dwijnand" % "sbt-project-graph" % "0.4.0")

As `sbt-project-graph` is an auto plugin that is all that is required.

## Tasks

  [DOT]: https://en.wikipedia.org/wiki/DOT_(graph_description_language)
  [graphviz]: https://www.graphviz.org

* `projectsGraphDot`: Creates `target/projects-graph.dot` with the project graph. See [DOT][].
* `projectsGraphSvg`: Creates `target/projects-graph.svg` with the project graph. Requires [graphviz][].
* `projectsGraphPng`: Creates `target/projects-graph.png` with the project graph. Requires [graphviz][].

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
