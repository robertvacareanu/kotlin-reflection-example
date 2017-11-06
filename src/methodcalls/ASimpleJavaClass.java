package methodcalls;

import java.io.File;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Optional;

public class ASimpleJavaClass {

    public void aSimpleJavaMethod() {
        System.out.println("Inside aSimpleJavaMethod of ASimpleJavaClass" + this.toString());
    }

    public void callKotlinFunFromJava() {
        try {
            Method kotlinFunction = TestClass.class.getMethod("aSimpleKotlinFunction");
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

    public void callExtensionFunFromJava() {
        // Path to where are .class files generated
        File generatedClassPath = new File("out/production/kotlin-reflection-example");
        try {
            URL url = generatedClassPath.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader classLoader = new URLClassLoader(urls);

            Class<?> kotlinGeneratedClass = classLoader.loadClass("methodcalls.MethodCallsKt");

            // Extension functions are compiled as static function that take as argument the object on which they are invoked
            Method kotlinExtensionFunction = kotlinGeneratedClass.getMethod("anExtensionFunction", TestClass.class);
            TestClass kotlinTestClass = new TestClass();
            // The first parameter represents the object on which the method is called. Since
            // extension functions are actually static function, this argument is ignored
            // The second argument is a varargs corresponding to the parameters the function takes
            kotlinExtensionFunction.invoke(null, kotlinTestClass);



        } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void staticKotlinFunLoadClass() {
        // Path to where are .class files generated
        File generatedClassPath = new File("out/production/kotlin-reflection-example");
        try {
            URL url = generatedClassPath.toURI().toURL();
            URL[] urls = new URL[]{url};


            // Use url to be able to load from a different path
            ClassLoader classLoader = new URLClassLoader(urls);


            // The name of the generated class is <class_name>$Companion
            Class<?> kotlinCompanionGeneratedClass = classLoader.loadClass("methodcalls.TestClass$Companion");

            invokeStaticKotlinFunction(kotlinCompanionGeneratedClass);

        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void staticKotlinFunFromField() {
        // Result of toGenericString: public static final methodcalls.TestClass$Companion methodcalls.TestClass.Companion
        Optional<Field> fieldClass = Arrays.stream(TestClass.class.getDeclaredFields()).filter((Field f) -> f.getName().equals("Companion")).findFirst();

        if (fieldClass.isPresent()) {
            Class<?> companionClass = fieldClass.get().getType();
            invokeStaticKotlinFunction(companionClass);
        }

    }

    private void invokeStaticKotlinFunction(Class<?> kotlinCompanionGeneratedClass)  {
        // Get the method to be called using reflection
        try {
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
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}