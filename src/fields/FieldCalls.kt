package fields

import java.io.File
import java.net.URLClassLoader
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

fun externalExtensionProperties() {
    val classGeneratedPath = File("out/production/kotlin-reflection-example")

    val urls = arrayOf(classGeneratedPath.toURI().toURL())

    val loader = URLClassLoader(urls)

    val klass = loader.loadClass("fields.FieldCallsKt")
    val method = klass.getDeclaredMethod("getExtensionProperty", FieldTestClass::class.java)

    val a = FieldTestClass("a", 12)

    println(method.invoke(null, a))


}

val FieldTestClass.extensionProperty: Int
    get() = 10