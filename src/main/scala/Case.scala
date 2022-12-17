import scala.annotation.unused

class Case(var isMine: Boolean, var isRevealed: Boolean = false, var isFlagged: Boolean = false, var coordinates: (Int, Int)) {
  def reveal(): Unit = {
    isRevealed = true
  }

  def flag(): Unit = {
    isFlagged = true
  }

  def unflag(): Unit = {
    isFlagged = false
  }

  def toggleFlag(): Unit = {
    isFlagged = !isFlagged
  }

  // (?) maybe we can use them later, when we gonna begin the implementation of the game with the functions

  def setCoordinates(x: Int, y: Int): (Int, Int) = {
    val coordinate = (x, y)
    coordinate
  }
  def getCoordinates: (Int, Int) = {
    coordinates
  }

}
