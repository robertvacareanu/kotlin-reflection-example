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

/**
 * Searches for the generated .class file where the extension function will be generated as a static function
 */
fun externalExtensionProperties() {
    val classGeneratedPath = File("out/production/kotlin-reflection-example")

    val urls = arrayOf(classGeneratedPath.toURI().toURL())

    val loader = URLClassLoader(urls)

    // The static function is generated inside a .class file that matches the name of the file in which it was written
    // In this case it is FieldCallsKt. Note that in the case of kotlin files, they are generated with a "Kt" at the end
    val klass = loader.loadClass("fields.FieldCallsKt")
    val method = klass.getDeclaredMethod("getExtensionProperty", FieldTestClass::class.java)

    val a = FieldTestClass("a", 12)

    println(method.invoke(null, a))


}

val FieldTestClass.extensionProperty: Int
    get() = 10