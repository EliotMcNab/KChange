package changeAPI.actions

import changeAPI.changes.Change
import changeAPI.changes.EvolvedChange

interface Action<T>{
    fun inform(list: List<T>)
}

class Display<T>(
    parent: Change<T>
) : EvolvedChange<T>(parent), Action<T> {
    override fun inform(list: List<T>) { println(list) }
}