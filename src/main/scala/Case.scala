class Case(var isMine: Boolean, var isRevealed: Boolean = false, var isFlagged: Boolean = false, var coordinates: (Int, Int)) {
  // déclaration de la variable value
  var value: Int = 0

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

  def setCoordinates(x: Int, y: Int): (Int, Int) = {
    val coordinate = (x, y)
    coordinate
  }
  def getCoordinates: (Int, Int) = {
    coordinates
  }
  def incrementValue(): Unit = {
    value += 1
  }

  def setMine(): Unit = {
    this.isMine = true
  }
}
