package com.bkowalik

import org.scalatest.{FlatSpec, Matchers}
import shapeless._
import test._

import scala.language.higherKinds

class Slide03 extends FlatSpec with Matchers {

  it should "allow to use HMap" in {
    // given
    class MapEntry[K, V]
    implicit val stringToDouble = new MapEntry[String, Double]

    // when
    val hmap = HMap[MapEntry]("1" -> 1.0, "2" -> 2.0)

    // then
    hmap.get("2") shouldBe Some(2.0)
    hmap.get("nooone") shouldBe None
  }

  it should "do magic trick" in {
    // given
    class MapEntry[K, V]
    implicit def fooBar[T, M[_]] = new MapEntry[Int, M[T]]
    implicit val asd = new MapEntry[Int, Id[String]]

    // when
    val monadicMap = HMap[MapEntry](1 -> Option(1), 2 -> List(2), 33 -> "Sd")

    // then
    val result = monadicMap.get(1)
    result shouldBe Some(Some(1))
    monadicMap.get(33) shouldBe Some("Sd")
  }

}
