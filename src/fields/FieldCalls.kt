package fields

import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.declaredMembers


fun printAllProperties(klass: KClass<*>) {
    // Returns all functions and properties of that class
    // Doesn't contain any reference to external extension properties. Those type of
    // properties are hold inside the .class file generated corresponding to the file
    // in which they were declared
    klass.declaredMembers.filter { it is KProperty }.forEach {
        println("Name: ${it.name} Class: ${it.javaClass}")
    }
}

val FieldTestClass.extensionProperty: Int
    get() = 10