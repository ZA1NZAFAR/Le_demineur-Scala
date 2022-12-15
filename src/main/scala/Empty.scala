class Empty(adjacentMines: Int) {
  override def toString: String = adjacentMines.toString
  var cases : List[Case] = List[Case](adjacentMines)
}
