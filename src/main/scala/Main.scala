/*
Authors :
    Meryem KOSE
    Ousseynou SAKHO
    Zain ZAFAR
 */


object Main {
  def main(args: Array[String]): Unit = {
    // initialize the game with user responses
    println("Number of rows ? :")
    val rows = Utilitaire.getIntFromUser()
    println("Number of columns ? :")
    val columns = Utilitaire.getIntFromUser()
    println("Number of mines to place? :")
    val mines = Utilitaire.getIntFromUser(0, rows * columns)

    // initialize the game
    val game = new Minesweeper(rows, columns, mines)


    // Boucle de jeu
    var gameOver = false
    while (!gameOver) {
      // Afficher la grille actuelle
      game.display()

      // Demandez à l'utilisateur de sélectionner les coordonnées de la case à révéler
      var row: Int = 0
      var column: Int = 0
      var input_correct = false
      while (!input_correct) {
        try {
          println("Choose row number : (Starting from 1)")
          row = Utilitaire.getIntFromUser(1, rows) - 1
          println("Choose column number : (Starting from 1)")
          column = Utilitaire.getIntFromUser(1, columns) - 1
          if (row < 0 || column < 0 || row >= rows || column >= columns) {
            throw new Exception()
          }
          input_correct = true
        } catch {
          case _: Exception =>
            println("Invalid input, please enter a valid row and column number")
        }
      }

      // Révélez la case et mettez à jour le nombre de mines restantes à trouver
      gameOver = game.reveal_case(row, column)
    }

    // Afficher le résultat final
    game.display()
    if (Utilitaire.won(game.grid)) {
      println("Wow, you just ....... won!")
    } else {
      println("Oops you just stepped on a mine :(")
    }
  }
}
