package com.github.dpratt747.helpers

import shapeless._

class CoProductHelper[A] {
    def apply[C <: Coproduct, K <: HList]()(implicit
        labelledGeneric: LabelledGeneric.Aux[A, C],
        keys: ops.union.Keys.Aux[C, K],
        toList: ops.hlist.ToTraversable.Aux[K, List, Symbol]
    ): List[String] = toList(keys()).map(_.name)
}

object CoProductHelper{
	def values[A] = new CoProductHelper[A]()
}