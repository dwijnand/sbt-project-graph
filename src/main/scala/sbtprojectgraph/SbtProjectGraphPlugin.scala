package sbtprojectgraph

import sbt._, Keys._
import sbt.internal.{ BuildStructure, LoadedBuildUnit } // sbt/sbt#3296

object SbtProjectGraphPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    lazy val projectsGraphIncludeTransitiveEdges = settingKey[Boolean](
      "Should the dependency graph include transitive edges, default: false."
    )
  }

  import autoImport._

  override def buildSettings: Seq[Setting[_]] = Seq(
    commands ++= Seq(
      projectsGraphDot,
      projectsGraphSvg,
      projectsGraphPng
    )
  )

  override def globalSettings: Seq[Def.Setting[_]] =
    super.globalSettings ++ Seq(projectsGraphIncludeTransitiveEdges := false)

  val projectsGraphDot = Command.command("projectsGraphDot") { s =>
    projectsGraphIncludeTransitiveEdges.map(executeProjectsGraphDot(s, _))
    s
  }

  val projectsGraphSvg = Command.command("projectsGraphSvg") { s =>
    projectsGraphIncludeTransitiveEdges.map(dotTo("svg", _)(s))
    s
  }

  val projectsGraphPng = Command.command("projectsGraphPng") { s =>
    projectsGraphIncludeTransitiveEdges.map(dotTo("png", _)(s))
    s
  }

  private[this] def dotTo(outputFormat: String, includeTransitiveEdges: Boolean)(s: State) = {
    val dotFile = executeProjectsGraphDot(s, includeTransitiveEdges)
    val extracted = Project extract s
    val outFile = extracted.get(target) / s"projects-graph.$outputFormat"
    val command = Seq("dot", "-o" + outFile.getAbsolutePath, s"-T$outputFormat", dotFile.getAbsolutePath)
    sys.process.Process(command).!
    extracted get sLog info s"Wrote project graph to '$outFile'"
    s
  }

  private[this] def executeProjectsGraphDot(s: State, includeTransitiveEdges: Boolean): File = {
    val extracted: Extracted = Project extract s

    val currentBuildUri: URI = extracted.currentRef.build

    val buildStructure:   BuildStructure            = extracted.structure
    val buildUnitsMap:    Map[URI, LoadedBuildUnit] = buildStructure.units
    val currentBuildUnit: LoadedBuildUnit           = buildUnitsMap(currentBuildUri)

    val projectsMap: Map[String, ResolvedProject] = currentBuildUnit.defined

    val projects: Seq[ResolvedProject] = projectsMap.values.toVector

    val projectsNodes: Seq[Node[ResolvedProject]] = projects map (p => Node.create(p, projectsMap, includeTransitiveEdges))

    val edges: Seq[Edge[ResolvedProject]] = projectsNodes.flatMap(_.allEdges).distinct

    val projectsGraphDotFile = extracted.get(target) / "projects-graph.dot"

    IO.write(projectsGraphDotFile, Dot.toFileContent(projects, edges))

    extracted get sLog info s"Wrote project graph to '$projectsGraphDotFile'"

    projectsGraphDotFile
  }
}
