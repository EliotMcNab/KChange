package changeAPI.operations

import changeAPI.PrimitiveChange

interface PrimitiveOperations<T>: PrimitiveRedefinitions<T> {
    // remove
    fun removeAll(vararg elements: T): PrimitiveChange<T>
    fun removeAll(list: List<T>): PrimitiveChange<T>
    fun removeFirst(vararg elements: T): PrimitiveChange<T>
    fun removeFirst(list: List<T>): PrimitiveChange<T>
    fun removeLast(vararg elements: T): PrimitiveChange<T>
    fun removeLast(list: List<T>): PrimitiveChange<T>
    // retain
    fun retainAll(vararg elements: T): PrimitiveChange<T>
    fun retainAll(list: List<T>): PrimitiveChange<T>
    fun retainFirst(vararg elements: T): PrimitiveChange<T>
    fun retainFirst(list: List<T>): PrimitiveChange<T>
    fun retainLast(vararg elements: T): PrimitiveChange<T>
    fun retainLast(list: List<T>): PrimitiveChange<T>
    // setting
    fun setAll(vararg pairs: Pair<T, T>): PrimitiveChange<T>
    fun setAll(replacingMap: Map<T, T>): PrimitiveChange<T>
    fun setFirst(vararg pairs: Pair<T, T>): PrimitiveChange<T>
    fun setFirst(replacingMap: Map<T, T>): PrimitiveChange<T>
    fun setLast(vararg pairs: Pair<T, T>): PrimitiveChange<T>
    fun setLast(replacingMap: Map<T, T>): PrimitiveChange<T>
    // ordering
    fun sorted(): PrimitiveChange<T>
    fun unique(): PrimitiveChange<T>

}