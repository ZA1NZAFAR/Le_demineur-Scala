
class Minesweeper(val grid: Seq[Seq[Case]]) {
  val functions = new Functions // Création d'une instance de la classe Functions
  val mine = new Mine(new Case(true, false, false, (0, 0)))
  val empty = new Empty(0, new Case(false, false, false, (0, 0)))

  def display(): Unit = {
    val (numRows, numCols) = functions.get_dimension(grid)
    for {
      i <- 0 until numRows
      j <- 0 until numCols
    } {
      val cell = grid(i)(j)
      if (cell.isRevealed) {
        cell match {
          case `mine` => print("*")
          case `empty` => print(empty.adjacentMines)
        }
      } else if (cell.isFlagged) print("F")
      else print(" ")
      if (j == numCols - 1) println()
    }
  }



  def interact(x: Int, y: Int): Unit = {
    val cell = grid(x)(y)
    cell match {
      case mine: Mine =>
        println("You lost!")
        sys.exit(1)
      case empty: Empty =>
        if (empty.adjacentMines == 0) reveal(x, y)
        else cell.reveal()
    }
  }

  def reveal(x: Int, y: Int): Unit = {
    val cell = grid(x)(y)
    if (!cell.isRevealed && !cell.isFlagged) {
      cell.reveal()
      for {
        i <- x-1 to x+1
        j <- y-1 to y+1
        if functions.is_inside(grid, (i, j)) && !grid(i)(j).isRevealed
      } interact(i, j)
    }
  }



}
