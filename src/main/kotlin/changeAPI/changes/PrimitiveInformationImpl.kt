package changeAPI.changes

import changeAPI.information.Operator
import changeAPI.information.PrimitiveInformation
import util.differenceOf
import util.productOf
import util.quotientOf
import util.sumOf

sealed class PrimitiveInformationImpl<T>(
    list: List<T>,
    parent: Change<*>?,
    private val operator: Operator<T>,
) : EvolvedAccessorsImpl<T>(list, parent),
    PrimitiveInformation<T> {

    override fun sumOf(): T = apply().sumOf(operator)

    override fun differenceOf(): T = apply().differenceOf(operator)

    override fun productOf(): T = apply().productOf(operator)

    override fun quotientOf(): T = apply().quotientOf(operator)

}