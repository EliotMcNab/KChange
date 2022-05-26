package util

import changeAPI.information.Operator

fun <T> addBy(summator: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(OPERATIONS.ADDITION)) {
    override fun add(a: T, b: T): T = summator(a, b)
}

fun <T> subBy(subtractor: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(OPERATIONS.SUBTRACTION)) {
    override fun sub(a: T, b: T): T = subtractor(a, b)
}

fun <T> multBy(multiplier: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(OPERATIONS.MULTIPLICATION)) {
    override fun mult(a: T, b: T): T = multiplier(a, b)
}

fun <T> divBy(divider: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(OPERATIONS.DIVISION)) {
    override fun div(a: T, b: T): T = divider(a, b)
}

fun <T> mergeBy(merger: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(OPERATIONS.MERGING)) {
    override fun merge(a: T, b: T): T = merger(a, b)
}

fun <T> operateBy(
    add: Operator<T>? = null,
    sub: Operator<T>? = null,
    mult: Operator<T>? = null,
    div: Operator<T>? = null,
    merge: Operator<T>? = null,
): Operator<T> {
    require(add == null || add.supports(Operator.OPERATIONS.ADDITION))
    require(sub == null || sub.supports(Operator.OPERATIONS.SUBTRACTION))
    require(mult == null || mult.supports(Operator.OPERATIONS.MULTIPLICATION))
    require(div == null || div.supports(Operator.OPERATIONS.DIVISION))
    require(merge == null || merge.supports(Operator.OPERATIONS.MERGING))

    val supported = mutableSetOf<Operator.OPERATIONS>()

    when {
        add != null -> supported.add(Operator.OPERATIONS.ADDITION)
        sub != null -> supported.add(Operator.OPERATIONS.SUBTRACTION)
        mult != null -> supported.add(Operator.OPERATIONS.MULTIPLICATION)
        div != null -> supported.add(Operator.OPERATIONS.DIVISION)
        merge != null -> supported.add(Operator.OPERATIONS.MERGING)
    }

    return object : Operator<T>(supported) {
        override fun add(a: T, b: T): T = add?.add(a, b) ?: throw UnsupportedOperationException()
        override fun sub(a: T, b: T): T = sub?.sub(a, b) ?: throw UnsupportedOperationException()
        override fun mult(a: T, b: T): T = mult?.mult(a, b) ?: throw UnsupportedOperationException()
        override fun div(a: T, b: T): T = div?.div(a, b) ?: throw UnsupportedOperationException()
        override fun merge(a: T, b: T): T = merge?.merge(a, b) ?: throw UnsupportedOperationException()
    }
}