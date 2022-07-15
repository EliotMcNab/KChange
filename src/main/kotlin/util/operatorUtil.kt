package util

import changeAPI.information.Operator
import changeAPI.information.Operator.OPERATIONS.*

fun <T> addBy(summator: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(ADDITION)) {
    override fun add(a: T, b: T): T = summator(a, b)
}

fun <T> subBy(subtractor: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(SUBTRACTION)) {
    override fun sub(a: T, b: T): T = subtractor(a, b)
}

fun <T> multBy(multiplier: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(MULTIPLICATION)) {
    override fun mult(a: T, b: T): T = multiplier(a, b)
}

fun <T> divBy(divider: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(DIVISION)) {
    override fun div(a: T, b: T): T = divider(a, b)
}

fun <T> mergeBy(merger: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(MERGING)) {
    override fun merge(a: T, b: T): T = merger(a, b)
}

fun <T> operateBy(
    add: Operator<T>? = null,
    sub: Operator<T>? = null,
    mult: Operator<T>? = null,
    div: Operator<T>? = null,
    merge: Operator<T>? = null,
): Operator<T> {
    require(add == null || add.supports(ADDITION))
    require(sub == null || sub.supports(SUBTRACTION))
    require(mult == null || mult.supports(MULTIPLICATION))
    require(div == null || div.supports(DIVISION))
    require(merge == null || merge.supports(MERGING))

    val supported = buildSet {
        if (add != null) add(ADDITION)
        if (sub != null) add(SUBTRACTION)
        if (mult != null) add(MULTIPLICATION)
        if (div != null) add(DIVISION)
        if (merge != null) add(MERGING)
    }

    return object : Operator<T>(supported) {
        override fun add(a: T, b: T): T = add?.add(a, b) ?: throw UnsupportedOperationException()
        override fun sub(a: T, b: T): T = sub?.sub(a, b) ?: throw UnsupportedOperationException()
        override fun mult(a: T, b: T): T = mult?.mult(a, b) ?: throw UnsupportedOperationException()
        override fun div(a: T, b: T): T = div?.div(a, b) ?: throw UnsupportedOperationException()
        override fun merge(a: T, b: T): T = merge?.merge(a, b) ?: throw UnsupportedOperationException()
    }
}