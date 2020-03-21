package com.github.dpratt747.game

import cats.effect.IO
import cats.effect.concurrent.Ref
import com.github.dpratt747.deck.Deck.Card
import com.github.dpratt747.deck.{Deck, Rank, Suit}
import com.github.dpratt747.game.Game.loop
import com.github.dpratt747.players.Player
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers

class GameSpec extends AnyFunSpec with TypeCheckedTripleEquals with Matchers {

  describe(Game.getClass.getSimpleName){

		it("the winner should be a person if both the dealer and player have 21"){
      val cards21 = List(Card(Suit.Clubs, Rank.Ace), Card(Suit.Clubs, Rank.Jack))

      val player = Player.Person("Person1", Ref.unsafe[IO, List[Card]](cards21))
      val dealer = Player.Dealer(Ref.unsafe[IO, List[Card]](cards21))

      (for {
        player <- loop(player, dealer, Ref.unsafe[IO, List[Card]] (Deck.cards))
      } yield player.toString should ===("Person1")).unsafeRunSync()
    }

		it("the player should win if the dealer has a score over 21 and the player has a score less than 21"){
      val cards20 = List(Card(Suit.Clubs, Rank.Ace), Card(Suit.Clubs, Rank.Jack))
      val cards31 = List(Card(Suit.Clubs, Rank.Ace), Card(Suit.Clubs, Rank.Jack), Card(Suit.Clubs, Rank.Queen))

      val player = Player.Person("Person1", Ref.unsafe[IO, List[Card]](cards20))
      val dealer = Player.Dealer(Ref.unsafe[IO, List[Card]](cards31))

      (for {
        player <- loop(player, dealer, Ref.unsafe[IO, List[Card]] (Deck.cards))
      } yield player.toString should ===("Person1")).unsafeRunSync()
    }

    it("the player should not take anymore cards when their score is 17"){
      val cards17 = List(Card(Suit.Clubs, Rank.Ace), Card(Suit.Clubs, Rank.Six))

      val player = Player.Person("Person2", Ref.unsafe[IO, List[Card]](cards17))
      val dealer = Player.Dealer(Ref.unsafe[IO, List[Card]](List.empty[Card]))

      (for {
        _ <- loop(player, dealer, Ref.unsafe[IO, List[Card]] (Deck.cards))
      } yield()).unsafeRunSync()

      player.calculateHandScore.unsafeRunSync() should ===(17)
    }

  }
}
