package patmat

import org.scalatest.FunSuite

class HuffmanSuite extends FunSuite {

  trait TestTrees {
    val t1 = Leaf('a', 2)
    val t2 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
  }

  test("weight of a tree") {
    new TestTrees {
      assert(Huffman.weight(t1) === 2)
      assert(Huffman.weight(t2) === 5)
    }
  }

  test("chars of a tree") {
    new TestTrees {
      assert(Huffman.chars(t1) === List('a'))
      assert(Huffman.chars(t2) === List('a', 'b'))
    }
  }

  test("times of a list of chars") {
    assert(Huffman.times(List('a', 'b', 'a')) === List(('a', 2), ('b', 1)))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(Huffman.makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }

  test("singleton check") {
    assert(Huffman.singleton(List(Leaf('a', 2))) === true)
    assert(Huffman.singleton(List(Leaf('a', 2), Leaf('b', 3))) === false)
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(Huffman.combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(Huffman.decode(t2, Huffman.encode(t2)("ab".toList)) === "ab".toList)
    }
  }

  test("quickEncode should give the same result as encode") {
    new TestTrees {
      assert(Huffman.quickEncode(t2)("ab".toList) === Huffman.encode(t2)("ab".toList))
    }
  }
}
