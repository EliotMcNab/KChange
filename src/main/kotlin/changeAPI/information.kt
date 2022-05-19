package changeAPI

interface Informer<T>{
    fun inform(list: List<T>)
}

class Display<T>(
    parent: Change<T>
) : EvolvedChange<T>(parent), Informer<T> {
    override fun inform(list: List<T>) = println(list)
}