package com.github.dpratt747.deck


import com.github.dpratt747.helpers.CoProductHelper._
import cats.implicits._
import com.github.dpratt747.helpers.Ops._

object Deck{

	final case class Card(suit: Suit, rank: Rank)

	private lazy val suits = values[Suit]().map(_.toSuit).sequence.getOrElse(throw new RuntimeException("No suits"))
	private lazy val ranks = values[Rank]().map(_.toRank).sequence.getOrElse(throw new RuntimeException("No ranks"))

	lazy val cards: List[Card] = scala.util.Random.shuffle(suits.flatMap(suit => ranks.map(rank => Card(suit, rank))))

}