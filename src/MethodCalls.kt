import kotlin.reflect.KFunction

fun main(args: Array<String>) {
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


    /**
     * Will print:
     * a and b
     */
    string.myExtensionFunction("b")
    val extensionFunctionObject = string::myExtensionFunction
    extensionFunctionObject("b")


    val javaClass = MethodCallsJava()
    val javaFunction = javaClass::javaMethod
    val javaFunctionStatic = MethodCallsJava::javaMethod
    javaFunction()
    javaFunctionStatic(javaClass)

    javaClass.callKotlinFun()
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

fun String.myExtensionFunction(string: String) {
    println("$this and $string")
}

class TestClass {
    fun kotlinFunction() {
        println("Inside Kotlin function")
    }
    companion object {
        fun staticKotlinFunction() {
            println("Inside static Kotlin function")
        }
    }

}

