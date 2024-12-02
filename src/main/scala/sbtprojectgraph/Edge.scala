package sbtprojectgraph

sealed trait EdgeKind

object EdgeKind {
  case object Solid extends EdgeKind
  case object Dashed extends EdgeKind
}

final case class Edge[A](from: A, to: A, kind: EdgeKind, extra: Option[String])

object Edge {
  def fromDependency[A](from: A, to: Dependency[A]): Edge[A] = {
    val kind = to.kind match {
      case DependencyKind.DependsOn => EdgeKind.Solid
      case DependencyKind.Aggregate => EdgeKind.Dashed
    }
    Edge(from = from, to = to.target, kind = kind, extra = to.configuration)
  }
}
