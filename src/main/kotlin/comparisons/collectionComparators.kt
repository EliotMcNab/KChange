package comparisons

class ListComparator<T>(
    private val comparator: Comparator<T>,
    private val index: Int
) : Comparator<List<T>> {
    override fun compare(o1: List<T>?, o2: List<T>?): Int {
        if (index < 0 || (o1?.size ?: -1) <= index || (o2?.size ?: -1) <= index)
            throw IndexOutOfBoundsException(
                "can't compare element at index $index ins arrays of size " +
                "${o1?.size ?: "null"} and ${o2?.size ?: "null"}"
            )

        return comparator.compare(o1?.get(index), o2?.get(index))
    }
}