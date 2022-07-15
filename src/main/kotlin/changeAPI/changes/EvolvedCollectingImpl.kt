package changeAPI.changes

import changeAPI.information.EvolvedCollecting
import changeAPI.information.Operator
import util.*

/**
 * Implements **collection logic** for *evolved type* [Changes][Change]
 */
sealed class EvolvedCollectingImpl<T>(
    list: List<T>,
    parent: Change<*>?
) : MappingsImpl<T>(list, parent),
    EvolvedCollecting<T> {

    /**
     * Adds up all elements in the [Change] as specified by the given [operator]
     */
    override fun addAll(operator: Operator<T>): T = apply().addAll(operator)

    /**
     * Subtracts all elements in the [Change] with each other as specified by the given [operator]
     */
    override fun subAll(operator: Operator<T>): T = apply().subAll(operator)

    /**
     * Multiplies all elements in the [Change] with each other as specified by the given [operator]
     */
    override fun multAll(operator: Operator<T>): T = apply().multAll(operator)

    /**
     * Divides all elements in the [Change] with each other as specified by the given [operator]
     */
    override fun divAll(operator: Operator<T>): T = apply().divAll(operator)

    /**
     * Merges all elements in the [Change] with each other as specified by the given [operator]
     */
    override fun mergeAll(operator: Operator<T>): T = apply().mergeAll(operator)

}