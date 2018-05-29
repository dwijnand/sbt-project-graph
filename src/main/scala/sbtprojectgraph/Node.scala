package sbtprojectgraph

import sbt.{ ProjectRef, ResolvedProject }

/** A node in a dependency tree of elements of type `A`. */
final case class Node[A](value: A, directDeps: Set[Dependency[Node[A]]], allDeps: Set[Dependency[A]], allEdges: Set[Edge[A]])

object Node {
  def create(p: ResolvedProject, projects: Map[String, ResolvedProject]): Node[ResolvedProject] = {
    val aggregates = p.aggregate.toSet[ProjectRef].flatMap(ref => projects.get(ref.project).map(Dependency.fromAggregate))
    val classpathDeps = p.dependencies.flatMap(dep => projects.get(dep.project.project).map(Dependency.fromDependsOn))

    val directDeps0:    Set[Dependency[ResolvedProject]]       =  aggregates ++ classpathDeps
    val directDeps:     Set[Dependency[Node[ResolvedProject]]] = directDeps0 map (d => Dependency(create(d.target, projects), d.kind))
    val transDeps:      Set[Dependency[ResolvedProject]]       = directDeps flatMap (_.target.allDeps)
    val uniqDirectDeps: Set[Dependency[Node[ResolvedProject]]] = directDeps filterNot (d => transDeps(d.map(_.value)))

    Node(
           value = p,
      directDeps = uniqDirectDeps,
         allDeps = directDeps0 ++ transDeps,
        allEdges = uniqDirectDeps.flatMap(_.target.allEdges) ++ uniqDirectDeps.map(d => Edge.fromDependency(p, d.map(_.value)))
    )
  }
}
