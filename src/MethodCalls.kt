import kotlin.reflect.KFunction

fun main(args: Array<String>) {
    val string = "a"

    val objectMethodReference = string::hashCode
    val classMethodReference = String::hashCode


    println("objectMethodReference: ${objectMethodReference()}\nclassMethodReference ${classMethodReference("b")}\n")
    printParameters(objectMethodReference)
    printParameters(classMethodReference)
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

