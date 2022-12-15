class Case(var isMine: Boolean, var isRevealed: Boolean = false, var isFlagged: Boolean = false) {
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
}
