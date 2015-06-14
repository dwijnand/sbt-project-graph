package com.dwijnand.sbtprojectgraph

import sbt._

object SbtProjectGraphPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {

  }
  import autoImport._

  override def globalSettings: Seq[Def.Setting[_]] = super.globalSettings

  override def buildSettings: Seq[Def.Setting[_]] = super.buildSettings

  override def projectSettings: Seq[Def.Setting[_]] = super.projectSettings

  override def projectConfigurations: Seq[Configuration] = super.projectConfigurations
}
