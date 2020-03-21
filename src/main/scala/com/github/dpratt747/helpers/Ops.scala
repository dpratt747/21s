package com.github.dpratt747.helpers

import com.github.dpratt747.deck.Rank._
import com.github.dpratt747.deck.Suit.{Clubs, Diamond, Heart, Spade}
import com.github.dpratt747.deck.{Rank, Suit}

object Ops {

  implicit class StringOps(string: String) {

    val toSuit: Option[Suit] =
      string.toLowerCase() match {
        case "spade" => Some(Spade)
        case "heart" => Some(Heart)
        case "diamond" => Some(Diamond)
        case "clubs" => Some(Clubs)
        case _ => None
      }

    val toRank: Option[Rank] =
      string.toLowerCase() match {
        case "ace" => Some(Ace)
        case "two" => Some(Two)
        case "three" => Some(Three)
        case "four" => Some(Four)
        case "five" => Some(Five)
        case "six" => Some(Six)
        case "seven" => Some(Seven)
        case "eight" => Some(Eight)
        case "nine" => Some(Nine)
        case "ten" => Some(Ten)
        case "jack" => Some(Jack)
        case "queen" => Some(Queen)
        case "king" => Some(King)
        case _ => None
      }
  }
}
