import binarytree.Tree
import binarytree.Tree.toString

given (Int => String) = _.toString

@main
def main(): Unit =
  val newTree: Tree[Int] = Tree(10, 5, 15, 2, 8, 21, 6, 9)
  println(toString(newTree, toString))
  val balanced = Tree.balance(newTree)
  println(toString(balanced, toString))



