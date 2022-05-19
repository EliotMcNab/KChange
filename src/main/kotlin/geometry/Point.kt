package geometry

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * 2D point implementation in Kotlin<br><br>
 * @param [x]: x-coordinates of the point
 * @param [y]: y-coordinates of the point
 * @author Eliot McNab
 */
data class Point(
    val x: Double,
    val y: Double
) : Comparable<Point> {
    // comparison
    override operator fun compareTo(other: Point) = compareValues(distance(), other.distance())

    // distance calculations
    infix fun distanceTo(other: Point) = sqrt((x - other.x).pow(2) + (y - other.y).pow(2))
    fun distance() = sqrt(x.pow(2) + y.pow(2))

    // range operator
    operator fun rangeTo(endInclusive: Point) = PointProgression(this, endInclusive, 1.0)
    infix fun downTo(low: Point) = PointProgression(low, this, -1.0)
}

// binary arithmetic operators
operator fun Point.plus(other: Point) = Point(x + other.x, y + other.y)
operator fun Point.minus(other: Point) = Point(x - other.x, y - other.y)
operator fun Point.times(other: Double) = Point(x * other, y * other)
operator fun Point.div(other: Double) = Point(x / other, y / other)

// unary operators
operator fun Point.unaryMinus() = Point(-x, -y)
operator fun Point.inc() = Point(x + 1, y + 1)
operator fun Point.dec() = Point(x - 1, y - 1)

class PointIterator(
    start: Point,
    private val endInclusive: Point,
    private val stepSize: Double
) : Iterator<Point> {
    private var currentPoint = start

    // increments while the current point is inferior to the inclusive end
    override fun hasNext(): Boolean = currentPoint + Point(stepSize, stepSize) <= endInclusive

    // increments by 1 to get next point
    override fun next(): Point {
        currentPoint += Point(stepSize, stepSize)
        return currentPoint
    }
}

class PointProgression(
    override val start: Point,
    override val endInclusive: Point,
    private val stepSize: Double
) : ClosedRange<Point>, Iterable<Point> {
    override fun iterator(): Iterator<Point> = PointIterator(start, endInclusive, stepSize)
}


