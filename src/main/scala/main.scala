import binarytree.Tree
given (Int => String) = _.toString

@main def main(): Unit =
  val tree = Tree.empty[Int]
  val newTree: Tree[Int] = tree.add(10, 5, 15, 2, 8, 21, 6, 9)
  println(newTree.toString)
  val balanced = newTree.balanced
  println(balanced.toString)
