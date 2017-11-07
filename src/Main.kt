import fields.FieldTestClass
import fields.externalExtensionProperties
import fields.printAllProperties
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

    println("\n\nProperties")
    printAllProperties(FieldTestClass::class)
    externalExtensionProperties()

}