package com.bkowalik

import org.scalatest.{FlatSpec, Matchers}

class Slide01 extends FlatSpec with Matchers {

  it should "allow to get property" in {
    // given
    import shapeless.lens

    val commentUserOne = User("Jan", "Kowalski", "jankowalski@onet.pl")
    val commentUserTwo = User("Jan", "Nowak", "jannowak@wp.pl")
    val user = User("Bartosz", "Kowalik", "bkowalik@virtuslab.com")
    val engine = Engine(1999.0, 160, FuelType.Petrol)
    val car = Car(engine, BodyType.Hatchback, 2010)
    val price = Price(35000, Currency.PLN)
    val comments = Seq(
      Comment(commentUserOne, "Foo", Grade.Positive),
      Comment(commentUserTwo, "Bar", Grade.Positive)
    )
    val advertisement = Advertisement(user, car, "Super samochód!", price, comments)

    val commentLens = lens[Advertisement].comments

    // when
    val commentsFromAdvertisement = commentLens.get(advertisement)

    // then
    commentsFromAdvertisement should contain theSameElementsInOrderAs(comments)
  }

  it should "allow to set property" in {
    // given
    import shapeless.lens

    val commentUserOne = User("Jan", "Kowalski", "jankowalski@onet.pl")
    val commentUserTwo = User("Jan", "Nowak", "jannowak@wp.pl")
    val user = User("Bartosz", "Kowalik", "bkowalik@virtuslab.com")
    val engine = Engine(1999.0, 160, FuelType.Petrol)
    val car = Car(engine, BodyType.Hatchback, 2010)
    val price = Price(35000, Currency.PLN)
    val comments = Seq(
      Comment(commentUserOne, "Foo", Grade.Positive),
      Comment(commentUserTwo, "Bar", Grade.Positive)
    )
    val advertisement = Advertisement(user, car, "Super samochód!", price, comments)

    val carEngineLens = lens[Advertisement].car.engine
    val newEngine = Engine(2499.0, 200, FuelType.Petrol)

    // when
    val advertisementWithNewEngine = carEngineLens.set(advertisement)(newEngine)

    // then
    advertisementWithNewEngine.car.engine shouldBe newEngine
  }

  it should "allow to modify property" in {
    // given
    import shapeless.lens
    import shapeless.test.typed

    val commentUserOne = User("Jan", "Kowalski", "jankowalski@onet.pl")
    val commentUserTwo = User("Jan", "Nowak", "jannowak@wp.pl")
    val user = User("Bartosz", "Kowalik", "bkowalik@virtuslab.com")
    val engine = Engine(1999.0, 160, FuelType.Petrol)
    val car = Car(engine, BodyType.Hatchback, 2010)
    val price = Price(35000, Currency.PLN)
    val comments = Seq(
      Comment(commentUserOne, "Foo", Grade.Positive),
      Comment(commentUserTwo, "Bar", Grade.Positive)
    )
    val advertisement = Advertisement(user, car, "Super samochód!", price, comments)

    val priceCurrencyLens = lens[Advertisement].price.currency

    // when
    val advertisementWithModifiedPrice = priceCurrencyLens.modify(advertisement)(_ => Currency.USD)

    // then
    typed[Advertisement](advertisementWithModifiedPrice)
    advertisementWithModifiedPrice.price.currency shouldBe Currency.USD
  }

