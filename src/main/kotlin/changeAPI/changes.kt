package changeAPI

import comparisons.*
import java.util.function.Predicate

sealed class Change<T>(
    private val list: List<T> = emptyList(),
    private val parent: Change<T>? = null,
) : Collection<Change<T>> {
    private val generation: Int = (parent?.generation?.plus(1)) ?: 0

    companion object {
        fun of(vararg bytes: Byte)     = ByteChange(bytes.toList())
        fun of(vararg shorts: Short)   = ShortChange(shorts.toList())
        fun of(vararg ints: Int)       = IntChange(ints.toList())
        fun of(vararg longs: Long)     = LongChange(longs.toList())
        fun of(vararg floats: Float)   = FloatChange(floats.toList())
        fun of(vararg doubles: Double) = DoubleChange(doubles.toList())
        fun of(vararg chars: Char)     = CharChange(chars.toList())

        fun of(builder: List<Byte>)    = ByteChange(builder)
        fun of(builder: List<Short>)   = ShortChange(builder)
        fun of(builder: List<Int>)     = IntChange(builder)
        fun of(builder: List<Long>)    = LongChange(builder)
        fun of(builder: List<Float>)   = FloatChange(builder)
        fun of(builder: List<Double>)  = DoubleChange(builder)
        fun of(builder: List<Char>)    = CharChange(builder)

        fun <T> of(list: List<T>)      = EvolvedChange(list)
        fun <T> of(vararg elements: T) = EvolvedChange(elements.toList())
        fun <T> of()                   = EvolvedChange<T>()
    }

    fun rollback() = parent ?: this

    fun rollback(count: Int): Change<T> {
        require(count >= 0)
            { throw NonexistentChangeException("Rollback amount must be a positive integer") }
        require(generation - count >= 0)
            { throw NonexistentChangeException("Can only rollback $generation generations. Desired rollback: $count generations")}

        var currentChange = this
        for (generation in 0 until count) currentChange = currentChange.parent!!

        return currentChange
    }

    fun rollbackTo(generation: Int): Change<T> {
        require(generation >= 0)
            { throw NonexistentChangeException("Cannot rollback to change of generation inferior to 0") }
        require(generation < this.generation)
            { throw NonexistentChangeException("Target rollback generation $generation should be inferior to current generation ${this.generation}") }

        var currentChange = this
        while (currentChange.generation != generation) currentChange = currentChange.parent!!

        return currentChange
    }

    fun apply(): List<T> {
        val changes = ArrayList<Change<T>>(generation)
        changes.add(this)
        for (generation in 0 until generation) changes[generation].parent?.let { changes.add(it) }

        var list = changes.last().list

        for (change in changes.reversed()) {
            when (change) {
                is Effect<*>   -> list = (change as Effect<T>).applyTo(list)
                is Informer<*> -> (change as Informer<T>).inform(list)
                else           -> continue
            }
        }

        return list
    }

    override val size: Int get() = TODO("Not yet implemented")

    override fun isEmpty(): Boolean = TODO("Not yet implemented")
    override fun iterator(): Iterator<Change<T>> = TODO("Not yet implemented")
    override fun containsAll(elements: Collection<Change<T>>): Boolean = TODO("Not yet implemented")
    override fun contains(element: Change<T>): Boolean = TODO("Not yet implemented")
}

