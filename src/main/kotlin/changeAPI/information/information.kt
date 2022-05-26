package changeAPI.information

interface EvolvedInformation<T>{
    fun sumAll(operator: Operator<T>): T
    fun subAll(operator: Operator<T>): T
    fun multAll(operator: Operator<T>): T
    fun divAll(operator: Operator<T>): T
    fun mergeAll(operator: Operator<T>): T
}

interface PrimitiveInformation<T> {
    fun sumOf(): T
    fun differenceOf(): T
    fun productOf(): T
    fun quotientOf(): T
}