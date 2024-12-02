package sbtprojectgraph

sealed trait DependencyKind

object DependencyKind {
  case object DependsOn extends DependencyKind
  case object Aggregate extends DependencyKind
}

final case class Dependency[A](target: A, kind: DependencyKind, configuration: Option[String]) {
  def map[B](f: A => B): Dependency[B] = Dependency(f(target), kind, configuration)
}

object Dependency {
  def fromAggregate[A](target: A): Dependency[A] = Dependency(target, DependencyKind.Aggregate, None)
  def fromDependsOn[A](target: A, configuration: Option[String]): Dependency[A] = Dependency(target, DependencyKind.DependsOn, configuration)
}
