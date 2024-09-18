

package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))

  // Pruebas de las funciones
  val s1 = singletonSet(1)
  val s2 = singletonSet(2)
  val s3 = singletonSet(3)

  println(contains(s1, 1)) // true
  println(contains(s1, 2)) // false

  val s = union(s1, s2)
  println(contains(s, 1)) // true
  println(contains(s, 2)) // true
  println(contains(s, 3)) // false

  val i = intersect(s1, s2)
  println(contains(i, 1)) // false
  println(contains(i, 2)) // false

  val d = diff(s1, s2)
  println(contains(d, 1)) // true
  println(contains(d, 2)) // false

  val f = filter(s, x => x % 2 == 0)
  println(contains(f, 1)) // false
  println(contains(f, 2)) // true

  println(forall(s, x => x > 0)) // true
  println(exists(s, x => x == 2)) // true

  val m = map(s, x => x * x)
  println(contains(m, 1)) // true
  println(contains(m, 4)) // true
  println(contains(m, 9)) // false

  printSet(s) // {1,2}

}
