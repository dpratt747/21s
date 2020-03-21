package com.github.dpratt747.game

import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.syntax.all._
import com.github.dpratt747.deck.Deck.Card
import com.github.dpratt747.players.Player.{Dealer, Person}
import com.github.dpratt747.players.Player

object Game {

  def loop(player: Person, dealer: Dealer, cards: Ref[IO, List[Card]]): IO[Player] =
    for {
      playerScore <- player.calculateHandScore
      dealerScore <- dealer.calculateHandScore
      check <- IO.suspend{
        if(playerScore == 21) IO(player)
        else if(dealerScore == 21) IO(dealer)
        else if(playerScore > 21) IO(dealer)
        else if(dealerScore > 21) IO(player)
        else if(playerScore >= 17 && !(dealerScore > playerScore)) dealer.dealNCards(1, cards, dealer) *> loop(player, dealer, cards)
        else if(dealerScore > playerScore && dealerScore <= 21 && playerScore >= 17) IO(dealer)
        else dealer.dealNCards(1, cards, player) *> loop(player, dealer, cards)
      }
    } yield check

}
