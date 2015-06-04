`show projectDependencies` shows root and aggregated projects

`BuildStructure.units` contains all projects

`projects` does:

```scala
val s: State

val extracted: Extracted = Project extract s

val structure  : BuildStructure = extracted.structure
val currentRef : ProjectRef     = extracted.currentRef // current project ref

val curi    : URI    = currentRef.build   // project URI
val project : String = currentRef.project // project id

val units                       : Map[URI, LoadedBuildUnit]    = structure.units
val build                       : LoadedBuildUnit              = units(curi)
val projectsIdToResolvedProject : Map[String, ResolvedProject] = build.defined
```
