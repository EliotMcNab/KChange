package changeAPI.information

/**
 * Defines basic operations between objects of the same type. An [Operator] **must specify the** [OPERATIONS]
 * **it supports** as part of its constructor. These [OPERATIONS] should match the *operation function* which have
 * been overridden in the operator (for example the operator should override the *add* function if it is marked as
 * supporting *ADDITION*)
 * @author Eliot McNab
 */
abstract class Operator<T>(
    private val supportedOperations: Set<OPERATIONS>
){

    /**
     * Operation types supported by the operator
     */
    enum class OPERATIONS {
        ADDITION,
        SUBTRACTION,
        MULTIPLICATION,
        SCALAR_MULTIPLICATION,
        DIVISION,
        SCALAR_DIVISION,
        MERGING
    }

    /**
     * Checks whether the given [OPERATIONS] is supported by the [Operator]
     * @return ([Boolean]): *true* if the operation is supported, *false* otherwise
     */
    fun supports(operation: OPERATIONS) = supportedOperations.contains(operation)

    /**
     * Defines how to add together objects supported by the [Operator].
     * @throws UnsupportedOperationException when the function is called from an [Operator] which does not support it.
     * To check if an operator supports a given operation, use the *supports(operation: OPERATIONS)* function before
     * calling the operation function itself
     */
    open fun add(a: T, b: T)     : T = throw UnsupportedOperationException()

    /**
     * Defines how to subtract together objects supported by the [Operator].
     * @throws UnsupportedOperationException when the function is called from an [Operator] which does not support it.
     * To check if an operator supports a given operation, use the *supports(operation: OPERATIONS)* function before
     * calling the operation function itself
     */
    open fun sub(a: T, b: T)     : T = throw UnsupportedOperationException()

    /**
     * Defines how to multiply together objects supported by the [Operator].
     * @throws UnsupportedOperationException when the function is called from an [Operator] which does not support it.
     * To check if an operator supports a given operation, use the *supports(operation: OPERATIONS)* function before
     * calling the operation function itself
     */
    open fun mult(a: T, b: T)    : T = throw UnsupportedOperationException()

    /**
     * Defines how to multiply objects supported by the [Operator] by a [Long].
     * @throws UnsupportedOperationException when the function is called from an [Operator] which does not support it.
     * To check if an operator supports a given operation, use the *supports(operation: OPERATIONS)* function before
     * calling the operation function itself
     */
    open fun mult(a: T, n: Long) : T = throw UnsupportedOperationException()

    /**
     * Defines how to divide together objects supported by the [Operator].
     * @throws UnsupportedOperationException when the function is called from an [Operator] which does not support it.
     * To check if an operator supports a given operation, use the *supports(operation: OPERATIONS)* function before
     * calling the operation function itself
     */
    open fun div(a: T, b: T)     : T = throw UnsupportedOperationException()

    /**
     * Defines how to divide objects supported by the [Operator] by a [Long].
     * @throws UnsupportedOperationException when the function is called from an [Operator] which does not support it.
     * To check if an operator supports a given operation, use the *supports(operation: OPERATIONS)* function before
     * calling the operation function itself
     */
    open fun div(a: T, n: Long)  : T = throw UnsupportedOperationException()

    /**
     * Represents a more advanced operation between objects supported by the [Operator] which is not easily represented
     * by other basic operations. **This should not be used as a shortcut for chaining other primitive operations**. For
     * that purpose, use an *extension function instead*
     * @throws UnsupportedOperationException when the function is called from an [Operator] which does not support it.
     * To check if an operator supports a given operation, use the *supports(operation: OPERATIONS)* function before
     * calling the operation function itself
     */
    open fun merge(a: T, b: T)   : T = throw UnsupportedOperationException()
}

/**
 * Defines the [Operator.OPERATIONS] supported by primitive operators
 * @author Eliot McNab
 */
sealed class PrimitiveOperator<T> : Operator<T>(setOf(
    OPERATIONS.ADDITION,
    OPERATIONS.SUBTRACTION,
    OPERATIONS.MULTIPLICATION,
    OPERATIONS.SCALAR_MULTIPLICATION,
    OPERATIONS.DIVISION,
    OPERATIONS.SCALAR_DIVISION
))

