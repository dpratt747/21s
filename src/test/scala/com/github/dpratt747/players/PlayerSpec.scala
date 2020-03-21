package com.github.dpratt747.players

import cats.effect.IO
import cats.effect.concurrent.Ref
import com.github.dpratt747.deck.CheckImplicits
import com.github.dpratt747.deck.Deck._
import org.scalacheck.Gen
import org.scalacheck.Prop.forAll
import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.Checkers


class PlayerSpec extends AnyFunSpec with TypeCheckedTripleEquals with Matchers with Checkers with CheckImplicits {

	implicit override val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 1000, workers = 3)

  describe(Player.getClass.getSimpleName){

    it("should be able to add cards to a player"){
      check{ lc: List[Card] =>
        val action = for{
          emptyDeck <- Ref.of[IO, List[Card]](List.empty[Card])
          player = Player.Person("PersonForTest", emptyDeck)
          _ <- player.addCards(lc)
          cards <- player.cards.get
        } yield cards.size == lc.size
        action.unsafeRunSync()
      }
    }

    it("should be able to calculate the total value of a players cards"){
      check{ cards: List[Card] =>
        val person = Player.Person("PersonForTestingValue", Ref.unsafe[IO, List[Card]](cards))
        val dealer = Player.Dealer(Ref.unsafe[IO, List[Card]](cards))
        person.calculateHandScore.unsafeRunSync() == cards.map(_.rank.value).sum &&
        dealer.calculateHandScore.unsafeRunSync() == cards.map(_.rank.value).sum
      }
    }

    it("dealer should be able to dealN cards to players") {
      val intGen = Gen.choose(0, 52)
      check {
        forAll(intGen) { n: Int =>
          (for {
            tableCards <- Ref.of[IO, List[Card]](cards)
            player1 = Player.Person("PersonForTesting", Ref.unsafe[IO, List[Card]](List.empty[Card]))
            dealer = Player.Dealer(Ref.unsafe[IO, List[Card]](List.empty[Card]))
            playersCardsBeforeDeal <- player1.cards.get
            assertion1 = playersCardsBeforeDeal.isEmpty
            _ <- dealer.dealNCards(n, tableCards, player1)
            playersCardsAfterDeal <- player1.cards.get
            assertion2 = playersCardsAfterDeal.size == n
          } yield assertion1 && assertion2).unsafeRunSync()
        }
      }
    }

    it("dealer should be able to dealN cards to itself") {
      val intGen = Gen.choose(0, 52)
      check {
        forAll(intGen) { n: Int =>
          (for {
            tableCards <- Ref.of[IO, List[Card]](cards)
            dealer = Player.Dealer(Ref.unsafe[IO, List[Card]](List.empty[Card]))
            playersCardsBeforeDeal <- dealer.cards.get
            assertion1 = playersCardsBeforeDeal.isEmpty
            _ <- dealer.dealNCards(n, tableCards, dealer)
            playersCardsAfterDeal <- dealer.cards.get
            assertion2 = playersCardsAfterDeal.size == n
          } yield assertion1 && assertion2).unsafeRunSync()
        }
      }
    }

    it("should return the player name when toString is called"){
      val emptyCard = Ref.unsafe[IO, List[Card]](List.empty[Card])
      val player = Player.Person("PersonForTesting", emptyCard)
      val dealer = Player.Dealer(emptyCard)

      player.toString should ===("PersonForTesting")
      dealer.toString should ===("Dealer")
    }

	}
}
