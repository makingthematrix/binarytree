package binarytree

final case class City(name: String, population: Int)

object City:
  def apply(name: String): City = City(name, 0)
  def compareByName(c1: City, c2: City): Int = c1.name.compareTo(c2.name)
  def compareByPop(c1: City, c2: City): Int = c1.population - c2.population