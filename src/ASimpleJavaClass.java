import java.lang.reflect.Method;

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

    }

}
