package fields

class FieldTestClass(val field1: String, val field2: Int) {
    val FieldTestClass.extensionPropertyInsideClass: Int
            get() = 20

}