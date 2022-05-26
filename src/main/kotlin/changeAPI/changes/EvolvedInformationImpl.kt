package changeAPI.changes

import changeAPI.information.EvolvedInformation
import changeAPI.information.Operator
import util.*

sealed class EvolvedInformationImpl<T>(
    list: List<T>,
    parent: Change<*>?
) : TransformImpl<T>(list, parent),
    EvolvedInformation<T> {

    override fun sumAll(operator: Operator<T>): T = apply().sumOf(operator)

    override fun subAll(operator: Operator<T>): T = apply().differenceOf(operator)

    override fun multAll(operator: Operator<T>): T = apply().productOf(operator)

    override fun divAll(operator: Operator<T>): T = apply().quotientOf(operator)

    override fun mergeAll(operator: Operator<T>): T = apply().mergeOf(operator)

}