organization := "com.dwijnand.sbtprojectgraph"
        name := "sbt-project-graph"
     version := "0.1.0"
    licenses := Seq(("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")))
   sbtPlugin := true
 description := "An sbt plugin to help visualise inter-project dependencies"

GithubRelease.repo := s"dwijnand/${name.value}"
