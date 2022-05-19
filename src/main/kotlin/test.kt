interface A {
    fun method1() = "1st method"
    fun method2() = "2nd method"
}

interface B : A {
    override fun method1() = "method 1 by B"
    override fun method2() = "method 2 by B"
    fun method3() = "method 3"
}

class Test1 : A

open class Test2 : B

class Limited : Test2()