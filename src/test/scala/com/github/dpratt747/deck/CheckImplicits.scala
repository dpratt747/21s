package com.github.dpratt747.deck

import com.github.dpratt747.deck.Deck.Card

import org.scalacheck._
import org.scalacheck.Arbitrary
import org.scalacheck.ScalacheckShapeless._ /* derive arbitrary using shapeless for a given ADT */

trait CheckImplicits {

  implicit val arbCard: Arbitrary[Card] = Arbitrary {
    implicitly[Arbitrary[Suit]]
    implicitly[Arbitrary[Rank]]
    Gen.resultOf(Card)
  }

  implicit val arbListCards: Arbitrary[List[Card]] = Arbitrary(Gen.atLeastOne[Card](Deck.cards).map(_.toList))
}
