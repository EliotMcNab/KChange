package changeAPI.actions

import changeAPI.changes.Change
import changeAPI.changes.EvolvedChange

interface Action<T>{
    fun inform(list: List<T>)
}