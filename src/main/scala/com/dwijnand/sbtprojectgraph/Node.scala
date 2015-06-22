package com.dwijnand.sbtprojectgraph

import sbt.{ ProjectRef, ResolvedProject }

/** A node in a dependency tree of elements of type `A`. */
case class Node[A](value: A, directDeps: Set[Node[A]], allDeps: Set[A], allEdges: Set[(A, A)])

object Node {
  def create(p: ResolvedProject, projects: Map[String, ResolvedProject]): Node[ResolvedProject] = {
    val directDeps0    : Set[ResolvedProject]       = p.uses.toSet map ((p: ProjectRef) => projects(p.project))
    val directDeps     : Set[Node[ResolvedProject]] = directDeps0 map (d => create(d, projects))
    val transDeps      : Set[ResolvedProject]       = directDeps flatMap (_.allDeps)
    val uniqDirectDeps : Set[Node[ResolvedProject]] = directDeps filterNot (d => transDeps(d.value))

    Node(
           value = p,
      directDeps = uniqDirectDeps,
         allDeps = directDeps0 ++ transDeps,
        allEdges = uniqDirectDeps.flatMap(_.allEdges) ++ uniqDirectDeps.map(d => p -> d.value)
    )
  }
}
