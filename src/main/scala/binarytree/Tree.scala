package binarytree

import scala.collection.mutable

enum Tree[+T] extends Iterable[T] {
  case Branch(left: Tree[T], value: T, right: Tree[T])
  case Leaf(value: T)
  case Stump

  inline def toString(using str: T => String): String = Tree.toString[T](this)
  inline def add[S >: T, U >: S](value: S)(using ord: Ordering[U]): Tree[S] = Tree.add(this, value)
  inline def add[S >: T, U >: S](values: Seq[S])(using ord: Ordering[U]): Tree[S] = Tree.add(this, values)

  override lazy val size: Int = Tree.size(this)
  override lazy val toList: List[T] = Tree.toList(this)
  override def iterator: Iterator[T] = toList.iterator

  def balanced[U >: T](using ord: Ordering[U]): Tree[T] = {
    val buffer = toList.toBuffer
    val newListBuffer = mutable.ListBuffer[T]()
    while (buffer.nonEmpty) {
      val t = buffer.remove(buffer.size / 2)
      newListBuffer.addOne(t)
    }
    Tree(newListBuffer.toList)
  }
}

object Tree {
  inline def empty[T]: Tree[T] = Stump
  inline def apply[T](value: T): Tree[T] = Leaf(value)
  inline def apply[T, U >: T](values: Seq[T])(using ord: Ordering[U]): Tree[T] = add(Stump, values)

  def toList[T](tree: Tree[T]): List[T] = tree match {
    case Stump                      => Nil
    case Leaf(value)                => List(value)
    case Branch(left, value, right) => toList(left) ::: value :: toList(right)
  }

  def size[T](tree: Tree[T]): Int = tree match {
    case Stump                      => 0
    case Leaf(value)                => 1
    case Branch(left, value, right) => size(left) + 1 + size(right)
  }

  def toString[T](tree: Tree[T], acc: String = "", level: Int = 0)(using str: T => String): String = {
    def append(valueStr: String) = s"$acc\n${"-" * level}$valueStr"
    tree match {
      case Stump =>
        append("X")
      case Leaf(value) =>
        append(str(value))
      case Branch(left, value, right) =>
        val newAcc = toString(left, append(str(value)), level + 1)
        toString(right, newAcc, level + 1)
    }
  }

  def add[T, U >: T](tree: Tree[T], values: Seq[T])(using ord: Ordering[U]): Tree[T] =
    values.foldLeft(tree)((acc, value) => add(acc, value))

  def add[T, U >: T](tree: Tree[T], t: T)(using ord: Ordering[U]): Tree[T] = tree match {
    case Stump                                                   => Leaf(t)
    case Leaf(value) if ord.compare(t, value) < 0                => Branch(Leaf(t), value, Stump)
    case Leaf(value) if ord.compare(t, value) > 0                => Branch(Stump, value, Leaf(t))
    case leaf: Leaf[T]                                           => leaf // no changes
    case Branch(left, value, right) if ord.compare(t, value) < 0 => Branch(add(left, t), value, right)
    case Branch(left, value, right) if ord.compare(t, value) > 0 => Branch(left, value, add(right, t))
    case branch: Branch[T]                                       => branch // no changes
  }
}