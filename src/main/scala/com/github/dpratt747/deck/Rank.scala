package com.github.dpratt747.deck

sealed trait Rank extends Product with Serializable {
  val value: Int
}

object Rank {
	case object Ace extends Rank {
    val value = 11
  }
	case object Two extends Rank {
    val value = 2
  }
	case object Three extends Rank {
    val value = 3
  }
	case object Four extends Rank {
    val value = 4
  }
	case object Five extends Rank {
    val value = 5
  }
	case object Six extends Rank {
    val value = 6
  }
	case object Seven extends Rank {
    val value = 7
  }
	case object Eight extends Rank {
    val value = 8
  }
	case object Nine extends Rank {
    val value = 9
  }
	case object Ten extends Rank {
    val value = 10
  }
	case object Jack extends Rank {
    val value = 10
  }
	case object Queen extends Rank {
    val value = 10
  }
	case object King extends Rank {
    val value = 10
  }
}
