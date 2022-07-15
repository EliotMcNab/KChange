package changeAPI.changes

import changeAPI.information.Operator
import changeAPI.information.PrimitiveCollecting
import util.subAll
import util.multAll
import util.divAll
import util.addAll

/**
 * Implements **collection logic** for *primitive* [Changes][Change]
 */
sealed class PrimitiveCollectingImpl<T>(
    list: List<T>,
    parent: Change<*>?,
    private val operator: Operator<T>,
) : EvolvedAccessorsImpl<T>(list, parent),
    PrimitiveCollecting<T> {

    /**
     * Adds up all elements in the [Change]
     */
    override fun addAll(): T = apply().addAll(operator)

    /**
     * Subtracts all elements in the [Change] with each other
     */
    override fun subAll(): T = apply().subAll(operator)

    /**
     * Multiplies all elements in the [Change] with each other
     */
    override fun multAll(): T = apply().multAll(operator)

    /**
     * Divides all elements in the [Change] with each other
     */
    override fun divAll(): T = apply().divAll(operator)

}