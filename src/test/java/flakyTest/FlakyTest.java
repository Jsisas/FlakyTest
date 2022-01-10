package flakyTest;

import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TestTemplate
@Target({ElementType.METHOD})
@ExtendWith(FlakyExtension.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlakyTest {
    int maxAttempts();
}
