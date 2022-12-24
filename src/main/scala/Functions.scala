import scala.util.Random

class Functions {

  /* Seq will be used to represent the matrix since it's mutable whereas Array isn't.
  So, we prefer it since we can't predicate what dimension the user will use, and as we'll need to "manipulate" the matrix
  => something that we can't do directly by using "Array", on the contrary of "Seq".
  Explanation of the implementation: we'll first get the length of the matrix, which will be equal to the length of it
  then, for the number of columns, we'll need to choose the max length of all the rows of the matrix. Let's get an example to explain it :
  In the the following matrix, / : indicates a null values.
      Matrix example :
      123
      34/
      /7/
      So, here we'll need to take 3 as the number of columns, as it's the max length value that "satisfy" all the other
   */
  def get_dimension(m: Seq[Seq[Case]]): (Int, Int) = {
    // (?) maybe we can use tha class empty for the following ???
    if (m.isEmpty) return (0, 0)
    val rowNumber = m.length
    val columnNumber = m.map(_.length).max
    (rowNumber, columnNumber)
  }


  // Here we'll just return true if the x and y value of the given coordinates is in the defined matrix.
  //First, we "extract" x and y value from the "coordinate" tuple, then we check if the "mathematical condition" for a matrix is satisfied
  def is_inside(m: Seq[Seq[Case]], coordinates: (Int, Int)): Boolean = {
    val (row, col) = coordinates
    val numRows = m.length
    val numCols = m.head.length

    row >= 0 && row < numRows && col >= 0 && col < numCols
  }




  def get_neighbors(m: Seq[Seq[Case]], coordinates: (Int, Int)): Seq[(Int, Int)] = {
    val (row, col) = coordinates
    val numRows = m.length
    val numCols = m.head.length

    // First, we define all the 8 different neighbors (we begin from "the bottom" of given coordinate) that a given coordinates can have, then we'll filter on it to
    // satisfy the different conditions :

    // - In the corner : 3 neighbors
    // - In the edge : 5 neighbors
    // - In the middle : all the neighbors
    val neighbors = Seq((row + 1, col + 1), (row + 1, col), (row + 1, col - 1), (row, col + 1), (row, col - 1), (row - 1, col + 1), (row - 1, col), (row - 1, col - 1))

    // Of course, we need to check that all the neighbors are inside the matrix :
    val validNeighbors = neighbors.filter(coordinates => is_inside(m, coordinates))
    //We'll study all the different cases that are possible :
    // The corners :
    // case 1 : In the top right corner :
    if (row == numRows - 1 && col == 0) {
      validNeighbors.filter(coordinates => coordinates._1 <= 1 && coordinates._2 >= col - 1)
    }
    // case 2 : In the top left corner :
    else if (row == 0 && col == numCols - 1) {
      validNeighbors.filter(coordinates => coordinates._1 <= 1 && coordinates._2 <= 1)
    }
    // case 3 : In the right bottom corner :
    else if (row == numRows - 1 && col == numCols - 1) {
      // Coin inférieur droit
      validNeighbors.filter(coordinates => coordinates._1 >= row - 1 && coordinates._2 >= col - 1)
    }
    // case 4 : In the left bottom corner :
    else if (row == 0 && col == 0) {
      validNeighbors.filter(coordinates => coordinates._1 >= row - 1 && coordinates._2 <= 1)
    }
    // The edges :
    // All the edges of the first line :
    else if (row == 0) {
      validNeighbors.filter(coordinates => coordinates._1 <= 1)
    }
    // All the edges of the first column :
    else if (col == 0) {
      validNeighbors.filter(coordinates => coordinates._2 <= 1)
    }
    // All the edges of the last line :
    else if (row == numRows - 1) {
      validNeighbors.filter(coordinates => coordinates._1 >= row - 1)
    }
    // All the edges of the last column :
    else if (col == numCols - 1) {
      validNeighbors.filter(coordinates => coordinates._2 >= col - 1)
    }
    // Finally, for all the other cases that are not in a border or an edge :
    else {
      validNeighbors
    }
  }

  // For generate random coordinates, we'll use the random function between the dimensions of the matrix. If the given
  // values isn't inside "the valid dimensions" of the matrix, we'll regenerate it until it is, then return this valid coordinate:
  def randoms_coords(m: Seq[Seq[Case]]): (Int, Int) = {
    val (rowNumber, columnsNumber) = get_dimension(m)
    var randomCoordinates = (Random.nextInt(rowNumber), Random.nextInt(columnsNumber))
    var counter = 0
    while (!is_inside(m, randomCoordinates) && counter < 1000) {
      randomCoordinates = (Random.nextInt(rowNumber), Random.nextInt(columnsNumber))
      counter += 1
    }
    randomCoordinates
  }


}
