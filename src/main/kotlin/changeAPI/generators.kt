package changeAPI

import util.compareTo
import java.util.Objects
import java.util.Random

fun interface Generator<T>{
    fun generate(): T
    fun generate(size: Int) = buildList { for (index in 0 until size) add(generate()) }
    operator fun get(size: Int) = generate(size)
}

abstract class RandomGenerator<T>(
    seed: Long?
) : Generator<T> {
    protected val rand = if (seed == null) Random() else Random(seed)
}

abstract class RandomNumberGenerator<T : Number>(
    seed: Long?,
    private val min: T,
    private val max: T,
    private val typeName: String,
    private val generation: (min: T, max: T, rand: Random) -> T
) : RandomGenerator<T>(seed) {
    override fun generate() = require(min < max) {errorMessage()}.run { generation(min, max, rand) }
    private fun errorMessage() = "Cannot generate $typeName between min value $min and max value $max"
}

interface CompanionNumberGenerator<T : Number>{
    fun generateNumber(min: T, max: T, rand: Random): T
}

data class RandomByteGenerator(
    private val max: Byte,
    private val min: Byte = 0,
    private val seed: Long? = null
) : RandomNumberGenerator<Byte>(seed, min, max, "byte", this::generateNumber) {
    private companion object : CompanionNumberGenerator<Byte> {
        override fun generateNumber(min: Byte, max: Byte, rand: Random) = rand.nextInt(min.toInt(), max.toInt()).toByte()
    }
}

data class RandomShortGenerator(
    private val max: Short,
    private val min: Short = 0,
    private val seed: Long? = null
) : RandomNumberGenerator<Short>(seed, min, max, "short", this::generateNumber) {
    private companion object : CompanionNumberGenerator<Short> {
        override fun generateNumber(min: Short, max: Short, rand: Random) = rand.nextInt(min.toInt(), max.toInt()).toShort()
    }
}

data class RandomIntGenerator(
    private val max: Int,
    private val min: Int = 0,
    private val seed: Long? = null
) : RandomNumberGenerator<Int>(seed, min, max, "integer", this::generateNumber) {
    private companion object : CompanionNumberGenerator<Int> {
        override fun generateNumber(min: Int, max: Int, rand: Random) = rand.nextInt(min, max)
    }
}

data class RandomLongGenerator(
    private val max: Long,
    private val min: Long = 0L,
    private val seed: Long? = null
) : RandomNumberGenerator<Long>(seed, min, max, "long", this::generateNumber) {
    private companion object : CompanionNumberGenerator<Long> {
        override fun generateNumber(min: Long, max: Long, rand: Random) = rand.nextLong(min, max)
    }
}

data class RandomFloatGenerator(
    private val max: Float,
    private val min: Float = 0.0F,
    private val seed: Long? = null
) : RandomNumberGenerator<Float>(seed, min, max, "float", this::generateNumber) {
    private companion object : CompanionNumberGenerator<Float> {
        override fun generateNumber(min: Float, max: Float, rand: Random) = rand.nextFloat(min, max)
    }
}

data class RandomDoubleGenerator(
    private val max: Double,
    private val min: Double = 0.0,
    private val seed: Long? = null
) : RandomNumberGenerator<Double>(seed, min, max, "double", this::generateNumber) {
    private companion object : CompanionNumberGenerator<Double> {
        override fun generateNumber(min: Double, max: Double, rand: Random) = rand.nextDouble(min, max)
    }
}

open class RandomCharGenerator(
    private val max: Int = 127,
    private val min: Int = 0,
    private val seed: Long? = null
) : RandomGenerator<Char>(seed) {
    override fun generate() = require(validate()) { error() }.run { rand.nextInt(min, max).toChar() }
    private fun validate() = max > min && max in 1..127 && min in 0..126
    private fun error() = "Chars can only be generated in the range 0..127, current range: $min..$max"

    override fun toString() = "RandomCharGenerator(max=$max, min=$min, seed=$seed)"
    override fun equals(other: Any?) = when (other) {
        is RandomCharGenerator -> Objects.compare(this, other, compareBy ({it.seed}, {it.min}, {it.max}) ) == 0
        else                   -> false
    }
    override fun hashCode(): Int {
        var result = max
        result = 31 * result + min
        result = 31 * result + (seed?.hashCode() ?: 0)
        return result
    }
}

data class RandomLetterGenerator(
    private val max: Int = 122,
    private val min: Int = 97,
    private val upperCase: Boolean = false,
    val seed: Long? = null
) : RandomCharGenerator(max, min, seed) {
    override fun generate() = require(validate()) { error() }.run { super.generate().apply { if (upperCase && rand.nextBoolean()) return uppercaseChar() } }
    private fun validate() = max > min && max in 98..122 && min in 97..121
    private fun error() = "Letters can only be generated in the range 97..122, current range: $min..$max"
}

abstract class Generators {
    companion object {
        val byteGenerator   = RandomByteGenerator(Byte.MAX_VALUE)
        val shortGenerator  = RandomShortGenerator(Short.MAX_VALUE)
        val intGenerator    = RandomIntGenerator(Int.MAX_VALUE)
        val longGenerator   = RandomLongGenerator(Long.MAX_VALUE)
        val floatGenerator  = RandomFloatGenerator(Float.MAX_VALUE)
        val doubleGenerator = RandomDoubleGenerator(Double.MAX_VALUE)
        val charGenerator   = RandomCharGenerator()
        val letterGenerator = RandomLetterGenerator()
    }
}