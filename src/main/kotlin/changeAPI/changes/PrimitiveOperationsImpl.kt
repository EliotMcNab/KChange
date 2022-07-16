package changeAPI.changes

import changeAPI.*
import changeAPI.information.Operator
import changeAPI.operations.PrimitiveOperations
import java.util.function.Predicate

/**
 * Implements operation logic for primitive type [Changes][Change]
 */
sealed class PrimitiveOperationsImpl<T>(
    list: List<T>,
    parent: Change<*>?,
    private val comparator: Comparator<T>,
    private val operator: Operator<T>
) : PrimitiveAccessorsImpl<T>(list, parent, comparator, operator), PrimitiveOperations<T> {

    // ======================================
    //                 ADDING
    // ======================================

    /**
     * Adds a new [element] *at the end* of the [Change]
     */
    override fun add(element: T): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, Add(listOf(element), this))

    /**
     * Adds all given [elements] at the end of the [Change]
     */
    override fun addAll(vararg elements: T): PrimitiveChange<T> =
        addAll(elements.toList())

    /**
     * Adds all elements in the given [list] at the end of the [Change]
     */
    override fun addAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, Add(list, this))

    // ======================================
    //              REMOVING
    // ======================================

    /**
     * **Removes all** given [elements] from the [Change]. Comparing is done with the given [comparator] _instead of the
     * default primitive [Comparator]_
     */
    override fun removeAll(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeAll(comparator, elements.toList())

    /**
     * **Removes all** elements in the given [list] from the [Change]. Comparing is done with the given [comparator]
     * _instead of the default primitive [Comparator]_
     */
    override fun removeAll(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveAll(list, comparator, this))

    /**
     * **Removes all** given [elements] from the [Change]. _Comparing is done with the default primitive [Comparator]_
     */
    override fun removeAll(vararg elements: T): PrimitiveChange<T> =
        removeAll(elements.toList())

    /**
     * **Removes all** elements in the given [list] from the [Change]. _Comparing is done with the default primitive
     * comparator_
     */
    override fun removeAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveAll(list, comparator, this))

    /**
     * **Removes the first** occurrence of every given [element][elements]. Comparing is done with the given [comparator]
     * _instead of the default primitive [Comparator]_
     */
    override fun removeFirst(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeFirst(comparator, elements.toList())

    /**
     * **Removes the first** occurrence of every element in the given [list]. Comparing is done with the given [comparator]
     * _instead of the default primitive [Comparator]_
     */
    override fun removeFirst(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveFirst(list, comparator, this))

    /**
     * **Removes the first** occurrence of every given [element][elements]. _Comparing is done with the default primitive
     * comparator_
     */
    override fun removeFirst(vararg elements: T): PrimitiveChange<T> =
        removeFirst(elements.toList())

    /**
     * **Removes the first** occurrence of every element in the given [list]. _Comparing is done with the default primitive
     * comparator_
     */
    override fun removeFirst(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveFirst(list, comparator, this))

    /**
     * **Removes the last** occurrence of every given [element][elements]. Comparing is done with the given [comparator]
     * _instead of the default primitive [Comparator]_
     */
    override fun removeLast(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        removeLast(comparator, elements.toList())

    /**
     * **Removes the last** occurrence of every element in the given [list]. Comparing is done with the given [comparator]
     * _instead of the default primitive [Comparator]_
     */
    override fun removeLast(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveLast(list, comparator, this))

    /**
     * **Removes the last** occurrence of every given [element][elements]. _Comparison is done using the default
     * primitive [Comparator]_
     */
    override fun removeLast(vararg elements: T): PrimitiveChange<T> =
        removeLast(elements.toList())

    /**
     * **Removes the last** occurrence of every element in the given [list]. _Comparison is done using the default
     * primitive [Comparator]_
     */
    override fun removeLast(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveLast(list, comparator, this))

    /**
     * Removes elements in the [Change] **at all given [indexes]**. _Negative indexes are ignored_
     */
    override fun removeAt(vararg indexes: Int): PrimitiveChange<T> =
        removeAt(indexes.toList())

    /**
     * Removes elements in the [Change] **at all indexes in the given [list]**. _Negative indexes are ignored_
     */
    override fun removeAt(list: List<Int>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveAt(list, this))

    /**
     * Removes all elements in the [Change] **that match the given [filter]**
     */
    override fun removeIf(filter: Predicate<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RemoveIf(filter, this))

    /**
     * Removes all elements in the [Change] **that match the given [filter]**
     */
    override fun removeIf(filter: (T) -> Boolean): PrimitiveChange<T> =
        removeIf(Predicate(filter))

    // ======================================
    //              RETAINING
    // ======================================

    /**
     * **Retains all** elements in the [Change] which are also present in the given [elements]. Comparison is done using
     * the given [comparator] _instead of the default primitive [Comparator]_
     */
    override fun retainAll(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainAll(comparator, elements.toList())

    /**
     * **Retains all** elements in the [Change] which are also present in the given [list]. Comparison is done using
     * the given [comparator] _instead of the default primitive [Comparator]_
     */
    override fun retainAll(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainAll(list, comparator, this))

    /**
     * **Retains all** elements in the [Change] which are also present in the given [elements]. _Comparison is done
     * using the default primitive [Comparator]_
     */
    override fun retainAll(vararg elements: T): PrimitiveChange<T> =
        retainAll(elements.toList())

    /**
     * **Retains all** elements in the [Change] which are also present in the given [list]. _Comparison is done
     * using the default primitive [Comparator]_
     */
    override fun retainAll(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainAll(list, comparator, this))

    /**
     * **Retains only the first occurrence** of elements in the [Change] which are also in the given [elements].
     * Comparison is done using the given [comparator] _instead of the default primitive [Comparator]_
     */
    override fun retainFirst(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainFirst(comparator, elements.toList())

    /**
     * **Retains only the first occurrence** of elements in the [Change] which are also in the given [list].
     * Comparison is done using the given [comparator] _instead of the default primitive [Comparator]_
     */
    override fun retainFirst(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainFirst(list, comparator, this))

    /**
     * **Retains only the first occurrence** of elements in the [Change] which are also in the given [elements].
     * _Comparison is done using the default primitive [Comparator]_
     */
    override fun retainFirst(vararg elements: T): PrimitiveChange<T> =
        retainFirst(elements.toList())

    /**
     * **Retains only the first occurrence** of elements in the [Change] which are also in the given [list].
     * _Comparison is done using the default primitive [Comparator]_
     */
    override fun retainFirst(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainFirst(list, comparator, this))

    /**
     * **Retains only the last occurrence** of elements in the [Change] which are also in the given [elements].
     * Comparison is done using the given [Comparator] _instead of using the default primitive [Comparator]_
     */
    override fun retainLast(comparator: Comparator<T>, vararg elements: T): PrimitiveChange<T> =
        retainLast(comparator, elements.toList())

    /**
     * **Retains only the last occurrence** of elements in the [Change] which are also in the given [list].
     * Comparison is done using the given [Comparator] _instead of using the default primitive [Comparator]_
     */
    override fun retainLast(comparator: Comparator<T>, list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainLast(list, comparator, this))

    /**
     * **Retains only the last occurrence** of elements in the [Change] which are also in the given [elements].
     * _Comparison is done using the default primitive [Comparator]_
     */
    override fun retainLast(vararg elements: T): PrimitiveChange<T> =
        retainLast(elements.toList())

    /**
     * **Retains only the last occurrence** of elements in the [Change] which are also in the given [list].
     * _Comparison is done using the default primitive [Comparator]_
     */
    override fun retainLast(list: List<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainLast(list, comparator, this))

    /**
     * **Retains** only the element **at the given [indexes]** in the [Change]
     */
    override fun retainAt(vararg indexes: Int): PrimitiveChange<T> =
        retainAt(indexes.toList())

    /**
     * **Retains** only the element **at the indexes in the given [list]** in the [Change]
     */
    override fun retainAt(list: List<Int>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainAt(list, this))

    /**
     * **Retains** only elements in the [Change] **which match the given [filter]**
     */
    override fun retainIf(filter: Predicate<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, RetainIf(filter, this))

    /**
     * **Retains** only elements in the [Change] **which match the given [filter]**
     */
    override fun retainIf(filter: (T) -> Boolean): PrimitiveChange<T> =
        retainIf(Predicate(filter))

    // ======================================
    //              SETTING
    // ======================================

    /**
     * **Replaces all** elements in the [Change] which match the keys in the given key-value [pairs] by their associated
     * value. Comparing elements in the [Change] to the given keys is done using the given [comparator] _instead of the
     * default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * val change = Change
     *      .of(listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
     *      .setAll(
     *          CustomComparator,   // some fancy comparator for comparing Ints
     *          1 to 2,             // replaces all instances of 1 by 2
     *          3 to 4,             // replaces all instances of 3 by 4
     *          5 to 4              // replaces all instances of 5 by 4
     *      )
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     */
    override fun setAll(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setAll(comparator, mapOf(*pairs))

    /**
     * **Replaces all** elements in the [Change] which match the keys in the given [map][replacingMap] by their associated
     * value. Comparing elements in the [Change] to the given keys is done using the given [comparator] _instead of the
     * default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * // specifies values to replace (keys) and replacing values (values)
     * val map = mapOf(
     *      1 to 2,                 // replaces all instances of 1 by 2
     *      3 to 4,                 // replaces all instances of 3 by 4
     *      5 to 4                  // replaces all instances of 5 by 4
     * )
     *
     * val change = Change
     *      .of(listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5))
     *      .setAll(
     *          CustomComparator,   // some fancy comparator for comparing Ints
     *          map                 // map of values to replace
     *      )
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     */
    override fun setAll(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetAll(replacingMap, comparator, this))

    /**
     * **Replaces all** elements in the [Change] which match a key in any of the given [pairs] with the associated
     * new value. _Comparison between elements and keys is done using the default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * val change = Change
     *      .of(listOf(1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10))
     *      .setAll(
     *          1 to 2,
     *          3 to 4,
     *          5 to 4
     *      )
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     */
    override fun setAll(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setAll(mapOf(*pairs))

    /**
     * **Replaces all** elements in the [Change] which match a key in any of the given [map][replacingMap] with the associated
     * new value. _Comparison between elements and keys is done using the default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * // specifies the values to replace and their replacing values
     * val replacingMap = mapOf(
     *      1 to 2,
     *      3 to 4,
     *      5 to 4
     * )
     *
     * val change = Change
     *      .of(listOf(1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10))
     *      .setAll(map)
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     */
    override fun setAll(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetAll(replacingMap, comparator, this))

    /**
     * **Replaces the first** elements in the [Change] which match each keys in the given key-value [pairs] by their associated
     * value. Comparing elements in the [Change] to the given keys is done using the given [comparator] _instead of the
     * default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * val change = Change
     *      .of(listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
     *      .setFirst(
     *          CustomComparator,   // some fancy comparator for comparing Ints
     *          1 to 2,             // replaces all instances of 1 by 2
     *          3 to 4,             // replaces all instances of 3 by 4
     *          5 to 4              // replaces all instances of 5 by 4
     *      )
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     */
    override fun setFirst(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setFirst(comparator, mapOf(*pairs))

    /**
     * **Replaces the first** elements in the [Change] which match each keys in the given [map][replacingMap] by their associated
     * value. Comparing elements in the [Change] to the given keys is done using the given [comparator] _instead of the
     * default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * val change = Change
     *      .of(listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
     *      .setFirst(
     *          CustomComparator,   // some fancy comparator for comparing Ints
     *          1 to 2,             // replaces all instances of 1 by 2
     *          3 to 4,             // replaces all instances of 3 by 4
     *          5 to 4              // replaces all instances of 5 by 4
     *      )
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     */
    override fun setFirst(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetFirst(replacingMap, comparator, this))

    /**
     * **Replaces the first** elements in the [Change] which match a key in any of the given key-value [pairs] with the associated
     * new value. _Comparison between elements and keys is done using the default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * // specifies the values to replace and their replacing values
     * val replacingMap = mapOf(
     *      1 to 2,
     *      3 to 4,
     *      5 to 4
     * )
     *
     * val change = Change
     *      .of(listOf(1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10))
     *      .setFirst(map)
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     */
    override fun setFirst(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setFirst(mapOf(*pairs))

    /**
     * **Replaces the first** elements in the [Change] which match a key in any of the given [map][replacingMap] with the associated
     * new value. _Comparison between elements and keys is done using the default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * // specifies the values to replace and their replacing values
     * val replacingMap = mapOf(
     *      1 to 2,
     *      3 to 4,
     *      5 to 4
     * )
     *
     * val change = Change
     *      .of(listOf(1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10))
     *      .setFirst(map)
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     */
    override fun setFirst(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetFirst(replacingMap, comparator, this))

    /**
     * **Replaces the last** elements in the [Change] which match each keys in the given key-value [pairs] by their associated
     * value. Comparing elements in the [Change] to the given keys is done using the given [comparator] _instead of the
     * default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * val change = Change
     *      .of(listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
     *      .setLast(
     *          CustomComparator,   // some fancy comparator for comparing Ints
     *          1 to 2,             // replaces all instances of 1 by 2
     *          3 to 4,             // replaces all instances of 3 by 4
     *          5 to 4              // replaces all instances of 5 by 4
     *      )
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     * @param pairs [Pair] of values to replace in the [Change] and their associated replacing value
     * @param comparator [Comparator] used to compare elements in the [Change] with the keys in the given [pairs]
     */
    override fun setLast(comparator: Comparator<T>, vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setLast(comparator, mapOf(*pairs))

    /**
     * **Replaces the last** elements in the [Change] which match each keys in the given [map][replacingMap] by their associated
     * value. Comparing elements in the [Change] to the given keys is done using the given [comparator] _instead of the
     * default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * val change = Change
     *      .of(listOf(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
     *      .setLast(
     *          CustomComparator,   // some fancy comparator for comparing Ints
     *          1 to 2,             // replaces all instances of 1 by 2
     *          3 to 4,             // replaces all instances of 3 by 4
     *          5 to 4              // replaces all instances of 5 by 4
     *      )
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     * @param replacingMap [Map] of values to replace in the [Change] and their associated replacing value
     * @param comparator [Comparator] used to compare elements in the [Change] with the keys in the given [map][replacingMap]
     */
    override fun setLast(comparator: Comparator<T>, replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetLast(replacingMap, comparator, this))

    /**
     * **Replaces the last** elements in the [Change] which match a key in any of the given key-value [pairs] with the associated
     * new value. _Comparison between elements and keys is done using the default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * // specifies the values to replace and their replacing values
     * val replacingMap = mapOf(
     *      1 to 2,
     *      3 to 4,
     *      5 to 4
     * )
     *
     * val change = Change
     *      .of(listOf(1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10))
     *      .setLast(map)
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     * @param pairs [Pair] of values to replace in the [Change] and their associated replacing value
     */
    override fun setLast(vararg pairs: Pair<T, T>): PrimitiveChange<T> =
        setLast(mapOf(*pairs))

    /**
     * **Replaces the last** elements in the [Change] which match a key in any of the given [map][replacingMap] with the associated
     * new value. _Comparison between elements and keys is done using the default primitive [Comparator]_
     *
     * .
     * ### Example
     * ```
     * // specifies the values to replace and their replacing values
     * val replacingMap = mapOf(
     *      1 to 2,
     *      3 to 4,
     *      5 to 4
     * )
     *
     * val change = Change
     *      .of(listOf(1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10))
     *      .setLast(map)
     *      .apply()
     *
     * println(change)
     * ```
     * ### Output
     * ```
     * >> [2, 2, 2, 2, 4, 4, 4, 4, 4, 4]
     * ```
     * @param replacingMap [Map] of values to replace in the [Change] and their associated replacing value
     */
    override fun setLast(replacingMap: Map<T, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetLast(replacingMap, comparator, this))

    /**
     * **Replaces** elements in the [Change] **at the given indexes** by their associated new value, as specified in the
     * given index-value [pairs]. _Indices must be valid within the Change as the method does not filter out invalid indices_
     * @param pairs index-value [Pairs][Pair] used to find and replace existing values in the [Change]
     */
    override fun setAt(vararg pairs: Pair<Int, T>): PrimitiveChange<T> =
        setAt(mapOf(*pairs))

    /**
     * **Replaces** elements in the [Change] **at the given indexes** by their associated new value, as specified in the
     * given index-value [map][indexMap]. _Indices must be valid within the Change as the method does not filter out invalid indices_
     * @param indexMap index-value [Map] used to find and replace existing values in the [Change]
     */
    override fun setAt(indexMap: Map<Int, T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, SetAt(indexMap, this))

    // ORDERING

    /**
     * **Sorts** the [Change] _in ascending order_ according to the given [comparator]. (Use this when the primitive
     * types used are shorthands for more complex types -hashes or binary for example-)
     * @param comparator the [Comparator] used to sort elements in the [Change] into ascending order
     */
    override fun sorted(comparator: Comparator<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, Sorted(comparator, this))

    /**
     * **Sorts** the [Change] _in ascending order_ according to the default primitive [Comparator]
     */
    override fun sorted(): PrimitiveChange<T> =
        sorted(comparator)

    /**
     * **Retains only** unique values in the [Change]. Comparison is done using the given [comparator] _instead of the
     * default primitive [Comparator]_
     */
    override fun unique(comparator: Comparator<T>): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, Unique(comparator, this))

    /**
     * **Retains only** unique values in the [Change]. _Comparison is done using the default primitive [Comparator]_
     */
    override fun unique(): PrimitiveChange<T> =
        unique(comparator)

    // FUNCTIONS

    /**
     * **Applies** the given function to each element in the [Change]. _The function used must return the same type as
     * the one used by the Change_. For more complex functions which result in a type change, use [mapToObj] instead.
     */
    override fun forEach(function: (T) -> T): PrimitiveChange<T> =
        PrimitiveAdapter(this.comparator, this.operator, this, ForEach(function, this))
}