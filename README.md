# Binary Tree Implementation in Scala 3

A comprehensive implementation of an ordered binary tree data structure in Scala 3, featuring modern language constructs and functional programming principles.

## Overview

This project implements a generic ordered binary tree using Scala 3's enum feature. The tree maintains elements in sorted order and provides efficient insertion, traversal, and balancing operations.

### Key Features

- **Generic Implementation**: Works with any type that has an `Ordering` instance
- **Immutable Design**: All operations return new tree instances without modifying the original
- **Iterable Interface**: Implements Scala's `Iterable[T]` trait for seamless integration with collections
- **Tree Balancing**: Includes functionality to balance unbalanced trees for optimal performance
- **Visual Representation**: Provides string representation with tree structure visualization

## Tree Structure

The binary tree is implemented as an enum with three cases:

- **`Stump`**: Represents an empty tree (equivalent to null/None in other implementations)
- **`Leaf(value: T)`**: A terminal node containing a single value
- **`Branch(left: Tree[T], value: T, right: Tree[T])`**: An internal node with left and right subtrees

### Binary Tree Properties

This implementation follows the standard binary search tree property:
- For any node with value `v`, all values in the left subtree are less than `v`
- All values in the right subtree are greater than `v`
- Duplicate values are ignored during insertion (no duplicates are stored)

## Usage Examples

```scala
import binarytree.Tree

// Create a tree with integers
val intTree = Tree(5, 3, 7, 1, 9, 4, 6)

// Create a tree with strings
val stringTree = Tree("apple", "banana", "cherry", "date")

// Add elements to existing tree
val expandedTree = intTree.add(2, 8, 10)

// Balance an unbalanced tree
val balancedTree = intTree.balanced

// Convert to list (in-order traversal)
val sortedList = intTree.toList  // List(1, 3, 4, 5, 6, 7, 9)

// Iterate through elements
intTree.foreach(println)

// Get tree size
val size = intTree.size

// Visual representation
println(intTree.toString)
```

## Building the Project

This project uses sbt (Scala Build Tool) for building and dependency management.

### Prerequisites

- Java 8 or higher
- sbt 1.x

### Build Commands

```bash
# Compile the project
sbt compile

# Run the main application (if available)
sbt run

# Create a JAR file
sbt package

# Clean build artifacts
sbt clean

# Open sbt interactive shell
sbt
```

## Running Tests

The project includes comprehensive test suites using MUnit testing framework:

```bash
# Run all tests
sbt test

# Run tests continuously (re-runs on file changes)
sbt ~test

# Run specific test suite
sbt "testOnly binarytree.TreeIntSuite"
sbt "testOnly binarytree.TreeCitySuite"
sbt "testOnly binarytree.TreeIteratorSuite"

# Run tests with verbose output
sbt "test --verbose"
```

### Test Coverage

The test suites cover:

- **TreeIntSuite**: Integer-based tree operations (insertion, balancing, serialization)
- **TreeCitySuite**: Custom object handling with custom ordering (demonstrates generic usage)
- **TreeIteratorSuite**: Iterator functionality and Iterable trait compliance

## Project Structure

```
binarytree/
├── build.sbt                          # Build configuration
├── src/
│   ├── main/scala/
│   │   └── binarytree/
│   │       └── Tree.scala              # Main tree implementation
│   └── test/scala/
│       └── binarytree/
│           ├── TreeIntSuite.scala      # Integer tree tests
│           ├── TreeCitySuite.scala     # Custom object tests
│           └── TreeIteratorSuite.scala # Iterator tests
└── README.md                           # This file
```

## Implementation Details

### Core Operations

- **Insertion**: `O(log n)` average case, `O(n)` worst case for unbalanced trees
- **Search**: Inherent through ordered structure and Iterable interface
- **Traversal**: In-order traversal via `toList` and `iterator` methods
- **Balancing**: Converts tree to sorted list and rebuilds as balanced tree

### Scala 3 Features Used

- **Enums**: Modern algebraic data type representation
- **Given Instances**: Type class instances for automatic string conversion
- **Inline Methods**: Performance optimization for simple operations
- **Context Parameters**: Clean API for ordering and string conversion requirements

## License

This project is licensed under the GPL 3.0 License - see the [LICENSE](LICENSE) file for details.

## Author

**Maciej Gorywoda**  
Email: makingthematrix@protonmail.com  
GitHub: [@makingthematrix](https://github.com/makingthematrix)

## Contributing

Contributions are welcome! Please feel free to submit issues, feature requests, or pull requests.

## Version

Current version: 0.1.0-SNAPSHOT