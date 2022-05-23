package changeAPI.information

import changeAPI.changes.Change
import changeAPI.changes.ListChange
import util.differenceOf
import util.productOf
import util.quotientOf
import util.sumOf

internal class ListInformationImpl<T>(
    list: List<T>,
    parent: Change<*>?
) : Change<T>(list, parent), ListInformation<T> {
    override fun sumOf(operator: Operator<T>): T = apply().sumOf(operator)

    override fun differenceOf(operator: Operator<T>): T = apply().differenceOf(operator)

    override fun productOf(operator: Operator<T>): T = apply().productOf(operator)

    override fun quotientOf(operator: Operator<T>): T = apply().quotientOf(operator)

}

internal class PrimitiveInformationImpl<T>(
    list: List<T>,
    parent: Change<*>?,
    private val operator: Operator<T>
) : ListChange<T>(list, parent), PrimitiveInformation<T> {
    override fun sumOf(): T = apply().sumOf(operator)

    override fun differenceOf(): T = apply().differenceOf(operator)

    override fun productOf(): T = apply().productOf(operator)

    override fun quotientOf(): T = apply().quotientOf(operator)

}