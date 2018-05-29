package sbtprojectgraph

import sbt.ResolvedProject

/** Utility for [[https://en.wikipedia.org/wiki/DOT_(graph_description_language) DOT]]. */
object Dot {
  private def edgeStyle(edge: Edge[_]): String = edge.kind match {
    case EdgeKind.Solid => "[style=solid]"
    case EdgeKind.Dashed => "[style=dashed]"
  }

  def toFileContent(nodes: Seq[ResolvedProject], edges: Seq[Edge[ResolvedProject]]): String = {
    val nodesContent = nodes.map(n => s"""    "${n.id}"[label=<${n.id}>]""").sorted.mkString("\n")
    val edgesContent = edges.map(e => s"""    "${e.from.id}" -> "${e.to.id}" ${edgeStyle(e)}""").sorted.mkString("\n")
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
