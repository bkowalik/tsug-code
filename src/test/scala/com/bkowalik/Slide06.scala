package com.bkowalik

import org.scalatest.{Matchers, FlatSpec}
import shapeless._
import test._

class Slide06 extends FlatSpec with Matchers {

  it should "work with products" in {
    // given
    val genericUser = Generic[User]
    val userInstance = User("Jan", "Kowalski", "jan.kowalski@foo.pl")

    // when
    val userRepr = genericUser.to(userInstance)
    val anotherUser = genericUser.from("Stefan" :: "Stefanowski" :: "stefcik@buziaczek.pl" :: HNil)

    // then
    typed[String :: String :: String :: HNil](userRepr)
    anotherUser shouldBe User("Stefan", "Stefanowski", "stefcik@buziaczek.pl")
  }

}
