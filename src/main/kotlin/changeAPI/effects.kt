package changeAPI

import util.*
import java.util.function.Predicate

interface Effect<T>{
    fun applyTo(list: List<T>): List<T>
}

data class PrimitiveAdapter<T>(
    val comparator: Comparator<T>,
    val parent: Change<T>?,
    private val effect: Effect<T>
) : PrimitiveChange<T>(comparator = comparator, parent = parent), Effect<T> {
    override fun applyTo(list: List<T>) = effect.applyTo(list)
    override fun toString() = "Primitive$effect"
}

abstract class AddBase<T>(
    protected open val toAdd: List<T>,
    parent: Change<T>
) : EvolvedChange<T>(parent), Effect<T>

data class Add<T>(
    override val toAdd: List<T>,
    val parent: Change<T>
) : AddBase<T>(toAdd, parent) {
    override fun applyTo(list: List<T>) = list + toAdd
    override fun toString() = "Add(toAdd=$toAdd)"
}

abstract class RemoveBase<T>(
    protected open val toRemove: List<T> = emptyList(),
    protected open val comparator: Comparator<T>? = null,
    protected open val removalIndexes: List<Int> = emptyList(),
    protected open val filter: Predicate<T>? = null,
    parent: Change<T>
) : EvolvedChange<T>(parent), Effect<T>

open class RemoveAll<T>(
    override val toRemove: List<T>,
    override val comparator: Comparator<T>,
    parent: Change<T>
) : RemoveBase<T>(toRemove = toRemove, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.removeAt(list.findAll(toRemove, comparator))
    override fun toString() = "RemoveAll(toRemove=$toRemove, comparator=$comparator)"
}

data class RemoveFirst<T>(
    override val toRemove: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<T>
) : RemoveBase<T>(toRemove = toRemove, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.removeAt(list.findFirst(toRemove, comparator))
    override fun toString() = "RemoveFirst(toRemove=$toRemove, comparator=$comparator)"
}

data class RemoveLast<T>(
    override val toRemove: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<T>
) : RemoveBase<T>(toRemove = toRemove, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.removeAt(list.findLast(toRemove, comparator))
    override fun toString() = "RemoveLast(toRemove=$toRemove, comparator=$comparator)"
}

data class RemoveAt<T>(
    override val removalIndexes: List<Int>,
    val parent: Change<T>
) : RemoveBase<T>(removalIndexes = removalIndexes, parent = parent) {
    override fun applyTo(list: List<T>) = list.removeAt(removalIndexes)
    override fun toString(): String = "RemoveAt(indexes=$removalIndexes)"
}

data class RemoveIf<T>(
    override val filter: Predicate<T>,
    val parent: Change<T>
) : RemoveBase<T>(filter = filter, parent = parent) {
    override fun applyTo(list: List<T>): List<T> = list.removeAt(list.findMatches(filter))
    override fun toString() = "RemoveIf(filter=$filter)"
}

abstract class RetainBase<T>(
    protected open val toRetain: List<T> = emptyList(),
    protected open val comparator: Comparator<T>? = null,
    protected open val retainedIndexes: List<Int> = emptyList(),
    protected open val filter: Predicate<T>? = null,
    parent: Change<T>
) : EvolvedChange<T>(parent), Effect<T>

data class RetainAll<T>(
    override val toRetain: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<T>
) : RetainBase<T>(toRetain = toRetain, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(list.findAll(toRetain, comparator))
    override fun toString() = "RetainAll(toRetain=$toRetain, comparator=$comparator)"
}

data class RetainFirst<T>(
    override val toRetain: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<T>
) : RetainBase<T>(toRetain = toRetain, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(list.findFirst(toRetain, comparator))
    override fun toString() = "RetainFirst(toRetain=$toRetain, comparator=$comparator)"
}

data class RetainLast<T>(
    override val toRetain: List<T>,
    override val comparator: Comparator<T>,
    val parent: Change<T>
) : RetainBase<T>(toRetain = toRetain, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(list.findLast(toRetain, comparator))
    override fun toString() = "RetainLast(toRetain=$toRetain, comparator=$comparator)"
}

data class RetainIf<T>(
    override val filter: Predicate<T>,
    val parent: Change<T>
) : RetainBase<T>(filter = filter, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(list.findMatches(filter))
    override fun toString() = "RetainIf(filter=$filter)"
}

data class RetainAt<T>(
    override val retainedIndexes: List<Int>,
    val parent: Change<T>
) : RetainBase<T>(retainedIndexes = retainedIndexes, parent = parent) {
    override fun applyTo(list: List<T>) = list.retainAt(retainedIndexes)
}

abstract class SetBase<T>(
    protected open val replacingMap: Map<T, T>? = null,
    protected open val replacingValue: T? = null,
    protected open val comparator: Comparator<T>? = null,
    protected open val replacingIndexes: List<Int> = emptyList(),
    parent: Change<T>
) : EvolvedChange<T>(parent), Effect<T>

data class SetAll<T>(
    override val replacingMap: Map<T, T>,
    override val comparator: Comparator<T>,
    val parent: Change<T>
) : SetBase<T>(replacingMap = replacingMap, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.setAll(replacingMap, comparator)
    override fun toString() = "SetAll(toReplace=${replacingMap.keys}, replacing=${replacingMap.values}, comparator=$comparator)"
}

data class SetFirst<T>(
    override val replacingMap: Map<T, T>,
    override val comparator: Comparator<T>,
    val parent: Change<T>
) : SetBase<T>(replacingMap = replacingMap, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.setFirst(replacingMap, comparator)
    override fun toString() = "SetFirst(toReplace=${replacingMap.keys}, replacing=${replacingMap.values}, comparator=$comparator)"
}

data class SetLast<T>(
    override val replacingMap: Map<T, T>,
    override val comparator: Comparator<T>,
    val parent: Change<T>
) : SetBase<T>(replacingMap = replacingMap, comparator = comparator, parent = parent) {
    override fun applyTo(list: List<T>) = list.setLast(replacingMap, comparator)
    override fun toString() = "SetLast(toReplace=${replacingMap.keys}, replacing=${replacingMap.values}, comparator=$comparator)"
}

