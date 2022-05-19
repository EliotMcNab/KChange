package changeAPI

import comparisons.*
import java.util.function.Predicate

sealed class Change<T>(
    private val list: List<T> = emptyList(),
    private val parent: Change<T>? = null,
) : Collection<Change<T>> {
    val generation: Int = (parent?.generation?.plus(1)) ?: 0

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
        if (count < 0)
            throw NonexistentChangeException("Rollback amount must be a positive integer")
        if (generation - count < 0)
            throw NonexistentChangeException("Can only rollback $generation generations. Desired rollback: $count generations")

        var currentChange = this
        for (generation in 0 until count) currentChange = currentChange.parent!!

        return currentChange
    }

    fun rollbackTo(generation: Int): Change<T> {
        if (generation < 0)
            throw NonexistentChangeException("Cannot rollback to change of generation inferior to 0")
        if (generation >= this.generation)
            throw NonexistentChangeException("Cannot rollback to a change of generation superior or equal to the current change")

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

    fun display() = Display(this)

    // add
    override fun add(element: T)                                                = Add(listOf(element), this)
    override fun addAll(vararg elements: T)                                     = Add(elements.toList(), this)
    // remove
    override fun removeAll(comparator: Comparator<T>, vararg elements: T)       = RemoveAll(elements.toList(), comparator, this)
    override fun removeFirst(comparator: Comparator<T>, vararg elements: T)    = RemoveFirst(elements.toList(), comparator, this)
    override fun removeLast(comparator: Comparator<T>, vararg elements: T)     = RemoveLast(elements.toList(), comparator, this)
    override fun removeAt(vararg indexes: Int)                                 = RemoveAt(indexes.toList(), this)
    override fun removeIf(filter: Predicate<T>)                                = RemoveIf(filter, this)
    override fun removeIf(filter: (T) -> Boolean)                              = removeIf(Predicate(filter))
    // retain
    override fun retainAll(comparator: Comparator<T>, vararg elements: T)      = RetainAll(elements.toList(), comparator, this)
    override fun retainFirst(comparator: Comparator<T>, vararg elements: T)    = RetainFirst(elements.toList(), comparator, this)
    override fun retainLast(comparator: Comparator<T>, vararg elements: T)     = RetainLast(elements.toList(), comparator, this)
    override fun retainAt(vararg indexes: Int)                                 = RetainAt(indexes.toList(), this)
    override fun retainIf(filter: Predicate<T>)                                = RetainIf(filter, this)
    override fun retainIf(filter: (T) -> Boolean)                              = retainIf(Predicate(filter))
    // setting
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>)   = SetAll(mapOf(*pairs), comparator, this)
    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>) = SetFirst(mapOf(*pairs), comparator, this)
}

sealed class PrimitiveChange<T>(
    list: List<T> = emptyList(),
    parent: Change<T>? = null,
    private val comparator: Comparator<T>
) : Change<T>(list, parent), PrimitiveTypeOperations<T> {

    // add
    override fun add(element: T)                                               = PrimitiveAdapter(this.comparator, this, Add(listOf(element), this))
    override fun addAll(vararg elements: T)                                    = PrimitiveAdapter(this.comparator, this, Add(elements.toList(), this))
    // remove
    override fun removeAll(comparator: Comparator<T>, vararg elements: T)      = PrimitiveAdapter(this.comparator, this, RemoveAll(elements.toList(), comparator, this))
    override fun removeAll(vararg elements: T)                                 = PrimitiveAdapter(this.comparator, this, RemoveAll(elements.toList(), comparator, this))
    fun removeAll(list: List<T>) = PrimitiveAdapter(this.comparator, this, RemoveAll(list, comparator, this))
    override fun removeFirst(comparator: Comparator<T>, vararg elements: T)    = PrimitiveAdapter(this.comparator, this, RemoveFirst(elements.toList(), comparator, this))
    override fun removeFirst(vararg elements: T)                               = PrimitiveAdapter(this.comparator, this, RemoveFirst(elements.toList(), comparator, this))
    override fun removeLast(comparator: Comparator<T>, vararg elements: T)     = PrimitiveAdapter(this.comparator, this, RemoveLast(elements.toList(), comparator, this))
    override fun removeLast(vararg elements: T)                                = PrimitiveAdapter(this.comparator, this, RemoveLast(elements.toList(), comparator, this))
    override fun removeAt(vararg indexes: Int)                                 = PrimitiveAdapter(this.comparator, this, RemoveAt(indexes.toList(), this))
    override fun removeIf(filter: Predicate<T>)                                = PrimitiveAdapter(this.comparator, this, RemoveIf(filter, this))
    override fun removeIf(filter: (T) -> Boolean)                              = removeIf(Predicate(filter))
    // retain
    override fun retainAll(comparator: Comparator<T>, vararg elements: T)      = PrimitiveAdapter(this.comparator, this, RetainAll(elements.toList(), comparator, this))
    override fun retainAll(vararg elements: T): PrimitiveChange<T>             = PrimitiveAdapter(this.comparator, this, RetainAll(elements.toList(), comparator, this))
    override fun retainFirst(comparator: Comparator<T>, vararg elements: T)    = PrimitiveAdapter(this.comparator, this, RetainFirst(elements.toList(), comparator, this))
    override fun retainFirst(vararg elements: T)                               = PrimitiveAdapter(this.comparator, this, RetainFirst(elements.toList(), comparator, this))
    override fun retainLast(comparator: Comparator<T>, vararg elements: T)     = PrimitiveAdapter(this.comparator, this, RetainLast(elements.toList(), comparator, this))
    override fun retainLast(vararg elements: T)                                = PrimitiveAdapter(this.comparator, this, RetainLast(elements.toList(), comparator, this))
    override fun retainAt(vararg indexes: Int)                                 = PrimitiveAdapter(this.comparator, this, RetainAt(indexes.toList(), this))
    override fun retainIf(filter: Predicate<T>)                                = PrimitiveAdapter(this.comparator, this, RetainIf(filter, this))
    override fun retainIf(filter: (T) -> Boolean)                              = retainIf(Predicate(filter))
    // setting
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>)   = PrimitiveAdapter(this.comparator, this, SetAll(mapOf(*pairs), comparator, this))
    override fun setAll(vararg pairs: Pair<T, T>)                              = PrimitiveAdapter(this.comparator, this, SetAll(mapOf(*pairs), comparator, this))
    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>) = PrimitiveAdapter(this.comparator, this, SetFirst(mapOf(*pairs), comparator, this))
    override fun setFirst(vararg pairs: Pair<T, T>)                            = PrimitiveAdapter(this.comparator, this, SetFirst(mapOf(*pairs), comparator, this))
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