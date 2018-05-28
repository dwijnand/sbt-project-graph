package sbtprojectgraph

import sbt._, Keys._
import sbt.internal.{ BuildStructure, LoadedBuildUnit } // sbt/sbt#3296

object SbtProjectGraphPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override def buildSettings: Seq[Setting[_]] = Seq(commands += projectsGraphDot)

  val projectsGraphDot = Command.command("projectsGraphDot") { s =>
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

    s
  }
}
