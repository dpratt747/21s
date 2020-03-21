package com.github.dpratt747.players

import cats.effect._
import cats.effect.concurrent.Ref
import cats.syntax.all._
import com.github.dpratt747.deck.Deck.Card
import com.typesafe.scalalogging.LazyLogging

sealed trait Player extends Product with Serializable {
	val cards: Ref[IO, List[Card]]

  final def addCards(cardsToAdd: List[Card]): IO[Unit] = cards.update(_ ++ cardsToAdd)

  final def calculateHandScore: IO[Int] = cards.get.map(_.map(_.rank.value).sum)
}

object Player extends LazyLogging {

  final case class Person(value: String, cards: Ref[IO, List[Card]]) extends Player {
    override def toString: String = value
	}

	final case class Dealer(cards: Ref[IO, List[Card]]) extends Player {
    private def takeNCards(n : Int, item: Ref[IO, List[Card]]): IO[List[Card]] = item.get.map(_.take(n))
    private def dropNCards(n : Int, item: Ref[IO, List[Card]]): IO[List[Card]] = item.getAndUpdate(_.drop(n))

    def dealNCards(n: Int, tableCards: Ref[IO, List[Card]],  player: Player): IO[Unit] =
      for {
        cards <- takeNCards(n, tableCards) <* dropNCards(n, tableCards)
        _ = logger.trace(s"$player taking $n cards from deck")
        _ <- player.addCards(cards)
        hand <- player.cards.get
        total <- player.calculateHandScore
        _ = logger.info(s"$player currently has $hand in hand. Total: ${Console.MAGENTA} $total ${Console.RESET}")
      } yield ()

    override def toString: String = this.getClass.getSimpleName
  }
}
