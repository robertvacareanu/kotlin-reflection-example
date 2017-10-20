import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Optional;

public class ASimpleJavaClass {

    public void javaMethod() {
        System.out.println("Inside java method of ASimpleJavaClass");
    }

    public void callKotlinFunFromJava() {
        try {
            Method kotlinFunction = TestClass.class.getMethod("kotlinFunction");
            TestClass kotlinTestClass = new TestClass();
            kotlinFunction.invoke(kotlinTestClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void callKotlinStaticFunFromJava() {
        staticKotlinFunLoadClass();
        staticKotlinFunFromField();

    }

    public void staticKotlinFunLoadClass() {
        File f = new File("out/production/kotlin-reflection-example");
        try {
            URL url = f.toURI().toURL();
            URL[] urls = new URL[]{url};


            // Use url to be able to load from a different path
            ClassLoader cl = new URLClassLoader(urls);


            // The name of the generated class is <class_name>$Companion
            Class<?> kotlinCompanionGeneratedClass = cl.loadClass("TestClass$Companion");

            invoke(kotlinCompanionGeneratedClass);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void staticKotlinFunFromField() {
        // Result of toGenericString: public static final TestClass$Companion TestClass.Companion
        Optional<Field> fieldClass = Arrays.stream(TestClass.class.getDeclaredFields()).filter((Field f) -> f.getName().equals("Companion")).findFirst();//

        if (fieldClass.isPresent()) {
            Class<?> companionClass = fieldClass.get().getType();
            try {
                invoke(companionClass);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void invoke(Class<?> kotlinCompanionGeneratedClass) throws Exception {
        // Get the method to be called using reflection
        Method staticKotlinFunction = kotlinCompanionGeneratedClass.getMethod("staticKotlinFunction");

        // The generated class contains a private constructor that takes one argument: kotlin.jvm.internal.DefaultConstructorMarker
        Constructor<?> kotlinCompanionConstructor = kotlinCompanionGeneratedClass.getConstructors()[0];

        // The constructor is private. It must be set accessible to be able to invoke it
        kotlinCompanionConstructor.setAccessible(true);

        // It takes a parameter: class kotlin.jvm.internal.DefaultConstructorMarker
        Parameter p = kotlinCompanionConstructor.getParameters()[0];
        Class parameterClass = p.getType();

        // The kotlin.jvm.internal.DefaultConstructorMarker class contains a 0 parameter private constructor
        Constructor parameterClassConstructor = parameterClass.getDeclaredConstructors()[0];
        parameterClassConstructor.setAccessible(true);
        Object defaultConstructorMarkerObject = parameterClassConstructor.newInstance();

        Object kotlinCompanionObject = kotlinCompanionConstructor.newInstance(defaultConstructorMarkerObject);
        staticKotlinFunction.invoke(kotlinCompanionObject);
    }

}
