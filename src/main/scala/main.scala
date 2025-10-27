import binarytree.Tree

given (Int => String) = _.toString

@main def main(): Unit =
  val tree: Tree[Int] = Tree.empty[Int].add(5).add(2).add(1).add(3).add(4).add(6)
  println(tree.toString)