/**
 * Defines primitive operations for the [Byte] type
 * @author Eliot McNab
 */
object ByteOperator : PrimitiveOperator<Byte>() {
    override fun add(a: Byte, b: Byte)  : Byte = (a + b).toByte()
    override fun sub(a: Byte, b: Byte)  : Byte = (a - b).toByte()
    override fun mult(a: Byte, b: Byte) : Byte = (a * b).toByte()
    override fun mult(a: Byte, n: Long) : Byte = (a * n).toByte()
    override fun div(a: Byte, b: Byte)  : Byte = (a / b).toByte()
    override fun div(a: Byte, n: Long)  : Byte = (a / n).toByte()
}

/**
 * Defines primitive operations for the [Short] type
 * @author Eliot McNab
 */
object ShortOperator : PrimitiveOperator<Short>() {
    override fun add(a: Short, b: Short)  : Short = (a + b).toShort()
    override fun sub(a: Short, b: Short)  : Short = (a - b).toShort()
    override fun mult(a: Short, b: Short) : Short = (a * b).toShort()
    override fun mult(a: Short, n: Long)  : Short = (a * n).toShort()
    override fun div(a: Short, b: Short)  : Short = (a / b).toShort()
    override fun div(a: Short, n: Long)   : Short = (a / n).toShort()
}

/**
 * Defines primitive operations for the [Int] type
 * @author Eliot McNab
 */
object IntOperator : PrimitiveOperator<Int>() {
    override fun add(a: Int, b: Int)   : Int = a + b
    override fun sub(a: Int, b: Int)   : Int = a - b
    override fun mult(a: Int, b: Int)  : Int = a * b
    override fun mult(a: Int, n: Long) : Int = Math.toIntExact(a * n)
    override fun div(a: Int, b: Int)   : Int = a / b
    override fun div(a: Int, n: Long)  : Int = Math.toIntExact(a / n)
}

/**
 * Defines primitive operations for the [Long] type
 * @author Eliot McNab
 */
object LongOperator : PrimitiveOperator<Long>() {
    override fun add(a: Long, b: Long)  : Long = a + b
    override fun sub(a: Long, b: Long)  : Long = a - b
    override fun mult(a: Long, n: Long) : Long = a * n
    override fun div(a: Long, n: Long)  : Long = a / n
}

/**
 * Defines primitive operations for the [Float] type
 * @author Eliot McNab
 */
object FloatOperator : PrimitiveOperator<Float>() {
    override fun add(a: Float, b: Float)  : Float = a + b
    override fun sub(a: Float, b: Float)  : Float = a - b
    override fun mult(a: Float, b: Float) : Float = a * b
    override fun mult(a: Float, n: Long)  : Float = a * n
    override fun div(a: Float, b: Float)  : Float = a / b
    override fun div(a: Float, n: Long)   : Float = a / n
}

/**
 * Defines primitive operations for the [Double] type
 * @author Eliot McNab
 */
object DoubleOperator : PrimitiveOperator<Double>() {
    override fun add(a: Double, b: Double)  : Double = a + b
    override fun sub(a: Double, b: Double)  : Double = a - b
    override fun mult(a: Double, b: Double) : Double = a * b
    override fun mult(a: Double, n: Long)   : Double = a * n
    override fun div(a: Double, b: Double)  : Double = a / b
    override fun div(a: Double, n: Long)    : Double = a / n
}

/**
 * Defines primitive operations for the [Char] type
 * @author Eliot McNab
 */
object CharOperator : PrimitiveOperator<Char>() {
    override fun add(a: Char, b: Char)  : Char = a + b.code
    override fun sub(a: Char, b: Char)  : Char = a - b.code
    override fun mult(a: Char, b: Char) : Char = (a.code * b.code).toChar()
    override fun mult(a: Char, n: Long) : Char = Math.toIntExact(a.code * n).toChar()
    override fun div(a: Char, b: Char)  : Char = (a.code / b.code).toChar()
    override fun div(a: Char, n: Long)  : Char = Math.toIntExact(a.code / n).toChar()
}