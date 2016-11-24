package sbtprojectgraph

import sbt.ResolvedProject

/** Utility for [[https://en.wikipedia.org/wiki/DOT_(graph_description_language) DOT]]. */
object Dot {
  def toFileContent(nodes: Seq[ResolvedProject], edges: Seq[(ResolvedProject, ResolvedProject)]): String = {
    val nodesContent = nodes map (n => s"""    "${n.id}"[label=<${n.id}>]""") mkString "\n"
    val edgesContent = edges map (e => s"""    "${e._1.id}" -> "${e._2.id}"""") mkString "\n"
    val content =
      s"""
         |digraph "projects-graph" {
         |    graph[rankdir="LR"]
         |    node [
         |        shape="record"
         |    ]
         |    edge [
         |        arrowtail="none"
         |    ]
         |$nodesContent
         |$edgesContent
         |}
       """.stripMargin.trim
    content
  }
}