  it should "allow to combine lenses to get property" in {
    // given
    import shapeless.lens
    import shapeless.test.typed

    val commentUserOne = User("Jan", "Kowalski", "jankowalski@onet.pl")
    val commentUserTwo = User("Jan", "Nowak", "jannowak@wp.pl")
    val user = User("Bartosz", "Kowalik", "bkowalik@virtuslab.com")
    val engine = Engine(1999.0, 160, FuelType.Petrol)
    val car = Car(engine, BodyType.Hatchback, 2010)
    val price = Price(35000, Currency.PLN)
    val comments = Seq(
      Comment(commentUserOne, "Foo", Grade.Positive),
      Comment(commentUserTwo, "Bar", Grade.Positive)
    )
    val advertisement = Advertisement(user, car, "Super samochód!", price, comments)

    val currencyLens = lens[Advertisement].price.currency
    val engineFuelTypeLens = lens[Advertisement].car.engine.fuel
    val userEmailLens = lens[Advertisement].user.email

    val combinedLens = currencyLens ~ engineFuelTypeLens ~ userEmailLens

    // when
    val currencyFuelTypeEmail = combinedLens.get(advertisement)

    // then
    typed[(Currency, FuelType, String)](currencyFuelTypeEmail)
    currencyFuelTypeEmail._1 shouldBe advertisement.price.currency
    currencyFuelTypeEmail._2 shouldBe advertisement.car.engine.fuel
    currencyFuelTypeEmail._3 shouldBe advertisement.user.email
  }

  it should "allow to combine lenses to set property" in {
    // given
    import shapeless.lens
    import shapeless.test.typed

    val commentUserOne = User("Jan", "Kowalski", "jankowalski@onet.pl")
    val commentUserTwo = User("Jan", "Nowak", "jannowak@wp.pl")
    val user = User("Bartosz", "Kowalik", "bkowalik@virtuslab.com")
    val engine = Engine(1999.0, 160, FuelType.Petrol)
    val car = Car(engine, BodyType.Hatchback, 2010)
    val price = Price(35000, Currency.PLN)
    val comments = Seq(
      Comment(commentUserOne, "Foo", Grade.Positive),
      Comment(commentUserTwo, "Bar", Grade.Positive)
    )
    val advertisement = Advertisement(user, car, "Super samochód!", price, comments)

    val currencyLens = lens[Advertisement].price.currency
    val engineFuelTypeLens = lens[Advertisement].car.engine.fuel
    val userEmailLens = lens[Advertisement].user.email

    val combinedLens = currencyLens ~ engineFuelTypeLens ~ userEmailLens

    // when
    val editedAdvertisement = combinedLens.set(advertisement)(Currency.USD, FuelType.Diesel, "abc@def.pl")

    // then
    typed[Advertisement](editedAdvertisement)
    editedAdvertisement.price.currency shouldBe Currency.USD
    editedAdvertisement.car.engine.fuel shouldBe FuelType.Diesel
    editedAdvertisement.user.email shouldBe "abc@def.pl"
  }

  it should "allow to combine lenses to modify property" in {
    // given
    import shapeless.lens
    import shapeless.test.typed

    val commentUserOne = User("Jan", "Kowalski", "jankowalski@onet.pl")
    val commentUserTwo = User("Jan", "Nowak", "jannowak@wp.pl")
    val user = User("Bartosz", "Kowalik", "bkowalik@virtuslab.com")
    val engine = Engine(1999.0, 160, FuelType.Petrol)
    val car = Car(engine, BodyType.Hatchback, 2010)
    val price = Price(35000, Currency.PLN)
    val comments = Seq(
      Comment(commentUserOne, "Foo", Grade.Positive),
      Comment(commentUserTwo, "Bar", Grade.Positive)
    )
    val advertisement = Advertisement(user, car, "Super samochód!", price, comments)

    val currencyLens = lens[Advertisement].price.currency
    val engineFuelTypeLens = lens[Advertisement].car.engine.fuel
    val userEmailLens = lens[Advertisement].user.email

    val combinedLens = currencyLens ~ engineFuelTypeLens ~ userEmailLens

    // when
    val modifiedAdvertisement = combinedLens.modify(advertisement) {
      case (currency, fuelType, email) =>
        (Currency.EUR, FuelType.Diesel, email.toUpperCase)
    }

    // then
    typed[Advertisement](modifiedAdvertisement)
    modifiedAdvertisement.price.currency shouldBe Currency.EUR
    modifiedAdvertisement.car.engine.fuel shouldBe FuelType.Diesel
    modifiedAdvertisement.user.email shouldBe "bkowalik@virtuslab.com".toUpperCase()
  }

}
