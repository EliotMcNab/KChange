package changeAPI.changes

import changeAPI.information.EvolvedInformation
import changeAPI.information.Operator
import util.differenceOf
import util.productOf
import util.quotientOf
import util.sumOf

sealed class EvolvedInformationImpl<T>(
    list: List<T>,
    parent: Change<*>?
) : TransformImpl<T>(list, parent),
    EvolvedInformation<T> {

    override fun sumOf(operator: Operator<T>): T = apply().sumOf(operator)

    override fun differenceOf(operator: Operator<T>): T = apply().differenceOf(operator)

    override fun productOf(operator: Operator<T>): T = apply().productOf(operator)

    override fun quotientOf(operator: Operator<T>): T = apply().quotientOf(operator)


}