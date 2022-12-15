class Mine(val cell: Case) {
  def reveal(): Unit = {
    cell.isRevealed = true
  }

  def flag(): Unit = {
    cell.isFlagged = true
  }

  def unflag(): Unit = {
    cell.isFlagged = false
  }

  def toggleFlag(): Unit = {
    cell.isFlagged = !cell.isFlagged
  }
}