open class EvolvedChange<T> private constructor(
    list: List<T> = emptyList(),
    parent: Change<T>? = null
) : Change<T>(list, parent), ArrayOperations<T> {

    constructor(list: List<T> = emptyList()) : this(list, null)
    protected constructor(parent: Change<T>) : this(emptyList(), parent)

    // ADDING
    override fun add(element: T) : Change<T> =
        Add(listOf(element), this)

    override fun addAll(vararg elements: T): Change<T> =
        addAll(elements.toList())

    override fun addAll(list: List<T>): Change<T> =
        Add(list, this)

    // REMOVING
    override fun removeAll(comparator: Comparator<T>, vararg elements: T): Change<T> =
        removeAll(comparator, elements.toList())

    override fun removeAll(comparator: Comparator<T>, list: List<T>): Change<T> =
        RemoveAll(list, comparator, this)

    override fun removeFirst(comparator: Comparator<T>, vararg elements: T): Change<T> =
        removeFirst(comparator, elements.toList())

    override fun removeFirst(comparator: Comparator<T>, list: List<T>): Change<T> =
        RemoveFirst(list, comparator, this)

    override fun removeLast(comparator: Comparator<T>, vararg elements: T): Change<T> =
        removeLast(comparator, elements.toList())

    override fun removeLast(comparator: Comparator<T>, list: List<T>): Change<T> =
        RemoveLast(list, comparator, this)

    override fun removeAt(vararg indexes: Int): Change<T> =
        removeAt(indexes.toList())

    override fun removeAt(list: List<Int>): Change<T> =
        RemoveAt(list, this)

    override fun removeIf(filter: Predicate<T>): Change<T> =
        RemoveIf(filter, this)

    override fun removeIf(filter: (T) -> Boolean): Change<T> =
        removeIf(Predicate(filter))

    // RETAINING
    override fun retainAll(comparator: Comparator<T>, vararg elements: T): Change<T> =
        retainAll(comparator, elements.toList())

    override fun retainAll(comparator: Comparator<T>, list: List<T>): Change<T> =
        RetainAll(list, comparator, this)
    override fun retainFirst(comparator: Comparator<T>, vararg elements: T): Change<T> =
        retainFirst(comparator, elements.toList())

    override fun retainFirst(comparator: Comparator<T>, list: List<T>): Change<T> =
        RetainFirst(list, comparator, this)

    override fun retainLast(comparator: Comparator<T>, vararg elements: T): Change<T> =
        retainLast(comparator, elements.toList())

    override fun retainLast(comparator: Comparator<T>, list: List<T>): Change<T> =
        RetainLast(list, comparator, this)

    override fun retainAt(vararg indexes: Int): Change<T> =
        retainAt(indexes.toList())

    override fun retainAt(list: List<Int>): Change<T> =
        RetainAt(list, this)

    override fun retainIf(filter: Predicate<T>): Change<T> =
        RetainIf(filter, this)

    override fun retainIf(filter: (T) -> Boolean): Change<T> =
        retainIf(Predicate(filter))

    // SETTING
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): Change<T> =
        setAll(comparator, mapOf(*pairs))

    override fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): Change<T> =
        SetAll(replacingMap, comparator, this)

    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): Change<T> =
        setFirst(comparator, mapOf(*pairs))

    override fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): Change<T> =
        SetFirst(replacingMap, comparator, this)

    override fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): Change<T> =
        setLast(comparator, mapOf(*pairs))

    override fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): Change<T> =
        SetLast(replacingMap, comparator, this)

    override fun setAt(vararg pairs: Pair<Int, T>): Change<T> =
        setAt(mapOf(*pairs))
    override fun setAt(indexMap: Map<Int, T>): Change<T> =
        SetAt(indexMap, this)

    // ORDERING
    override fun sorted(comparator: Comparator<T>): Change<T> =
        Sorted(comparator, this)

    override fun unique(comparator: Comparator<T>): Change<T> =
        Unique(comparator, this)
}

