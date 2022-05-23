package changeAPI.information

abstract class Operator<T>(
    private val supportedOperations: Set<OPERATIONS>
){

    enum class OPERATIONS {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        SCALAR_MULTIPLICATION,
        DIVISION,
        SCALAR_DIVISION,
        MERGING
    }

    fun supports(operation: OPERATIONS) = supportedOperations.contains(operation)

    open fun add(a: T, b: T)     : T = throw UnsupportedOperationException()
    open fun sub(a: T, b: T)     : T = throw UnsupportedOperationException()
    open fun mult(a: T, b: T)    : T = throw UnsupportedOperationException()
    open fun mult(a: T, n: Long) : T = throw UnsupportedOperationException()
    open fun div(a: T, b: T)     : T = throw UnsupportedOperationException()
    open fun div(a: T, n: Long)  : T = throw UnsupportedOperationException()
    open fun merge(a: T, b: T)   : T = throw UnsupportedOperationException()
}

sealed class PrimitiveOperator<T> : Operator<T>(setOf(
    OPERATIONS.ADDITION,
    OPERATIONS.SUBTRACTION,
    OPERATIONS.MULTIPLICATION,
    OPERATIONS.SCALAR_MULTIPLICATION,
    OPERATIONS.DIVISION,
    OPERATIONS.SCALAR_DIVISION
))

object ByteOperator : PrimitiveOperator<Byte>() {
    override fun add(a: Byte, b: Byte)  : Byte = (a + b).toByte()
    override fun sub(a: Byte, b: Byte)  : Byte = (a - b).toByte()
    override fun mult(a: Byte, b: Byte) : Byte = (a * b).toByte()
    override fun mult(a: Byte, n: Long) : Byte = (a * n).toByte()
    override fun div(a: Byte, b: Byte)  : Byte = (a / b).toByte()
    override fun div(a: Byte, n: Long)  : Byte = (a / n).toByte()
}

object ShortOperator : PrimitiveOperator<Short>() {
    override fun add(a: Short, b: Short)  : Short = (a + b).toShort()
    override fun sub(a: Short, b: Short)  : Short = (a - b).toShort()
    override fun mult(a: Short, b: Short) : Short = (a * b).toShort()
    override fun mult(a: Short, n: Long)  : Short = (a * n).toShort()
    override fun div(a: Short, b: Short)  : Short = (a / b).toShort()
    override fun div(a: Short, n: Long)   : Short = (a / n).toShort()
}

object IntOperator : PrimitiveOperator<Int>() {
    override fun add(a: Int, b: Int)   : Int = a + b
    override fun sub(a: Int, b: Int)   : Int = a - b
    override fun mult(a: Int, b: Int)  : Int = a * b
    override fun mult(a: Int, n: Long) : Int = Math.toIntExact(a * n)
    override fun div(a: Int, b: Int)   : Int = a / b
    override fun div(a: Int, n: Long)  : Int = Math.toIntExact(a / n)
}

object LongOperator : PrimitiveOperator<Long>() {
    override fun add(a: Long, b: Long)  : Long = a + b
    override fun sub(a: Long, b: Long)  : Long = a - b
    override fun mult(a: Long, n: Long) : Long = a * n
    override fun div(a: Long, n: Long)  : Long = a / n
}

object FloatOperator : PrimitiveOperator<Float>() {
    override fun add(a: Float, b: Float)  : Float = a + b
    override fun sub(a: Float, b: Float)  : Float = a - b
    override fun mult(a: Float, b: Float) : Float = a * b
    override fun mult(a: Float, n: Long)  : Float = a * n
    override fun div(a: Float, b: Float)  : Float = a / b
    override fun div(a: Float, n: Long)   : Float = a / n
}

object DoubleOperator : PrimitiveOperator<Double>() {
    override fun add(a: Double, b: Double)  : Double = a + b
    override fun sub(a: Double, b: Double)  : Double = a - b
    override fun mult(a: Double, b: Double) : Double = a * b
    override fun mult(a: Double, n: Long)   : Double = a * n
    override fun div(a: Double, b: Double)  : Double = a / b
    override fun div(a: Double, n: Long)    : Double = a / n
}

object CharOperator : PrimitiveOperator<Char>() {
    override fun add(a: Char, b: Char)  : Char = a + b.code
    override fun sub(a: Char, b: Char)  : Char = a - b.code
    override fun mult(a: Char, b: Char) : Char = (a.code * b.code).toChar()
    override fun mult(a: Char, n: Long) : Char = Math.toIntExact(a.code * n).toChar()
    override fun div(a: Char, b: Char)  : Char = (a.code / b.code).toChar()
    override fun div(a: Char, n: Long)  : Char = Math.toIntExact(a.code / n).toChar()
}