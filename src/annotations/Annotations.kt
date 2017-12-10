package annotations

fun kotlinAccessAnnotation() {
    val simpleAnnotatedClass = SimpleKotlinAnnotatedClass()
    simpleAnnotatedClass::class.annotations.forEach(::println)
    val simpleJavaClasAnnotated = SimpleJavaAnnotatedClass()
    simpleJavaClasAnnotated::class.annotations.forEach(::println)
}

fun javaAccessAnnotations() {
    SimpleJavaAnnotatedClass().accessKotlinAnnotation()
}