sealed class PrimitiveChange<T>(
    list: List<T> = emptyList(),
    parent: Change<T>? = null,
    private val comparator: Comparator<T>
) : Change<T>(list, parent), PrimitiveTypeOperations<T> {

    // ADDING
    override fun add(element: T): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, Add(listOf(element), this))

    override fun addAll(vararg elements: T): PrimitiveChange<T> =
        addAll(elements.toList())

    override fun addAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, Add(list, this))

    // REMOVING
    override fun removeAll(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeAll(comparator, elements.toList())

    override fun removeAll(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RemoveAll(list, comparator, this))

    override fun removeAll(vararg elements: T): PrimitiveChange<T> =
        removeAll(elements.toList())

    override fun removeAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RemoveAll(list, comparator, this))

    override fun removeFirst(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeFirst(comparator, elements.toList())

    override fun removeFirst(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RemoveFirst(list, comparator, this))

    override fun removeFirst(vararg elements: T): PrimitiveChange<T> =
        removeFirst(elements.toList())

    override fun removeFirst(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RemoveFirst(list, comparator, this))

    override fun removeLast(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeLast(comparator, elements.toList())

    override fun removeLast(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RemoveLast(list, comparator, this))

    override fun removeLast(vararg elements: T): PrimitiveChange<T> =
        removeLast(elements.toList())

    override fun removeLast(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RemoveLast(list, comparator, this))

    override fun removeAt(vararg indexes: Int): PrimitiveChange<T> =
        removeAt(indexes.toList())

    override fun removeAt(list: List<Int>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RemoveAt(list, this))

    override fun removeIf(filter: Predicate<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RemoveIf(filter, this))

    override fun removeIf(filter: (T) -> Boolean): PrimitiveChange<T> =
        removeIf(Predicate(filter))

    // RETAINING
    override fun retainAll(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainAll(comparator, elements.toList())

    override fun retainAll(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RetainAll(list, comparator, this))

    override fun retainAll(vararg elements: T): PrimitiveChange<T> =
        retainAll(elements.toList())

    override fun retainAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RetainAll(list, comparator, this))

    override fun retainFirst(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainFirst(comparator, elements.toList())

    override fun retainFirst(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RetainFirst(list, comparator, this))

    override fun retainFirst(vararg elements: T): PrimitiveChange<T> =
        retainFirst(elements.toList())

    override fun retainFirst(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RetainFirst(list, comparator, this))

    override fun retainLast(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainLast(comparator, elements.toList())

    override fun retainLast(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RetainLast(list, comparator, this))

    override fun retainLast(vararg elements: T): PrimitiveChange<T> =
        retainLast(elements.toList())

    override fun retainLast(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RetainLast(list, comparator, this))

    override fun retainAt(vararg indexes: Int): PrimitiveChange<T> =
        retainAt(indexes.toList())

    override fun retainAt(list: List<Int>): PrimitiveAdapter<T> =
        PrimitiveAdapter(this.comparator, this, RetainAt(list, this))

    override fun retainIf(filter: Predicate<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, RetainIf(filter, this))

    override fun retainIf(filter: (T) -> Boolean): PrimitiveChange<T> =
        retainIf(Predicate(filter))

    // SETTING
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setAll(comparator, mapOf(*pairs))

    override fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, SetAll(replacingMap, comparator, this))

    override fun setAll(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setAll(mapOf(*pairs))

    override fun setAll(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, SetAll(replacingMap, comparator, this))

    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setFirst(comparator, mapOf(*pairs))

    override fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, SetFirst(replacingMap, comparator, this))

    override fun setFirst(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setFirst(mapOf(*pairs))

    override fun setFirst(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, SetFirst(replacingMap, comparator, this))

    override fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setLast(comparator, mapOf(*pairs))

    override fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, SetLast(replacingMap, comparator, this))

    override fun setLast(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setLast(mapOf(*pairs))

    override fun setLast(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, SetLast(replacingMap, comparator, this))

    override fun setAt(vararg pairs: Pair<Int, T>): PrimitiveChange<T> =
        setAt(mapOf(*pairs))

    override fun setAt(indexMap: Map<Int, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, SetAt(indexMap, this))

    // ORDERING
    override fun sorted(comparator: Comparator<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, Sorted(comparator, this))

    override fun sorted(): PrimitiveChange<T> =
        sorted(comparator)

    override fun unique(comparator: Comparator<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this, Unique(comparator, this))

    override fun unique(): PrimitiveChange<T> =
        unique(comparator)
}

data class ByteChange(
    private val list: List<Byte>
) : PrimitiveChange<Byte>(list = list, comparator = ByteComparator)

data class ShortChange(
    private val list: List<Short>
) : PrimitiveChange<Short>(list = list, comparator = ShortComparator)

data class IntChange(
    private val list: List<Int>
) : PrimitiveChange<Int>(list = list, comparator = IntComparator)

data class LongChange(
    private val list: List<Long>
) : PrimitiveChange<Long>(list = list, comparator = LongComparator)

data class FloatChange(
    private val list: List<Float>
) : PrimitiveChange<Float>(list = list, comparator = FloatComparator)

data class DoubleChange(
    private val list: List<Double>
) : PrimitiveChange<Double>(list = list, comparator = DoubleComparator)

data class CharChange(
    private val list: List<Char>
) : PrimitiveChange<Char>(list = list, comparator = CharComparator)