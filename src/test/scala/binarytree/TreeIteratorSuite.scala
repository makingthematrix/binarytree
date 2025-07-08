package binarytree

import binarytree.Tree.{Branch, Leaf, Stump, iterator}

class TreeIteratorSuite extends munit.FunSuite:
  given intToString: (Int => String) = _.toString

  test("iterator on Stump") {
    val stump = Stump
    val iter = iterator(stump)
    assertEquals(iter.hasNext, false)
  }

  test("iterator on Leaf") {
    val leaf = Leaf(42)
    val iter = iterator(leaf)
    assertEquals(iter.hasNext, true)
    assertEquals(iter.next(), 42)
    assertEquals(iter.hasNext, false)
  }

  test("iterator on simple Branch") {
    val tree = Branch(Leaf(1), 2, Leaf(3))
    val iter = iterator(tree)
    assertEquals(iter.hasNext, true)
    assertEquals(iter.next(), 1) // Start with left-most leaf
    assertEquals(iter.next(), 2) // Then parent
    assertEquals(iter.next(), 3) // Then right leaf
    assertEquals(iter.hasNext, false)
  }

  test("iterator on Branch with Stump") {
    val tree = Branch(Stump, 2, Leaf(3))
    val iter = iterator(tree)
    assertEquals(iter.hasNext, true)
    assertEquals(iter.next(), 2) // Start with parent since left is Stump
    assertEquals(iter.next(), 3) // Then right leaf
    assertEquals(iter.hasNext, false)
  }

  test("iterator on complex tree") {
    val tree = Branch(
      Branch(Leaf(1), 2, Leaf(3)),
      4,
      Branch(Stump, 5, Leaf(6))
    )
    val iter = iterator(tree)
    assertEquals(iter.hasNext, true)
    assertEquals(iter.next(), 1) // Left-most leaf
    assertEquals(iter.next(), 2) // Parent of left-most leaf
    assertEquals(iter.next(), 3) // Right leaf of left branch
    assertEquals(iter.next(), 4) // Root
    assertEquals(iter.next(), 5) // Parent in right branch
    assertEquals(iter.next(), 6) // Right-most leaf
    assertEquals(iter.hasNext, false)
  }

  test("iterator on more complex tree") {
    val tree = Branch(
      Branch(
        Branch(Leaf(1), 2, Stump),
        3,
        Leaf(4)
      ),
      5,
      Branch(
        Leaf(6),
        7,
        Branch(Stump, 8, Leaf(9))
      )
    )
    val iter = iterator(tree)
    val result = iter.toList
    assertEquals(result, List(1, 2, 3, 4, 5, 6, 7, 8, 9))
  }

  test("iterator collects all elements") {
    val values = List(5, 3, 7, 2, 4, 6, 8, 1, 9)
    val tree = values.foldLeft(Stump: Tree[Int])((t, v) => Tree.add(t, v))
    val iter = iterator(tree)
    val result = iter.toList
    assertEquals(result.sorted, values.sorted)
    assertEquals(result.length, values.length)
  }