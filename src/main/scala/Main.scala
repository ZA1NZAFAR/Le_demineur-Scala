object Main {
  def main(args: Array[String]): Unit = {
    val game = Partie

    // Initialiser le jeu avec une grille de 40x10 cases et 50 mines
    val grid = game.init_game(40, 10, 50)

    // Boucle de jeu
    var gameOver = false
    while (!gameOver) {
      // Afficher la grille actuelle
      game.display_grid(grid)

      // Demandez à l'utilisateur de sélectionner les coordonnées de la case à révéler
      println("Veuillez entrer les coordonnées de la case à révéler (x y) :")
      val coords = scala.io.StdIn.readLine().split(" ").map(_.toInt)

      // Révélez la case et mettez à jour le nombre de mines restantes à trouver
      gameOver = game.reveal_case(grid, coords(0), coords(1))
    }

    // Afficher le résultat final
    game.display_grid(grid)
    if (game.won(grid)) {
      println("Félicitations, vous avez gagné!")
    } else {
      println("Désolé, vous avez perdu.")
    }
  }
}
