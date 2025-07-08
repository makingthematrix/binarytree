package binarytree

import binarytree.Tree.{balance, size, toList}

enum Tree[+T] extends Iterable[T]:
  case Branch(left: Tree[T], value: T, right: Tree[T])
  case Leaf(value: T)
  case Stump

  def toString(using str: T => String): String = Tree.toString[T](this)
  def balanced[U >: T](using ord: Ordering[U]): Tree[T] = balance(this)

  override lazy val size: Int = Tree.size(this)
  override lazy val toList: List[T] = Tree.toList(this)
  override def iterator: Iterator[T] = toList.iterator

object Tree:
  inline def leaf[T](value: T): Tree[T] = Leaf(value)
  val stump: Tree[Nothing] = Stump
  inline def create[T, U >: T](values: Seq[T])(using ord: Ordering[U]): Tree[T] = add(Stump, values)
  inline def apply[T, U >: T](values: T*)(using ord: Ordering[U]): Tree[T] = create(values)

  def add[T, U >: T](tree: Tree[T], values: Seq[T])(using ord: Ordering[U]): Tree[T] =
    values.foldLeft(tree)(add) // this is equivalent to `values.foldLeft[Tree[T]](tree) { (tree, t) => add(tree, t) }`

  def size[T](tree: Tree[T]): Int = tree match
    case Stump                      => 0
    case Leaf(value)                => 1
    case Branch(left, value, right) => size(left) + 1 + size(right)

  def toList[T](tree: Tree[T]): List[T] = tree match
    case Stump                      => Nil
    case Leaf(value)                => List(value)
    case Branch(left, value, right) => toList(left) ::: List(value) ::: toList(right)

  inline def iterator[T](tree: Tree[T]): Iterator[T] = toList(tree).iterator

  def toString[T](tree: Tree[T], acc: String = "", level: Int = 0)(using str: T => String): String =
    def append(valueStr: String) = s"$acc\n${"-" * level}$valueStr"
    tree match
      case Branch(left, value, right) =>
        val newAcc = toString(left, append(str(value)), level + 1)
        toString(right, newAcc, level + 1)
      case Leaf(value) =>
        append(str(value))
      case Stump =>
        append("X")

  def add[T, U >: T](tree: Tree[T], t: T)(using ord: Ordering[U]): Tree[T] = tree match
    case Branch(left, value, right) if ord.compare(t, value) < 0 =>
      val newLeft = add(left, t)
      Branch(newLeft, value, right)
    case Branch(left, value, right) if ord.compare(t, value) > 0 =>
      val newRight = add(right, t)
      Branch(left, value, newRight)
    case branch: Branch[T] =>
      branch // no changes
    case Leaf(value) if ord.compare(t, value) < 0 =>
      Branch(Leaf(t), value, Stump)
    case Leaf(value) if ord.compare(t, value) > 0 =>
      Branch(Stump, value, Leaf(t))
    case leaf: Leaf[T] =>
      leaf // no changes
    case Stump =>
      Leaf(t)

  def balance[T, U >: T](tree: Tree[T])(using ord: Ordering[U]): Tree[T] =
    def go(list: List[T], acc: Tree[T]): Tree[T] = list match
      case Nil => acc
      case head :: Nil => add(acc, head)
      case _ =>
        val mid = list.size / 2
        go(list.drop(mid + 1), go(list.take(mid), add(acc, list(mid))))

    go(tree.toList.sorted, Stump)