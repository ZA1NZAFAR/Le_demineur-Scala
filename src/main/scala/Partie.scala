import scala.util.Random

object Partie {
  val functions = new Functions // Création d'une instance de la classe Functions

  def incr_tab(m: Seq[Seq[Case]], coordinates: (Int, Int)): Unit = {
    val (x, y) = coordinates
    if (!m(x)(y).isMine) m(x)(y).incrementValue()
  }

  // utiliser celui défini dans "Functions"
  def random_mine(m: Seq[Seq[Case]]): Unit = {
    val (numRows, numCols) = functions.get_dimension(m) // La fonction get_dimension se trouve dans l'objet "Partie"
    val emptySpots = for {
      i <- 0 until numRows
      j <- 0 until numCols
      if !m(i)(j).isMine
    } yield (i, j)
    if (emptySpots.nonEmpty) {
      val (x, y) = emptySpots(Random.nextInt(emptySpots.length))
      m(x)(y).setMine()
      for {
        i <- x - 1 to x + 1
        j <- y - 1 to y + 1
        if functions.is_inside(m, (i, j)) // La fonction is_inside se trouve également dans l'objet "Partie"
      } incr_tab(m, (i, j)) // La méthode incr_tab se trouve également dans l'objet "Partie"
    }
  }

  // Plus cohérent de placer display du grid ici ? Dans l'énoncé : indiquer d'utiliser celui dans Mineweeper
  def display_grid(m: Seq[Seq[Case]]): Unit = {
    val (numRows, numCols) = functions.get_dimension(m)
    for {
      i <- 0 until numRows
      j <- 0 until numCols
    } {
      val cell = m(i)(j)
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


  def reveal_case(grid: Seq[Seq[Case]], x: Int, y: Int): Boolean = {
    val caseToReveal = grid(x)(y)
    if(caseToReveal.isRevealed) {
      println("Case is already revealed!")
      return false
    }
    if (caseToReveal.isMine) {
      // Si la case révélée contient une mine, la partie est perdue
      caseToReveal.isRevealed = true
      true
    } else if (won(grid)) {
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

  def revealNeighbors(grid: Seq[Seq[Case]], x: Int, y: Int): Unit = {
    if (x < 0 || y < 0 || x >= grid.length || y >= grid(0).length) return // Check if the coordinates are within the grid bounds
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


  def won(grid: Seq[Seq[Case]]): Boolean = {
    grid.flatten.forall {
      case c: Case if !c.isMine => c.isRevealed
      case _ => true
    }
  }


  def init_case(k: Int): Case = {
    if (k == -1) {
      val mineCase = new Case(true, false, false, (0, 0))
      new Mine(mineCase)
      mineCase
    } else {
      val emptyCase = new Case(false, false, false, (0, 0))
      new Empty(k, emptyCase)
      emptyCase
    }
  }

  def init_game(w: Int, h: Int, n: Int): Seq[Seq[Case]] = {
    val m = Seq.fill(w, h)(new Case(false, false, false, (0, 0)))
    for (_ <- 1 to n) random_mine(m)
    m
  }

}
