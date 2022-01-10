package flakyTest;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import static flakyTest.FlakyExtension.TEST_PASSED;

@RequiredArgsConstructor
class FlakyAfterTestExecutionCallback implements AfterTestExecutionCallback {

    private final Store store;

    @Override
    public void afterTestExecution(ExtensionContext context) {
        store.put(TEST_PASSED, hasTestPassed(context));
    }

    private boolean hasTestPassed(ExtensionContext context){
        return context.getExecutionException().isEmpty();
    }
}
