package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (c == r || c == 0) {
      1
    } else {
      pascal(c - 1, r - 1) + pascal(c, r - 1)
    }
  }

  def testBalance(args: Array[String]) {
    println("Balance strings:")


    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }
  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def helper(chars:List[Char], openParens:Int): Boolean = {
      if (chars.isEmpty) {
        openParens == 0
    }
      else if (openParens<0){
        false
      }
      else {
        val head = chars.head
        if (head == '(') {
          helper(chars.tail, openParens+1)
        } else if (head == ')') {
          helper(chars.tail, openParens-1)
        }
        else {
          helper(chars.tail, openParens)
        }

      }

    }

    helper(chars,0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) {
      1  // There's exactly 1 way to make change for 0 money
    } else if (money < 0 || coins.isEmpty) {
      0  // No ways to make change if money is negative or no coins are left
    } else {
      // Include the first coin (coins.head) + exclude the first coin (coins.tail)
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }
  }

}
