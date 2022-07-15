package changeAPI.information

/**
 * Defines the functions used to collect values from a Change holding only evolved type information
 * @author Eliot McNab
 */
interface EvolvedCollecting<T>{
    fun addAll(operator: Operator<T>): T
    fun subAll(operator: Operator<T>): T
    fun multAll(operator: Operator<T>): T
    fun divAll(operator: Operator<T>): T
    fun mergeAll(operator: Operator<T>): T
}

/**
 * Defines the functions used to collect values from a Change holding only primitive type information
 * @author Eliot McNab
 */
interface PrimitiveCollecting<T> {
    fun addAll(): T
    fun subAll(): T
    fun multAll(): T
    fun divAll(): T
}