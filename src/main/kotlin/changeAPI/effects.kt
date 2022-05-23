package changeAPI

import changeAPI.changes.Change
import changeAPI.changes.EvolvedChange
import changeAPI.changes.PrimitiveChange
import changeAPI.information.Operator
import util.*
import java.util.function.Predicate

// TODO: update equals method in data class so they don't take parent into account ?

interface Effect<I, O>{
    fun applyTo(list: List<I>): List<O>
}

interface ContinuousEffect<T> : Effect<T, T>

data class PrimitiveAdapter<I, O>(
    val comparator: Comparator<O>,
    val operator: Operator<O>,
    val parent: Change<*>?,
    private val effect: Effect<I, O>
) : PrimitiveChange<O>(comparator = comparator, operator = operator, parent = parent), Effect<I, O> {
    override fun applyTo(list: List<I>) = effect.applyTo(list)
    override fun toString() = "Primitive$effect"
}

abstract class AddBase<T>(
    protected open val toAdd: List<T>,
    parent: Change<*>
) : EvolvedChange<T>(parent), ContinuousEffect<T>

data class Add<T>(
    override val toAdd: List<T>,
    val parent: Change<*>
) : AddBase<T>(toAdd, parent) {
    override fun applyTo(list: List<T>) = list + toAdd
    override fun toString() = "Add(toAdd=$toAdd)"
}

abstract class RemoveBase<T>(
    protected open val toRemove: List<T> = emptyList(),
    override val comparator: Comparator<T>? = null,
    protected open val removalIndexes: List<Int> = emptyList(),
    protected open val filter: Predicate<T>? = null,
    parent: Change<*>
) : ComparingBase<T>(comparator, parent)

open class RemoveAll<T>(
    override val toRemove: List<T>,
    override val comparator: Comparator<T>,
    parent: Change<*>
) : RemoveBase<T>(toRemove = toRemove, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.removeAt(list.findAll(toRemove, comparator))
    override fun toString() = "RemoveAll(toRemove=$toRemove, comparator=$comparator)"
}

data class RemoveFirst<T>(
    override val toRemove: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : RemoveBase<T>(toRemove = toRemove, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.removeAt(list.findFirst(toRemove, comparator))
    override fun toString() = "RemoveFirst(toRemove=$toRemove, comparator=$comparator)"
}

data class RemoveLast<T>(
    override val toRemove: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : RemoveBase<T>(toRemove = toRemove, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.removeAt(list.findLast(toRemove, comparator))
    override fun toString() = "RemoveLast(toRemove=$toRemove, comparator=$comparator)"
}

data class RemoveAt<T>(
    override val removalIndexes: List<Int>,
    val parent: Change<*>
) : RemoveBase<T>(removalIndexes = removalIndexes, parent = parent) {
    override fun applyTo(list: List<T>) = list.removeAt(removalIndexes)
    override fun toString(): String = "RemoveAt(indexes=$removalIndexes)"
}

data class RemoveIf<T>(
    override val filter: Predicate<T>,
    val parent: Change<*>
) : RemoveBase<T>(filter = filter, parent = parent) {
    override fun applyTo(list: List<T>): List<T> = list.removeAt(list.findAllMatches(filter))
    override fun toString() = "RemoveIf(filter=$filter)"
}

abstract class RetainBase<T>(
    protected open val toRetain: List<T> = emptyList(),
    override val comparator: Comparator<T>? = null,
    protected open val retainedIndexes: List<Int> = emptyList(),
    protected open val filter: Predicate<T>? = null,
    parent: Change<*>
) : ComparingBase<T>(comparator, parent)

data class RetainAll<T>(
    override val toRetain: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : RetainBase<T>(toRetain = toRetain, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(list.findAll(toRetain, comparator))
    override fun toString() = "RetainAll(toRetain=$toRetain, comparator=$comparator)"
}

data class RetainFirst<T>(
    override val toRetain: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : RetainBase<T>(toRetain = toRetain, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(list.findFirst(toRetain, comparator))
    override fun toString() = "RetainFirst(toRetain=$toRetain, comparator=$comparator)"
}

data class RetainLast<T>(
    override val toRetain: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : RetainBase<T>(toRetain = toRetain, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(list.findLast(toRetain, comparator))
    override fun toString() = "RetainLast(toRetain=$toRetain, comparator=$comparator)"
}

data class RetainIf<T>(
    override val filter: Predicate<T>,
    val parent: Change<*>
) : RetainBase<T>(filter = filter, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(list.findAllMatches(filter))
    override fun toString() = "RetainIf(filter=$filter)"
}

data class RetainAt<T>(
    override val retainedIndexes: List<Int>,
    val parent: Change<*>
) : RetainBase<T>(retainedIndexes = retainedIndexes, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(retainedIndexes)
}

abstract class SetBase<T>(
    protected open val replacingMap: Map<T, T>? = null,
    override val comparator: Comparator<T>? = null,
    protected open val indexMap: Map<Int, T>? = null,
    parent: Change<*>
) : ComparingBase<T>(comparator, parent)

data class SetAll<T>(
    override val replacingMap: Map<T, T>,
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : SetBase<T>(replacingMap = replacingMap, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.setAll(replacingMap, comparator)
    override fun toString() = "SetAll(toReplace=${replacingMap.keys}, replacing=${replacingMap.values}, comparator=$comparator)"
}

data class SetFirst<T>(
    override val replacingMap: Map<T, T>,
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : SetBase<T>(replacingMap = replacingMap, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.setFirst(replacingMap, comparator)
    override fun toString() = "SetFirst(toReplace=${replacingMap.keys}, replacing=${replacingMap.values}, comparator=$comparator)"
}

data class SetLast<T>(
    override val replacingMap: Map<T, T>,
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : SetBase<T>(replacingMap = replacingMap, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.setLast(replacingMap, comparator)
    override fun toString() = "SetLast(toReplace=${replacingMap.keys}, replacing=${replacingMap.values}, comparator=$comparator)"
}

data class SetAt<T>(
    override val indexMap: Map<Int, T>,
    val parent: Change<*>
) : SetBase<T>(indexMap = indexMap, parent = parent) {
    override fun applyTo(list: List<T>) = list.setAt(indexMap)
    override fun toString() = "SetAt(indexes=${indexMap.keys}, replacing=${indexMap.values})"
}

abstract class ComparingBase<T>(
    protected open val comparator: Comparator<T>? = null,
    parent: Change<*>
) : EvolvedChange<T>(parent), ContinuousEffect<T>

data class Unique<T>(
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : ComparingBase<T>(comparator, parent) {
    //TODO: implement custom method which retains elements based on the given comparator
    override fun applyTo(list: List<T>) = list.union(list).toList()
    override fun toString() = "Unique(comparator=$comparator)"
}

data class Sorted<T>(
    override val comparator: Comparator<T>,
    val parent: Change<*>
) : ComparingBase<T>(comparator, parent) {
    override fun applyTo(list: List<T>) = list.sortedWith(comparator)
    override fun toString() = "Sorted(comparator=$comparator)"
}

abstract class MapBase<I, O>(
    protected open val mappingFunction: (I) -> O,
    parent: Change<*>
) : EvolvedChange<O>(parent), Effect<I, O>

data class SimpleMapping<I, O>(
    override val mappingFunction: (I) -> O,
    val parent: Change<*>
) : MapBase<I, O>(mappingFunction, parent) {
    override fun applyTo(list: List<I>): List<O> = list.map { value -> mappingFunction(value) }
}
