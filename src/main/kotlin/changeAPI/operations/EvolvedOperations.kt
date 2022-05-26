package changeAPI.operations

import changeAPI.changes.EvolvedChange
import changeAPI.changes.ListChange
import java.util.function.Predicate

interface EvolvedOperations<T> : ListOperations<T> {
    // add
    override fun add(element: T): EvolvedChange<T>
    override fun addAll(vararg elements: T): EvolvedChange<T>
    override fun addAll(list: List<T>): EvolvedChange<T>
    // remove
    override fun removeAll(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T>
    override fun removeAll(comparator: Comparator<T>, list: List<T>): EvolvedChange<T>
    override fun removeFirst(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T>
    override fun removeFirst(comparator: Comparator<T>, list: List<T>): EvolvedChange<T>
    override fun removeLast(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T>
    override fun removeLast(comparator: Comparator<T>, list: List<T>): EvolvedChange<T>
    override fun removeAt(vararg indexes: Int): EvolvedChange<T>
    override fun removeAt(list: List<Int>): EvolvedChange<T>
    override fun removeIf(filter: Predicate<T>): EvolvedChange<T>
    override fun removeIf(filter: (T) -> Boolean): EvolvedChange<T>
    // retain
    override fun retainAll(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T>
    override fun retainAll(comparator: Comparator<T>, list: List<T>): EvolvedChange<T>
    override fun retainFirst(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T>
    override fun retainFirst(comparator: Comparator<T>, list: List<T>): EvolvedChange<T>
    override fun retainLast(comparator: Comparator<T>, vararg elements: T): EvolvedChange<T>
    override fun retainLast(comparator: Comparator<T>, list: List<T>): EvolvedChange<T>
    override fun retainAt(vararg indexes: Int): EvolvedChange<T>
    override fun retainAt(list: List<Int>): EvolvedChange<T>
    override fun retainIf(filter: Predicate<T>): EvolvedChange<T>
    override fun retainIf(filter: (T) -> Boolean): EvolvedChange<T>
    // setting
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): EvolvedChange<T>
    override fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): EvolvedChange<T>
    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): EvolvedChange<T>
    override fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): EvolvedChange<T>
    override fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): EvolvedChange<T>
    override fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): EvolvedChange<T>
    override fun setAt(vararg pairs: Pair<Int, T>): EvolvedChange<T>
    override fun setAt(indexMap: Map<Int, T>): EvolvedChange<T>
    // ordering
    override fun sorted(comparator: Comparator<T>): EvolvedChange<T>
    override fun unique(comparator: Comparator<T>): EvolvedChange<T>
    // functions
    override fun forEach(function: (T) -> T): EvolvedChange<T>

}