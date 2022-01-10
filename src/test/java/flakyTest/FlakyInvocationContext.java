package flakyTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import java.util.List;

@RequiredArgsConstructor
class FlakyInvocationContext implements TestTemplateInvocationContext {

    private final String displayName;
    private final int maxAttempts;
    private final int currentAttempt;
    private final Store store;

    @Override
    public String getDisplayName(int invocationIndex) {
        return displayName + " (attempt " +
                currentAttempt + "/" + maxAttempts + ")";
    }

    @Override
    public List<Extension> getAdditionalExtensions() {
        return List.of(new FlakyAfterTestExecutionCallback(store), new FlakyExceptionHandler(currentAttempt, maxAttempts));
    }
}
