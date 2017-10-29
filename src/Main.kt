import methodcalls.extensionFunctionExample
import methodcalls.functionReferenceExample
import methodcalls.javaCallsKotlin
import methodcalls.kotlinCallsJava

fun main(args: Array<String>) {

    println("Function reference example")
    functionReferenceExample()

    println("\n\nExtension function example")
    extensionFunctionExample()

    println("\n\nKotlin calls java")
    kotlinCallsJava()

    println("\n\nJava calls kotlin")
    javaCallsKotlin()

}