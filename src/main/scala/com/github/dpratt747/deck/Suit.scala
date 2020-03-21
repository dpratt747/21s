package com.github.dpratt747.deck


sealed trait Suit extends Product with Serializable

object Suit {
	case object Spade extends Suit
	case object Heart extends Suit
	case object Diamond extends Suit
	case object Clubs extends Suit
}

