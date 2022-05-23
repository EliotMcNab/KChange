package changeAPI.changes

import changeAPI.*
import changeAPI.information.ListInformationImpl
import changeAPI.information.PrimitiveInformationImpl
import changeAPI.information.*
import changeAPI.operations.delegations.ListAccessorsImpl
import changeAPI.operations.delegations.PrimitiveAccessorsImpl
import comparisons.*

open class Change<T>(
    private val list: List<T>,
    private val parent: Change<*>?,
    typeChange: Boolean = false
) : Collection<Change<T>> {
    private val generation: Int = (parent?.generation?.plus(1)) ?: 0
    private val typeGeneration: Int = if (!typeChange) (parent?.generation?.plus(1)) ?: 0  else 0

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

        fun of(packed: String)         = CharChange(packed.toCharArray().toList())

        fun <T> of(list: List<T>)      = EvolvedChange(list)
        fun <T> of(vararg elements: T) = EvolvedChange(elements.toList())
        fun <T> of()                   = EvolvedChange<T>()
    }

    fun rollback(): Change<T> {
        val errorMessage = "Impossible to rollback, there exists no preceding change of similar type"
        require(typeGeneration > 0) {throw NoneExistentChangeException(errorMessage)}
        return parent!! as Change<T>
    }

    fun rollback(count: Int): Change<T> {
        require(count >= 0)
            { throw NoneExistentChangeException("Rollback amount must be a positive integer") }
        require(typeGeneration - count >= 0)
            { throw NoneExistentChangeException("Can only rollback $generation generations. Desired rollback: $count generations") }

        var currentChange = this
        for (generation in 0 until count) currentChange = currentChange.parent!! as Change<T>

        return currentChange
    }

    fun rollbackTo(generation: Int): Change<T> {
        val error1 = "Cannot rollback to change of generation inferior to 0"
        val error2 = "Target rollback generation $generation should be inferior to current generation $typeGeneration"

        require(generation >= 0)
            { throw NoneExistentChangeException(error1) }
        require(generation < typeGeneration)
            { throw NoneExistentChangeException(error2) }

        var currentChange = this
        for (rollBack in 0 until (typeGeneration - generation)) currentChange = currentChange.parent!! as Change<T>

        return currentChange
    }

    private fun joinChanges(): List<Change<*>> {
        val allChange = arrayListOf<Change<*>>()

        allChange.add(this)
        for (currentGeneration in 0 until generation) allChange.add(allChange.last().parent!!)

        return allChange.reversed()
    }

    private fun compartmentalise(changes: List<Change<*>>): List<Pair<Int, Int>> {

        val compartments = arrayListOf<Pair<Int, Int>>()

        var compartmentStart : Int
        var compartmentStop = changes.size

        for (index in changes.indices.reversed()) {
            compartmentStart = index

            if (changes[index].typeGeneration == 0) {
                compartments.add(Pair(compartmentStart + 1, compartmentStop))
                compartmentStop = compartmentStart
            }
        }

        return compartments
    }

    private fun resolve(changes: List<Change<*>>): List<T> {
        var result = changes.first().list as List<T>

        for (change in changes) when (change) {
            is Effect<*, *> -> result = (change as Effect<T, T>).applyTo(result)
            else            -> continue
        }

        return result
    }

    fun apply(): List<T> = resolve(joinChanges())

    override val size: Int get() = TODO("Not yet implemented")

    override fun isEmpty(): Boolean = TODO("Not yet implemented")
    override fun iterator(): Iterator<Change<T>> = TODO("Not yet implemented")
    override fun containsAll(elements: Collection<Change<T>>): Boolean = TODO("Not yet implemented")
    override fun contains(element: Change<T>): Boolean = TODO("Not yet implemented")
}

abstract class ListChange<T> (
    list: List<T>,
    parent: Change<*>?
) : Change<T>(list, parent),
    ListAccessors<T> by ListAccessorsImpl(list, parent),
    ListInformation<T> by ListInformationImpl(list, parent)

open class EvolvedChange<T> private constructor(
    list: List<T> = emptyList(),
    parent: Change<*>? = null
) : EvolvedOperationsImpl<T>(list, parent) {
    constructor(list: List<T> = emptyList()) : this(list, null)
    protected constructor(parent: Change<*>) : this(emptyList(), parent)
}
open class PrimitiveChange<T>(
    list: List<T> = emptyList(),
    parent: Change<*>? = null,
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