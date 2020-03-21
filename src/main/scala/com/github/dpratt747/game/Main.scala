package com.github.dpratt747.game

import cats.effect.concurrent.Ref
import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._
import com.github.dpratt747.deck.Deck.Card
import com.github.dpratt747.deck.Deck
import com.github.dpratt747.game.Game.loop
import com.github.dpratt747.players.Player
import com.typesafe.scalalogging.LazyLogging

object Main extends IOApp with LazyLogging {

  val program: IO[Player] = for {
    deckCards <- Ref.of[IO, List[Card]](Deck.cards)
    emptyPlayerCards <- Ref.of[IO, List[Card]](List.empty[Card])
    emptyDealerCards <- Ref.of[IO, List[Card]](List.empty[Card])
    sam = Player.Person("Sam", emptyPlayerCards)
    dealer = Player.Dealer(emptyDealerCards)
    _ <- dealer.dealNCards(2, deckCards, sam)
    _ <- dealer.dealNCards(2, deckCards, dealer)
    winner <- loop(sam, dealer, deckCards)
    winnerScore <- winner.calculateHandScore
    _ <- IO(logger.info(Console.GREEN + s"The winner is $winner, with the following score: $winnerScore" + Console.RESET))
  } yield winner

  def run(args: List[String]): IO[ExitCode] = program.as(ExitCode.Success)
}
