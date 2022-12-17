//indiquer dans le main que le nbre de mines sera entre 0 et 8:
class Empty(adjacentMines: Int) {
  override def toString: String = adjacentMines.toString
  var cases : List[Case] = List[Case](adjacentMines)
}
