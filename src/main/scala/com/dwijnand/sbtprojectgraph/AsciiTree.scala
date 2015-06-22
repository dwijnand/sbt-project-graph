package com.dwijnand.sbtprojectgraph

object AsciiTree {
  def draw[A](top: A, children: A => Seq[A], display: A => String, maxColumn: Int = defaultColumnSize) = {
    def limitLine(s: String) = if (s.length > maxColumn) s.slice(0, maxColumn - 2) + ".." else s
    def insertBar(s: String, at: Int): String =
      if (at < s.length) {
        val sep = s(at).toString match { case " " => "|" ; case x => x }
        s.slice(0, at) + sep + s.slice(at + 1, s.length)
      }
      else s
    def toAsciiLines(node: A, level: Int): Vector[String] = {
      val line = limitLine(("  " * level) + (if (level == 0) "" else "+-") + display(node))
      val cs = children(node).toVector
      val childLines = cs map (toAsciiLines(_, level + 1))
      val withBar = childLines.zipWithIndex flatMap {
        case (lines, pos) if pos < (cs.size - 1) => lines map (insertBar(_, 2 * (level + 1)))
        case (lines, _) if lines.last.trim != "" => lines ++ Vector("  " * (level + 1))
        case (lines, _)                          => lines
      }
      line +: withBar
    }

    toAsciiLines(top, 0) mkString "\n"
  }

  def defaultColumnSize: Int = {
    val termWidth = jline.TerminalFactory.get().getWidth
    if (termWidth > 20) termWidth - 8 else 80
  }
}
