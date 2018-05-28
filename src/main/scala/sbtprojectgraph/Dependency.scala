package sbtprojectgraph

sealed trait DependencyKind

object DependencyKind {
  case object DependsOn extends DependencyKind
  case object Aggregate extends DependencyKind
}

final case class Dependency[A](target: A, kind: DependencyKind) {
  def map[B](f: A => B): Dependency[B] = Dependency(f(target), kind)
}

object Dependency {
  def fromAggregate[A](target: A) = Dependency(target, DependencyKind.Aggregate)
  def fromDependsOn[A](target: A) = Dependency(target, DependencyKind.DependsOn)
}
