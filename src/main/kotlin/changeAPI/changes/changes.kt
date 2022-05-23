package changeAPI.changes

import changeAPI.*
import changeAPI.ListInformationImpl
import changeAPI.PrimitiveInformationImpl
import changeAPI.actions.Action
import changeAPI.information.*
import changeAPI.operations.EvolvedOperations
import changeAPI.operations.delegations.ListAccessorsImpl
import changeAPI.operations.delegations.PrimitiveAccessorsImpl
import comparisons.*

open class Change<T>(
    private val list: List<T>,
    private val parent: Change<T>?,
) : Collection<Change<T>> {
    private val generation: Int = (parent?.generation?.plus(1)) ?: 0
    private lateinit var result: List<T>

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
            { throw NonexistentChangeException("Can only rollback $generation generations. Desired rollback: $count generations") }

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
        if (this::result.isInitialized) return result

        val changes = ArrayList<Change<T>>(generation)
        changes.add(this)
        for (generation in 0 until generation) changes[generation].parent?.let { changes.add(it) }

        var list = changes.last().list

        for (change in changes.reversed()) {
            when (change) {
                is Effect<*> -> list = (change as Effect<T>).applyTo(list)
                is Action<*> -> (change as Action<T>).inform(list)
                else           -> continue
            }
        }

        result = list

        return result
    }

    override val size: Int get() = TODO("Not yet implemented")

    override fun isEmpty(): Boolean = TODO("Not yet implemented")
    override fun iterator(): Iterator<Change<T>> = TODO("Not yet implemented")
    override fun containsAll(elements: Collection<Change<T>>): Boolean = TODO("Not yet implemented")
    override fun contains(element: Change<T>): Boolean = TODO("Not yet implemented")
}

abstract class ListChange<T> (
    list: List<T>,
    parent: Change<T>?
) : Change<T>(list, parent),
    ListAccessors<T> by ListAccessorsImpl(list, parent),
    ListInformation<T> by ListInformationImpl(list, parent)

open class EvolvedChange<T> private constructor(
    list: List<T> = emptyList(),
    parent: Change<T>? = null
) : EvolvedOperationsImpl<T>(list, parent) {
    constructor(list: List<T> = emptyList()) : this(list, null)
    protected constructor(parent: Change<T>) : this(emptyList(), parent)
}
open class PrimitiveChange<T>(
    list: List<T> = emptyList(),
    parent: Change<T>? = null,
    private val comparator: Comparator<T>,
    private val operator: Operator<T>
) : PrimitiveOperationsImpl<T>(list, parent, comparator, operator),
    PrimitiveAccessors<T> by PrimitiveAccessorsImpl(list, parent, comparator),
    PrimitiveInformation<T> by PrimitiveInformationImpl(list, parent, operator)
data class ByteChange(
    private val list: List<Byte>
) : PrimitiveChange<Byte>(list = list, comparator = ByteComparator, operator = ByteOperator)

data class ShortChange(
    private val list: List<Short>
) : PrimitiveChange<Short>(list = list, comparator = ShortComparator, operator = ShortOperator)

data class IntChange(
    private val list: List<Int>
) : PrimitiveChange<Int>(list = list, comparator = IntComparator, operator = IntOperator)

data class LongChange(
    private val list: List<Long>
) : PrimitiveChange<Long>(list = list, comparator = LongComparator, operator = LongOperator)

data class FloatChange(
    private val list: List<Float>
) : PrimitiveChange<Float>(list = list, comparator = FloatComparator, operator = FloatOperator)

data class DoubleChange(
    private val list: List<Double>
) : PrimitiveChange<Double>(list = list, comparator = DoubleComparator, operator = DoubleOperator)

data class CharChange(
    private val list: List<Char>
) : PrimitiveChange<Char>(list = list, comparator = CharComparator, operator = CharOperator)