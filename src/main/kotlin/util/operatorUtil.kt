package util

import changeAPI.information.Operator

fun <T> sumBy(summator: (a: T, b: T) -> T): Operator<T> = object : Operator<T>(setOf(OPERATIONS.ADDITION)) {
    override fun add(a: T, b: T): T = summator(a, b)
}