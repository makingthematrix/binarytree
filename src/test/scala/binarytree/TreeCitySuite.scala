package binarytree

import binarytree.Tree.{Branch, Leaf, Stump, add, create}

class TreeCitySuite extends munit.FunSuite:
  given (City => String) = c => s"${c.name}: ${c.population}"
  given Ordering[City] = Ordering.by(_.name)
  
  // Tests for Tree.serialize method
  test("serialize Leaf") {
    val tokyo = Cities.get("Tokyo").get
    val leaf = Leaf(tokyo)
    val result = leaf.toString
    assertEquals(result.trim, "Tokyo: 37400068")
  }

  test("serialize Branch with two Leafs") {
    val tokyo = Cities.get("Tokyo").get
    val london = Cities.get("London").get
    val paris = Cities.get("Paris").get
    val tree = Branch(Leaf(london), paris, Leaf(tokyo))
    val result = tree.toString
    val expected = "Paris: 11142000\n-London: 9002488\n-Tokyo: 37400068"
    assertEquals(result.trim, expected)
  }

  test("serialize complex tree") {
    val berlin = Cities.get("Berlin").get
    val london = Cities.get("London").get
    val paris = Cities.get("Paris").get
    val tokyo = Cities.get("Tokyo").get
    val tree = Branch(
      Branch(Leaf(berlin), london, Leaf(paris)),
      tokyo,
      Branch(Stump, Cities.get("Washington DC").get, Leaf(Cities.get("Beijing").get))
    )
    val result = tree.toString
    val expected = "Tokyo: 37400068\n-London: 9002488\n--Berlin: 3677472\n--Paris: 11142000\n-Washington DC: 689545\n--X\n--Beijing: 20462610"
    assertEquals(result.trim, expected)
  }

  // Tests for Tree.add method
  test("add to Stump") {
    val stump = Stump
    val tokyo = Cities.get("Tokyo").get
    val result = add(stump, tokyo)
    assertEquals(result, Leaf(tokyo))
  }

  test("add city with name before current to Leaf") {
    val paris = Cities.get("Paris").get
    val london = Cities.get("London").get
    val leaf = Leaf(paris)
    val result = add(leaf, london)
    assertEquals(result, Branch(Leaf(london), paris, Stump))
  }

  test("add city with name after current to Leaf") {
    val london = Cities.get("London").get
    val tokyo = Cities.get("Tokyo").get
    val leaf = Leaf(london)
    val result = add(leaf, tokyo)
    assertEquals(result, Branch(Stump, london, Leaf(tokyo)))
  }

  test("add duplicate city to Leaf") {
    val tokyo = Cities.get("Tokyo").get
    val tokyoDuplicate = City("Tokyo", 37400068)
    val leaf = Leaf(tokyo)
    val result = add(leaf, tokyoDuplicate)
    assertEquals(result, leaf) // No change expected
  }

  test("add to left branch") {
    val berlin = Cities.get("Berlin").get
    val london = Cities.get("London").get
    val paris = Cities.get("Paris").get
    val tree = Branch(Leaf(berlin), london, Leaf(paris))
    val addisAbaba = Cities.get("Addis Ababa").get
    val result = add(tree, addisAbaba)
    assertEquals(result, Branch(Branch(Leaf(addisAbaba), berlin, Stump), london, Leaf(paris)))
  }

  test("add to right branch") {
    val berlin = Cities.get("Berlin").get
    val london = Cities.get("London").get
    val paris = Cities.get("Paris").get
    val tree = Branch(Leaf(berlin), london, Leaf(paris))
    val tokyo = Cities.get("Tokyo").get
    val result = add(tree, tokyo)
    assertEquals(result, Branch(Leaf(berlin), london, Branch(Stump, paris, Leaf(tokyo))))
  }

  test("add duplicate city to Branch") {
    val berlin = Cities.get("Berlin").get
    val london = Cities.get("London").get
    val paris = Cities.get("Paris").get
    val tree = Branch(Leaf(berlin), london, Leaf(paris))
    val londonDuplicate = City("London", 9002488)
    val result = add(tree, londonDuplicate)
    assertEquals(result, tree) // No change expected
  }

  // Test for Tree.create method
  test("create tree with 5 cities") {
    // Select 5 cities
    val berlin = Cities.get("Berlin").get
    val london = Cities.get("London").get
    val paris = Cities.get("Paris").get
    val tokyo = Cities.get("Tokyo").get
    val washingtonDC = Cities.get("Washington DC").get

    // Create a list of cities
    val cities = List(tokyo, berlin, paris, london, washingtonDC)

    // Create a tree using Tree.create
    val tree = create(cities)

    // Verify the structure of the resulting tree
    // The structure depends on the order in which cities are added to the tree
    val expectedTree = Branch(
      Branch(
        Stump,
        berlin,
        Branch(
          Leaf(london),
          paris,
          Stump
        )
      ),
      tokyo,
      Leaf(washingtonDC)
    )

    assertEquals(tree, expectedTree)
  }
