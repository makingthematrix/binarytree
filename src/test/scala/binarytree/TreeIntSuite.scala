package binarytree

import binarytree.Tree.{Branch, Leaf, Stump, add}

class TreeIntSuite extends munit.FunSuite:
  given intToString: (Int => String) = _.toString

  test("serialize Stump") {
    val stump = Stump
    val result = stump.toString
    assertEquals(result.trim, "X")
  }

  test("serialize Leaf") {
    val leaf = Leaf(42)
    val result = leaf.toString
    assertEquals(result.trim, "42")
  }

  test("serialize Branch with two Leafs") {
    val tree = Branch(Leaf(1), 2, Leaf(3))
    val result = tree.toString
    val expected = "2\n-1\n-3"
    assertEquals(result.trim, expected)
  }

  test("serialize complex tree") {
    val tree = Branch(
      Branch(Leaf(1), 2, Leaf(3)),
      4,
      Branch(Stump, 5, Leaf(6))
    )
    val result = tree.toString
    val expected = "4\n-2\n--1\n--3\n-5\n--X\n--6"
    assertEquals(result.trim, expected)
  }

  // Tests for Tree.add method
  test("add to Stump") {
    val stump = Stump
    val result = add(stump, 42)
    assertEquals(result, Leaf(42))
  }

  test("add smaller value to Leaf") {
    val leaf = Leaf(42)
    val result = add(leaf, 30)
    assertEquals(result, Branch(Leaf(30), 42, Stump))
  }

  test("add larger value to Leaf") {
    val leaf = Leaf(42)
    val result = add(leaf, 50)
    assertEquals(result, Branch(Stump, 42, Leaf(50)))
  }

  test("add duplicate value to Leaf") {
    val leaf = Leaf(42)
    val result = add(leaf, 42)
    assertEquals(result, leaf) // No change expected
  }

  test("add to left branch") {
    val tree = Branch(Leaf(20), 30, Leaf(40))
    val result = add(tree, 10)
    assertEquals(result, Branch(Branch(Leaf(10), 20, Stump), 30, Leaf(40)))
  }

  test("add to right branch") {
    val tree = Branch(Leaf(20), 30, Leaf(40))
    val result = add(tree, 50)
    assertEquals(result, Branch(Leaf(20), 30, Branch(Stump, 40, Leaf(50))))
  }

  test("add duplicate value to Branch") {
    val tree = Branch(Leaf(20), 30, Leaf(40))
    val result = add(tree, 30)
    assertEquals(result, tree) // No change expected
  }

  // Tests for Tree.balanced method
  test("balance Stump") {
    val stump = Stump
    val result = stump.balanced[Int]
    assertEquals(result, Stump)
  }

  test("balance Leaf") {
    val leaf = Leaf(42)
    val result = leaf.balanced
    assertEquals(result, Leaf(42))
  }

  test("balance simple Branch") {
    // Create a tree with elements in non-sorted order
    val tree = Tree(30, 10, 50)
    val result = tree.balanced

    // The balanced tree should have the middle element (30) as the root
    // and the other elements as left and right children
    val expected = Branch(Leaf(10), 30, Leaf(50))
    assertEquals(result, expected)
  }

  test("balance unbalanced tree") {
    // Create a tree with elements in non-sorted order
    val tree = Tree(10, 20, 30, 40, 50)
    val result = tree.balanced

    // Check that the tree is balanced and contains all the original elements
    // The elements should be in ascending order when traversed in-order
    assertEquals(result.toList.sorted, List(10, 20, 30, 40, 50))

    // Check that the tree has the expected structure
    // The root should be the middle element
    result match
      case Branch(left, value, right) =>
        assertEquals(value, 30)
        // Check that the left and right subtrees are balanced
        assertEquals(left.size, 2)
        assertEquals(right.size, 2)
      case _ => fail("Expected a Branch")
  }

  test("balance complex unbalanced tree") {
    // Create a tree with elements in non-sorted order
    val tree = Tree(100, 50, 150, 25, 75, 125, 175)
    val result = tree.balanced

    // Check that the tree is balanced and contains all the original elements
    // The elements should be in ascending order when traversed in-order
    assertEquals(result.toList.sorted, List(25, 50, 75, 100, 125, 150, 175))

    // Check that the tree has the expected structure
    // The root should be the middle element
    result match
      case Branch(left, value, right) =>
        assertEquals(value, 100)
        // Check that the left and right subtrees are balanced
        assertEquals(left.size, 3)
        assertEquals(right.size, 3)
      case _ => fail("Expected a Branch")
  }

  test("balance already balanced tree") {
    // Create an already balanced tree
    val tree = Branch(Leaf(10), 20, Leaf(30))
    val result = tree.balanced

    // The result should still be balanced, but might have a different structure
    // due to the sorting and middle-element selection
    val expected = Branch(Leaf(10), 20, Leaf(30))
    assertEquals(result, expected)
  }
