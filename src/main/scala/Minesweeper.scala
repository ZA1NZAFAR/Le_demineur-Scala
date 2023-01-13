class Minesweeper() {
  var grid: Seq[Seq[Case]] = Seq()

  def this(w: Int, h: Int, n: Int) = {
    this()
    grid = Utilitaire.init_game(w, h, n)
  }

  // Plus cohérent de placer display du grid ici ? Dans l'énoncé : indiquer d'utiliser celui dans Mineweeper
  def display(): Unit = {
    val (numRows, numCols) = Utilitaire.get_dimension(grid)
    for {
      i <- 0 until numRows
      j <- 0 until numCols
    } {
      val cell = grid(i)(j)
      print(" ")
      if (cell.isFlagged) {
        print("F ")
      } else if (cell.isRevealed) {
        if (cell.isMine) {
          print("X ")
        } else {
          print(s"${cell.value} ")
        }
      } else {
        print("| ")
      }

      if (j == numCols - 1) {
        println()
      }
    }
  }

  // Allows to reveal a case and to reveal all the cases around it if the value of the case is 0 (no mines around it)
  def reveal_case(x: Int, y: Int): Boolean = {
    val caseToReveal = grid(x)(y)
    if (caseToReveal.isRevealed) {
      println("Case is already revealed!")
      return false
    }
    if (caseToReveal.isMine) {
      // Si la case révélée contient une mine, la partie est perdue
      caseToReveal.isRevealed = true
      true
    } else if (Utilitaire.won(grid)) {
      // Si toutes les cases sans mine ont été révélées, la partie est gagnée
      true
    } else {
      // Sinon, la partie continue
      if (caseToReveal.value == 0)
        revealNeighbors(grid, x, y)
      else
        caseToReveal.isRevealed = true
      false
    }
  }
  // Allows to reveal all the cases around a given case recursively
  def revealNeighbors(grid: Seq[Seq[Case]], x: Int, y: Int): Unit = {
    if (x < 0 || y < 0 || x >= grid.length || y >= grid.head.length) return // Check if the coordinates are within the grid bounds
    val currentCase = grid(x)(y)
    if (currentCase.isRevealed) return // check if the current case is already revealed or not equal to zero
    if (!currentCase.isMine && currentCase.value > 0) {
      currentCase.isRevealed = true
      return
    }

    currentCase.isRevealed = true // reveal the current case
    if (currentCase.value == 0) {
      revealNeighbors(grid, x - 1, y) // reveal the case on the left
      revealNeighbors(grid, x + 1, y) // reveal the case on the right
      revealNeighbors(grid, x, y - 1) // reveal the case above
      revealNeighbors(grid, x, y + 1) // reveal the case below
    }
  }


}
