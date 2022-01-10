package flakyTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.TestAbortedException;

@RequiredArgsConstructor
class FlakyExceptionHandler implements TestExecutionExceptionHandler {

    private final int currentAttempt;
    private final int maxAttempts;

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (currentAttempt >= maxAttempts) {
            throw throwable;
        } else {
            throw new TestAbortedException("Test attempt failed (attempt " + currentAttempt + "/" + maxAttempts + ")");
        }
    }
}
