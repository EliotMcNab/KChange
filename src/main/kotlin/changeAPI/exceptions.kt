package changeAPI

class NoneExistentChangeException(errorMessage: String = "") : RuntimeException(errorMessage)
class UnsupportedOperationException(
    errorMessage: String = "This operation is not supported by the Operator on which it is being called"
): RuntimeException(errorMessage)