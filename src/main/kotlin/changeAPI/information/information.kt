package changeAPI.information

interface EvolvedInformation<T>{
    fun sumOf(operator: Operator<T>): T
    fun differenceOf(operator: Operator<T>): T
    fun productOf(operator: Operator<T>): T
    fun quotientOf(operator: Operator<T>): T
}

interface PrimitiveInformation<T> {
    fun sumOf(): T
    fun differenceOf(): T
    fun productOf(): T
    fun quotientOf(): T
}