package comparisons

/**
 * Implementation of the [Comparator] interface with use of the default comparisons in the [Byte] class
 */
object ByteComparator : Comparator<Byte> {
    override fun compare(o1: Byte?, o2: Byte?) =
        when {
            o1 == o2                        -> 0
            o1 == null || o1 < (o2 as Byte) -> -1
            else                            -> 1
        }

    override fun toString(): String = "ByteComparator"
}

/**
 * Implementation of the [Comparator] interface with use of the default comparisons in the [Short] class
 */
object ShortComparator : Comparator<Short> {
    override fun compare(o1: Short?, o2: Short?) =
        when {
            o1 == o2                         -> 0
            o1 == null || o1 < (o2 as Short) -> -1
            else                             -> 1
        }

    override fun toString(): String = "ShortComparator"
}

/**
 * Implementation of the [Comparator] interface with use of the default comparisons in the [Int] class
 */
object IntComparator : Comparator<Int> {
    override fun compare(o1: Int?, o2: Int?) =
        when {
            o1 == o2                       -> 0
            o1 == null || o1 < (o2 as Int) -> -1
            else                           -> 1
        }

    override fun toString(): String = "IntComparator"
}

/**
 * Implementation of the [Comparator] interface with use of the default comparisons in the [Long] class
 */
object LongComparator : Comparator<Long> {
    override fun compare(o1: Long?, o2: Long?) =
        when {
            o1 == o2                        -> 0
            o1 == null || o1 < (o2 as Long) -> -1
            else                            -> 1
        }

    override fun toString(): String = "LongComparator"
}

/**
 * Implementation of the [Comparator] interface with use of the default comparisons in the [Float] class
 */
object FloatComparator : Comparator<Float> {
    override fun compare(o1: Float?, o2: Float?) =
        when {
            o1 == o2                        -> 0
            o1 == null || o1 < (o2 as Float) -> -1
            else                            -> 1
        }

    override fun toString(): String = "FloatComparator"
}

/**
 * Implementation of the [Comparator] interface with use of the default comparisons in the [Double] class
 */
object DoubleComparator : Comparator<Double> {
    override fun compare(o1: Double?, o2: Double?) =
        when {
            o1 == o2                        -> 0
            o1 == null || o1 < (o2 as Double) -> -1
            else                            -> 1
        }

    override fun toString(): String = "DoubleComparator"
}

/**
 * Implementation of the [Comparator] interface with use of the default comparisons in the [Char] class
 */
object CharComparator : Comparator<Char> {
    override fun compare(o1: Char?, o2: Char?) =
        when {
            o1 == o2                        -> 0
            o1 == null || o1 < (o2 as Char) -> -1
            else                            -> 1
        }

    override fun toString(): String = "CharComparator"
}