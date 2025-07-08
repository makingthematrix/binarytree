package binarytree

object Cities:
  val list: List[City] = List(
    City("Beijing", 20462610),
    City("New Delhi", 32941000),
    City("Jakarta", 11074000),
    City("Moscow", 12506000),
    City("Brasilia", 3055149),
    City("Washington DC", 689545),
    City("Dhaka", 21741000),
    City("Islamabad", 1095064),
    City("Tokyo", 37400068),
    City("Manila", 13923452),
    City("Cairo", 21750020),
    City("Mexico City", 21804515),
    City("Addis Ababa", 5005524),
    City("Berlin", 3677472),
    City("London", 9002488),
    City("Paris", 11142000),
    City("Ottawa", 1017449),
    City("Canberra", 431380),
    City("Pretoria", 741651),
    City("Riyadh", 7231447)
  )

  val map: Map[String, City] = list.map(c => c.name -> c).toMap
  def get(name: String): Option[City] = map.get(name)
  
