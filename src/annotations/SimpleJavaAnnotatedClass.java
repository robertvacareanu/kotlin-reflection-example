package annotations;

import java.lang.annotation.Annotation;

@ExtraData(data = "Java")
public class SimpleJavaAnnotatedClass {
    public void accessKotlinAnnotation() {
        System.out.println("Access kotlin annotations from java: ");
        SimpleKotlinAnnotatedClass simpleKotlinAnnotatedClass = new SimpleKotlinAnnotatedClass();
        for(Annotation annotation: SimpleKotlinAnnotatedClass.class.getAnnotations()) {
            System.out.println(annotation.annotationType());
        }
    }
}