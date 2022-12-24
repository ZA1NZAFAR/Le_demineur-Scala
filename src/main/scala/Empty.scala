//indiquer dans le main que le nbre de mines sera entre 0 et 8:
class Empty(val adjacentMines: Int, c: Case) extends Case(c.isMine, c.isRevealed, c.isFlagged, c.coordinates) {
  override def toString: String = adjacentMines.toString

  override def reveal(): Unit = {
    super.reveal()
  }
}

