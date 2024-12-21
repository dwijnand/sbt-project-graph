package sbtprojectgraph

import sbt._, Keys._
import sbt.internal.{ BuildStructure, LoadedBuildUnit } // sbt/sbt#3296

object SbtProjectGraphPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override def buildSettings: Seq[Setting[?]] = Seq(
    commands ++= Seq(
      projectsGraphDot,
      projectsGraphSvg,
      projectsGraphPng
    )
  )

  val projectsGraphDot = Command.command("projectsGraphDot") { s =>
    val (_, state) = executeProjectsGraphDot(s)
    state
  }

  val projectsGraphSvg = Command.command("projectsGraphSvg")(dotTo("svg"))
  val projectsGraphPng = Command.command("projectsGraphPng")(dotTo("png"))

  private[this] def dotTo(outputFormat: String)(s: State) = {
    val (dotFile, state) = executeProjectsGraphDot(s)
    val extracted = Project extract state
    val outFile = extracted.get(target) / s"projects-graph.$outputFormat"
    val command = Seq("dot", "-o" + outFile.getAbsolutePath, s"-T$outputFormat", dotFile.getAbsolutePath)
    sys.process.Process(command).!
    extracted get sLog info s"Wrote project graph to '$outFile'"
    state
  }

  private[this] def executeProjectsGraphDot(s: State): (File, State) = {
    val extracted: Extracted = Project extract s

    val currentBuildUri: URI = extracted.currentRef.build

    val buildStructure:   BuildStructure            = extracted.structure
    val buildUnitsMap:    Map[URI, LoadedBuildUnit] = buildStructure.units
    val currentBuildUnit: LoadedBuildUnit           = buildUnitsMap(currentBuildUri)

    val projectsMap: Map[String, ResolvedProject] = currentBuildUnit.defined

    val projects: Seq[ResolvedProject] = projectsMap.values.toVector

    val projectsNodes: Seq[Node[ResolvedProject]] = projects map (p => Node.create(p, projectsMap))

    val edges: Seq[Edge[ResolvedProject]] = projectsNodes.flatMap(_.allEdges).distinct

    val projectsGraphDotFile = extracted.get(target) / "projects-graph.dot"

    IO.write(projectsGraphDotFile, Dot.toFileContent(projects, edges))

    extracted get sLog info s"Wrote project graph to '$projectsGraphDotFile'"

    (projectsGraphDotFile, s)
  }
}
