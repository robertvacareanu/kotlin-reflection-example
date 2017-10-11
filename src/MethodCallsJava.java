import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by robert on 10/11/17.
 */
public class MethodCallsJava {

    public void javaMethod() {
        System.out.println("Inside java method of MethodCallsJava");
    }

    public void callKotlinFun() {
        try {
            Method kotlinFunction = TestClass.class.getMethod("kotlinFunction");
            TestClass kotlinTestClass = new TestClass();
            kotlinFunction.invoke(kotlinTestClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
