package flakyTest;

import lombok.Builder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static flakyTest.FlakyExtension.TEST_PASSED;

@Builder
class FlakyTestTemplateIterator implements Iterator<TestTemplateInvocationContext> {

    private final String displayName;
    private final int maxAttempts;
    private final ExtensionContext.Store store;

    int currentAttempt;

    @Override
    public boolean hasNext() {
        return currentAttempt < maxAttempts && !store.get(TEST_PASSED, Boolean.class);
    }

    @Override
    @SneakyThrows
    public TestTemplateInvocationContext next() {
        if (hasNext()) {
            currentAttempt += 1;
            return new FlakyInvocationContext(displayName, maxAttempts, currentAttempt, store);
        }
        throw new NoSuchElementException();
    }
}
