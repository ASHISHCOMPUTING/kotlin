// !API_VERSION: 1.3
// JVM_TARGET: 1.8
// WITH_RUNTIME
// FULL_JDK

interface Test<T> {
    @kotlin.annotations.JvmDefault
    fun test(p: T): T {
        return null!!
    }

    fun foo(p: T): T {
        return null!!
    }
}

interface Test2: Test<String> {
    @kotlin.annotations.JvmDefault
    override fun test(p: String): String {
        return p
    }

    override fun foo(p: String): String {
        return p
    }
}

class TestClass : Test2

fun box(): String {
    checkNoMethod(Test2::class.java, "foo", Any::class.java)
    checkMethodExists(Test2::class.java, "test", Any::class.java)

    checkNoMethod(TestClass::class.java, "test", Any::class.java)
    checkMethodExists(TestClass::class.java, "foo", String::class.java)
    checkMethodExists(TestClass::class.java, "foo", Any::class.java)

    return "OK"
}

fun checkNoMethod(clazz: Class<*>, name: String, vararg parameterTypes: Class<*>) {
    try {
        clazz.getDeclaredMethod(name, *parameterTypes)
    }
    catch (e: NoSuchMethodException) {
        return
    }
    throw AssertionError("fail: method $name was found in " + clazz)
}

fun checkMethodExists(clazz: Class<*>, name: String, vararg parameterTypes: Class<*>) {
    try {
        clazz.getDeclaredMethod(name, *parameterTypes)
        return
    }
    catch (e: NoSuchMethodException) {
        throw AssertionError("fail: method $name was not found in " + clazz, e)
    }

}
