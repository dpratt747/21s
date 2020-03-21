package com.github.dpratt747.deck

import com.github.dpratt747.deck.Deck._

import org.scalactic.TypeCheckedTripleEquals
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.Checkers


class DeckSpec extends AnyFunSpec with TypeCheckedTripleEquals with Matchers with Checkers with CheckImplicits {

	implicit override val generatorDrivenConfig: PropertyCheckConfiguration = PropertyCheckConfiguration(minSuccessful = 1000, workers = 3)
	
	describe(Deck.getClass.getSimpleName){

		it("should generate 52 cards"){
			cards.size should === (52)
		}
		
		it("should contain all the cards"){
			check{ c: Card =>
				cards.contains(c)
			}
		}

	}
}
