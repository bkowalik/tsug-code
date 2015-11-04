package com.bkowalik

import shapeless._
import test._
import org.scalatest.{Matchers, FlatSpec}

class Slide08 extends FlatSpec with Matchers {

  it should "take conjuction type" in {
    // given
    trait Foo
    trait Bar
    class Bazz extends Foo with Bar

    val bazzInstance = new Bazz

    def foo[T <: Foo with Bar](a: T): Boolean = true

    // when
    val result = foo(bazzInstance)

    // then
    result shouldBe true
  }

  it should "tell that Int is true" in {
    // given
    def foo[T: (Int |∨| String)#λ](a: T): Boolean = a match {
      case a: Int => true
      case a: String => false
    }

    // when
    val result = foo(1)

    // then
    result shouldBe true
  }

  it should "work with custom types" in {
    // given
    class Foo
    class Bar
    def foo[T: (Foo |∨| Bar)#λ](a: T): Boolean = a match {
      case a: Foo => true
      case a: Bar => false
    }

    val fooInstance = new Foo
    val barInstance = new Bar

    // when
    val resultFoo = foo(fooInstance)
    val resultBar = foo(barInstance)

    // then
    resultFoo shouldBe true
    resultBar shouldBe false
    illTyped(
      """
        foo("a")
      """)
  }
}
