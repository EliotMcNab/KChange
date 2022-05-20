package util

operator fun Number.compareTo(other: Number) = when {
    this is Byte   && other is Byte   -> this.compareTo(other)
    this is Short  && other is Short  -> this.compareTo(other)
    this is Int    && other is Int    -> this.compareTo(other)
    this is Long   && other is Long   -> this.compareTo(other)
    this is Float  && other is Float  -> this.compareTo(other)
    this is Double && other is Double -> this.compareTo(other)
    else                              -> -1
}