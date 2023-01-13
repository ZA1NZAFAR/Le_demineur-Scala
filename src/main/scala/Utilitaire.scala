import scala.annotation.tailrec
import scala.util.Random
object Utilitaire {

  /* Seq will be used to represent the matrix since it's mutable whereas Array isn't.
  So, we prefer it since we can't predicate what dimension the user will use, and as we'll need to "manipulate" the matrix
  => something that we can't do directly by using "Array", on the contrary of "Seq".
  Explanation of the implementation: we'll first get the length of the matrix, which will be equal to the length of it
  then, for the number of columns, we'll need to choose the max length of all the rows of the matrix. Let's get an example to explain it :
  In the the following matrix, / : indicates a null values.
      Matrix example :
      123
      34/
      /7/
      So, here we'll need to take 3 as the number of columns, as it's the max length value that "satisfy" all the other
   */
  def get_dimension(m: Seq[Seq[Case]]): (Int, Int) = {
    // (?) maybe we can use tha class empty for the following ???
    if (m.isEmpty) return (0, 0)
    val rowNumber = m.length
    val columnNumber = m.map(_.length).max
    (rowNumber, columnNumber)
  }


  // Here we'll just return true if the x and y value of the given coordinates is in the defined matrix.
  //First, we "extract" x and y value from the "coordinate" tuple, then we check if the "mathematical condition" for a matrix is satisfied
  def is_inside(m: Seq[Seq[Case]], coordinates: (Int, Int)): Boolean = {
    val (row, col) = coordinates
    val numRows = m.length
    val numCols = m.head.length

    row >= 0 && row < numRows && col >= 0 && col < numCols
  }


// Allows to check if the game is won or not (if all the cases without mines have been revealed)
  def won(grid: Seq[Seq[Case]]): Boolean = {
    grid.flatten.forall {
      case c: Case if !c.isMine => c.isRevealed
      case _ => true
    }
  }


  //we'll just create a matrix of "Case" with the given dimensions, then we'll fill it with the number of mines given
  def init_game(w: Int, h: Int, n: Int): Seq[Seq[Case]] = {
    val m = Seq.fill(w, h)(new Case(false, false, false, (0, 0)))
    for (_ <- 1 to n) fillGrid(m)
    m
  }

  // Allow to fill the grid with the number of mines given and to set the value of each case (number of mines around it)
  // In the subject we were asked to check the neighbours every time user asked to reveal a case, but here we'll do it once for better performance
  def fillGrid(m: Seq[Seq[Case]]): Unit = {
    val (numRows, numCols) = get_dimension(m) // La fonction get_dimension se trouve dans l'objet "Partie"
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
        if is_inside(m, (i, j)) // La fonction is_inside se trouve également dans l'objet "Partie"
      } incr_tab(m, (i, j)) // La méthode incr_tab se trouve également dans l'objet "Partie"
    }
  }

  // This function will be used to increment the number of mines around a given case
  def incr_tab(m: Seq[Seq[Case]], coordinates: (Int, Int)): Unit = {
    val (x, y) = coordinates
    if (!m(x)(y).isMine) m(x)(y).incrementValue()
  }





  def getIntFromUser(): Int = {
    getIntFromUser(Integer.MIN_VALUE, Integer.MAX_VALUE)
  }

  // This function will be used to get an integer from the user, and will continue to ask him until he gives a valid integer
  def getIntFromUser(min: Int, max: Int): Int = {
    val input = scala.io.StdIn.readLine()
    try {
      input.toInt
      if (input.toInt <= 0) {
        throw new Exception("The value must be positive")
      }
      if (input.toInt < min || input.toInt > max) {
        throw new Exception("The value must be between " + min + " and " + max)
      }
      input.toInt
    } catch {
      case e: NumberFormatException =>
        println("The value must be a valid integer and not empty")
        getIntFromUser(min, max)
      case e: Exception =>
        println(e.getMessage)
        println(e.getClass)
        getIntFromUser(min, max)
    }
  }

}
