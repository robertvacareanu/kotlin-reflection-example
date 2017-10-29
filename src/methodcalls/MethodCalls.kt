package methodcalls

import kotlin.reflect.KFunction

fun functionReferenceExample() {
    val string = "a"
    val objectMethodReference = string::hashCode
    val classMethodReference = String::hashCode


    /**
     * Will print:
     * objectMethodReference: 97
     * classMethodReference: 98
     *
     */
    println("objectMethodReference: ${objectMethodReference()}\nclassMethodReference ${classMethodReference("b")}\n")

    /**
     * Will print:
     * Parameters for hashCode:
     *      No parameters.
     * Returns: kotlin.Int
     */
    printParameters(objectMethodReference)


    /**
     * Will print:
     * Parameters for hashCode:
     *  	1. kotlin.Any
     *Returns: kotlin.Int
     */
    printParameters(classMethodReference) // take a parameter a string on which the specified method will be called
}


fun printParameters(function: KFunction<*>) {
    var nr = 1
    println("Parameters for ${function.name}: ")
    for (param in function.parameters) {
        println("\t${nr++}. ${param.type}${if (param.isOptional) " is an optional parameter" else ""}")
    }
    if (nr == 1) {
        println("\tNo parameters. ")
    }
    println("Returns: ${function.returnType}\n")

}

fun extensionFunctionExample() {
    val string = "a"
    /**
     * Will print:
     * a and b
     */
    string.myExtensionFunction("b")
    val extensionFunctionObject = string::myExtensionFunction
    extensionFunctionObject("b")
}

fun String.myExtensionFunction(string: String) {
    println("$this and $string")
}

fun TestClass.anExtensionFunction() {
    println("Extension function to methodcalls.TestClass")
}

fun kotlinCallsJava() {
    val javaClass = ASimpleJavaClass()

    // Contrary to java, in kotlin ou can get the reference to a method in two ways:
    // 1. By using an actual object of the class in which the method is defined
    val javaFunction = javaClass::aSimpleJavaMethod
    // 2. By using the class
    val javaFunctionStatic = ASimpleJavaClass::aSimpleJavaMethod
    // The difference comes when you will invoked them:
    // 1. By using the first method you can simply invoke the method or call it as a regular function in kotlin since it is implied that it will act
    // on the object used to get a reference to it
    javaFunction()
    // 2. By using the second method you have to supply when you call it the object it will act on. This difference can be highlighted by
    // calling the same object of type Method but giving as argument a different object of type ASimpleJavaClass. This will print different
    // hashcode
    javaFunctionStatic(javaClass)
    javaFunctionStatic(ASimpleJavaClass())
}

fun javaCallsKotlin() {
    val javaClass = ASimpleJavaClass()
    javaClass.callKotlinFunFromJava()
    javaClass.callKotlinStaticFunFromJava()
    javaClass.callExtensionFunFromJava()
}

class TestClass {
    fun aSimpleKotlinFunction() {
        println("Inside Kotlin function $this")
    }

    companion object {
        fun staticKotlinFunction() {
            println("Inside static Kotlin function")
        }
    }

}