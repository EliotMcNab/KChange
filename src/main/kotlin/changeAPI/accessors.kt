package changeAPI

import java.util.function.Predicate

interface EvolvedAccessors<T> {
    // finding
    fun findAll(comparator: Comparator<T>, vararg toFind: T): Map<T, List<Int>>
    fun findFirst(comparator: Comparator<T>, vararg toFind: T): List<Int>
    fun findLast(comparator: Comparator<T>, vararg toFind: T): List<Int>

    fun findAllMatches(filter: (T) -> Boolean): List<Int>
    fun findFirstMatch(filter: Predicate<T>): Int
    fun findLastMatch(filter: Predicate<T>): Int

    // getting
    fun get(index: Int): T
    fun getAt(vararg indexes: Int): List<T>

    fun getAllMatches(filter: (T) -> Boolean): List<T>
    fun getFirstMatch(filter: Predicate<T>): T
    fun getLastMatch(filter: Predicate<T>): T
}

interface PrimitiveAccessors<T>{
    // finding
    fun findAll(vararg toFind: T): Map<T, List<Int>>
    fun findFirst(vararg toFind: T): List<Int>
    fun findLast(vararg toFind: T): List<Int>
}