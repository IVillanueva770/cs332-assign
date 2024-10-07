package patmat

import common._

/**
 * Assignment 4: Huffman coding
 *
 */
object Huffman {

  /**
   * A huffman code is represented by a binary tree.
   *
   * Every `Leaf` node of the tree represents one character of the alphabet that the tree can encode.
   * The weight of a `Leaf` is the frequency of appearance of the character.
   *
   * The branches of the huffman tree, the `Fork` nodes, represent a set containing all the characters
   * present in the leaves below it. The weight of a `Fork` node is the sum of the weights of these
   * leaves.
   */
  abstract class CodeTree
  case class Fork(left: CodeTree, right: CodeTree, chars: List[Char], weight: Int) extends CodeTree
  case class Leaf(char: Char, weight: Int) extends CodeTree

  // Part 1: Basics

  def weight(tree: CodeTree): Int = tree match {
    case Leaf(_, w) => w
    case Fork(_, _, _, w) => w
  }

  def chars(tree: CodeTree): List[Char] = tree match {
    case Leaf(c, _) => List(c)
    case Fork(_, _, chs, _) => chs
  }

  def makeCodeTree(left: CodeTree, right: CodeTree) =
    Fork(left, right, chars(left) ::: chars(right), weight(left) + weight(right))

  // Part 2: Generating Huffman trees

  /**
   * In this assignment, we are working with lists of characters. This function allows
   * you to easily create a character list from a given string.
   */
  def string2Chars(str: String): List[Char] = str.toList

  /**
   * This function computes for each unique character in the list `chars` the number of
   * times it occurs. For example, the invocation
   *
   *   times(List('a', 'b', 'a'))
   *
   * should return the following (the order of the resulting list is not important):
   *
   *   List(('a', 2), ('b', 1))
   */
  def times(chars: List[Char]): List[(Char, Int)] = {
    chars.groupBy(identity).view.mapValues(_.size).toList
  }

  /**
   * Returns a list of `Leaf` nodes for a given frequency table `freqs`.
   *
   * The returned list should be ordered by ascending weights (i.e. the
   * head of the list should have the smallest weight), where the weight
   * of a leaf is the frequency of the character.
   */
  def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] = {
    freqs.sortBy(_._2).map { case (char, freq) => Leaf(char, freq) }
  }

  /**
   * Checks whether the list `trees` contains only one single code tree.
   */
  def singleton(trees: List[CodeTree]): Boolean = trees.size == 1

  /**
   * The parameter `trees` of this function is a list of code trees ordered
   * by ascending weights.
   *
   * This function takes the first two elements of the list `trees` and combines
   * them into a single `Fork` node. This node is then added back into the
   * remaining elements of `trees` at a position such that the ordering by weights
   * is preserved.
   */
  def combine(trees: List[CodeTree]): List[CodeTree] = {
    if (trees.length < 2) trees
    else {
      val (left :: right :: rest) = trees.sortBy(weight)
      val combinedTree = Fork(left, right, chars(left) ::: chars(right), weight(left) + weight(right))
      (combinedTree :: rest).sortBy(weight)
    }
  }

  /**
   * This function will be called in the following way:
   *
   *   until(singleton, combine)(trees)
   *
   * where `trees` is of type `List[CodeTree]`, `singleton` and `combine` refer to
   * the two functions defined above.
   */
  def until(singleton: List[CodeTree] => Boolean, combine: List[CodeTree] => List[CodeTree])(trees: List[CodeTree]): CodeTree = {
    if (singleton(trees)) trees.head
    else until(singleton, combine)(combine(trees))
  }

  /**
   * This function creates a code tree which is optimal to encode the text `chars`.
   *
   * The parameter `chars` is an arbitrary text. This function extracts the character
   * frequencies from that text and creates a code tree based on them.
   */
  def createCodeTree(chars: List[Char]): CodeTree = {
    val freqs = times(chars)
    val orderedLeaves = makeOrderedLeafList(freqs)
    until(singleton, combine)(orderedLeaves)
  }

  // Part 3: Decoding

  type Bit = Int

  /**
   * This function decodes the bit sequence `bits` using the code tree `tree` and returns
   * the resulting list of characters.
   */
  def decode(tree: CodeTree, bits: List[Bit]): List[Char] = {
    def decodeAcc(currentTree: CodeTree, bits: List[Bit]): List[Char] = currentTree match {
      case Leaf(char, _) =>
        if (bits.isEmpty) List(char)
        else char :: decodeAcc(tree, bits)
      case Fork(left, right, _, _) => bits match {
        case 0 :: tail => decodeAcc(left, tail)
        case 1 :: tail => decodeAcc(right, tail)
      }
    }
    decodeAcc(tree, bits)
  }

  /**
   * Write a function that returns the decoded secret
   */
  def decodedSecret: List[Char] = decode(frenchCode, secret)

  // Part 4a: Encoding using Huffman tree

  /**
   * This function encodes `text` using the code tree `tree`
   * into a sequence of bits.
   */
  def encode(tree: CodeTree)(text: List[Char]): List[Bit] = {
    def encodeChar(char: Char, tree: CodeTree): List[Bit] = tree match {
      case Leaf(_, _) => List()
      case Fork(left, right, _, _) =>
        if (chars(left).contains(char)) 0 :: encodeChar(char, left)
        else 1 :: encodeChar(char, right)
    }
    text.flatMap(encodeChar(_, tree))
  }

  // Part 4b: Encoding using code table

  type CodeTable = List[(Char, List[Bit])]

  /**
   * This function returns the bit sequence that represents the character `char` in
   * the code table `table`.
   */
  def codeBits(table: CodeTable)(char: Char): List[Bit] = {
    table.find(_._1 == char).map(_._2).getOrElse(List())
  }

  /**
   * Given a code tree, create a code table which contains, for every character in the
   * code tree, the sequence of bits representing that character.
   */
  def convert(tree: CodeTree): CodeTable = {
    def convertAcc(currentTree: CodeTree, acc: List[Bit]): CodeTable = currentTree match {
      case Leaf(char, _) => List((char, acc))
      case Fork(left, right, _, _) =>
        mergeCodeTables(convertAcc(left, acc :+ 0), convertAcc(right, acc :+ 1))
    }
    convertAcc(tree, List())
  }

  /**
   * This function takes two code tables and merges them into one. Depending on how you
   * use it in the `convert` method above, this merge method might also do some transformations
   * on the two parameter code tables.
   */
  def mergeCodeTables(a: CodeTable, b: CodeTable): CodeTable = a ::: b

  /**
   * This function encodes `text` according to the code tree `tree`.
   *
   * To speed up the encoding process, it first converts the code tree to a code table
   * and then uses it to perform the actual encoding.
   */
  def quickEncode(tree: CodeTree)(text: List[Char]): List[Bit] = {
    val table = convert(tree)
    text.flatMap(codeBits(table))
  }
}
