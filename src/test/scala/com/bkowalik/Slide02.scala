package com.bkowalik

import org.scalatest.{Matchers, FlatSpec}
import shapeless._
import shapeless.PolyDefns.{~>>, ~>}
import shapeless.test._

class Slide02 extends FlatSpec with Matchers {

  it should "map ordinary list" in {
    // given
    val list = List("a", "aa", "aaa", "aaaa")

    object size extends (String => Int) {
      def apply(s: String): Int = s.length
    }

    // when
    val mappedList = list.map(size)

    // then
    mappedList should contain theSameElementsInOrderAs List(1, 2, 3, 4)
  }


  it should "get size of normal HList" in {
    // given
    val list: ::[Int, ::[String, ::[List[String], HNil]]] = 1 :: "foo" :: List("abc", "def", "ghi") :: HNil
    (1, ("", ET))
    object size extends Poly1 {
      implicit def caseInt = at[Int](x => 1)
      implicit def caseString = at[String](s => s.length)
      implicit def caseList[T]
      (implicit innerSize: Case.Aux[T, Int]) = at[List[T]] { list =>
        list.foldLeft(0) { case (acc, innerType) =>
          acc + size(innerType)
        }
      }
    }

    // when
    val mappedList = list.map(size)

    // then
    typed[Int :: Int :: Int :: HNil](mappedList)
  }

}
