package com.bkowalik

case class User(name: String, surname: String, email: String)

sealed trait Currency
object Currency {
  case object EUR extends Currency
  case object PLN extends Currency
  case object USD extends Currency
}

case class Price(amount: BigDecimal, currency: Currency)

case class Advertisement(user: User, car: Car, details: String, price: Price, comments: Seq[Comment])

case class Comment(user: User, content: String, grade: Grade)

sealed trait Grade
object Grade {
  case object Positive extends Grade
  case object Negative extends Grade
  case object Neutral extends Grade
}

sealed trait FuelType
object FuelType {
  case object Diesel extends FuelType
  case object Petrol extends FuelType
}

sealed trait BodyType
object BodyType {
  case object Sedan extends BodyType
  case object Hatchback extends BodyType
  case object SUV extends BodyType
}

case class Engine(capacity: BigDecimal, horsePower: Int, fuel: FuelType)

case class Car(engine: Engine, body: BodyType, productionYear: Int)