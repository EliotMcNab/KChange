package changeAPI

import java.util.function.Predicate

interface ArrayOperations<T>{
    // add
    fun add(element: T): Change<T>
    fun addAll(vararg elements: T): Change<T>
    // remove
    fun removeAll(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun removeFirst(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun removeLast(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun removeAt(vararg indexes: Int): Change<T>
    fun removeIf(filter: Predicate<T>): Change<T>
    fun removeIf(filter: (T) -> Boolean): Change<T>
    // retain
    fun retainAll(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun retainFirst(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun retainLast(comparator: Comparator<T>, vararg elements: T): Change<T>
    fun retainAt(vararg indexes: Int): Change<T>
    fun retainIf(filter: Predicate<T>): Change<T>
    fun retainIf(filter: (T) -> Boolean): Change<T>
    // setting
    fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): Change<T>
    fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): Change<T>
}

interface PrimitiveTypeOperations<T> : ArrayOperations<T> {
    // remove
    fun removeAll(vararg elements: T): PrimitiveChange<T>
    fun removeFirst(vararg elements: T): Change<T>
    fun removeLast(vararg elements: T): Change<T>
    // retain
    fun retainAll(vararg elements: T): PrimitiveChange<T>
    fun retainFirst(vararg elements: T): PrimitiveChange<T>
    fun retainLast(vararg elements: T): PrimitiveChange<T>
    // setting
    fun setAll(vararg pairs: Pair<T, T>): PrimitiveChange<T>
    fun setFirst(vararg pairs: Pair<T, T>): PrimitiveChange<T>
}