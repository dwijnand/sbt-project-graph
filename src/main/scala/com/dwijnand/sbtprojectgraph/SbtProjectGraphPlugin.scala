package com.dwijnand.sbtprojectgraph

import sbt._
import sbt.Keys._

object SbtProjectGraphPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override def buildSettings = Seq[Setting[_]](commands += projectsGraphDot)

  val projectsGraphDot = Command.command("projectsGraphDot") { s =>
    val extracted: Extracted = Project extract s

    val currentBuildUri  : URI        = extracted.currentRef.build
    val currentProjectId : String     = extracted.currentRef.project

    val buildStructure          : BuildStructure            = extracted.structure
    val projectUriToBuildUnits  : Map[URI, LoadedBuildUnit] = buildStructure.units
    val currentProjectBuildUnit : LoadedBuildUnit           = projectUriToBuildUnits(currentBuildUri)

    val projectsLookup: Map[String, ResolvedProject] = currentProjectBuildUnit.defined

    val rootProject: ResolvedProject = projectsLookup(currentProjectId)

    val projectsSeq: Seq[ResolvedProject] =
      rootProject +: (projectsLookup.values filterNot rootProject.==).toVector

    val projectsNodes: Seq[Node[ResolvedProject]] = projectsSeq map (p => Node.create(p, projectsLookup))

    val edges: Seq[(ResolvedProject, ResolvedProject)] = projectsNodes.flatMap(_.allEdges).distinct

    val projectsGraphDotFile = extracted.get(target) / "projects-graph.dot"

    IO.write(projectsGraphDotFile, Dot.toFileContent(projectsSeq, edges))

    extracted get sLog info s"Wrote project graph to '$projectsGraphDotFile'"

    s
  }
}
