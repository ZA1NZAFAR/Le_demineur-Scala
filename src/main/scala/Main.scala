import java.lang

object Main {
  def main(args: Array[String]): Unit = {
    val game = Partie

    // Initialiser le jeu avec une grille de 40x10 cases et 50 mines
    val grid = game.init_game(10, 10, 10)

    // Boucle de jeu
    var gameOver = false
    while (!gameOver) {
      // Afficher la grille actuelle
      game.display_grid(grid)

      // Demandez à l'utilisateur de sélectionner les coordonnées de la case à révéler
      var coor_x: Int = 0
      var coor_y: Int = 0
      var input_correct = false
      while (!input_correct) {
        try {
          println("Veuillez entrer la valeur de x : ")
          coor_x = scala.io.StdIn.readInt()
          println("Veuillez entrer la valeur de y : ")
          coor_y = scala.io.StdIn.readInt()
          input_correct = true
        } catch {
          case _: Exception =>
            println("Enter valid value please")
        }
      }

      // Révélez la case et mettez à jour le nombre de mines restantes à trouver
      gameOver = game.reveal_case(grid, coor_x, coor_y)
    }

    // Afficher le résultat final
    game.display_grid(grid)
    if (game.won(grid)) {
      println("Wow, you just ....... won!")
    } else {
      println("Oops you just stepped on a mine :(")
    }
  }
}
