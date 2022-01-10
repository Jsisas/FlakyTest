package flakyTest;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.junit.platform.commons.util.AnnotationUtils;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterators.spliteratorUnknownSize;

class FlakyExtension implements TestTemplateInvocationContextProvider {

    public static String TEST_PASSED = "testPassed";

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        FlakyTest annotation = getRetryAnnotation(context);
        context.getStore(storeNamespace(context)).put(TEST_PASSED, false);

        FlakyTestTemplateIterator retryTestTemplateIterator = FlakyTestTemplateIterator.builder()
                .displayName(context.getDisplayName())
                .maxAttempts(annotation.maxAttempts())
                .store(context.getStore(storeNamespace(context)))
                .build();

        Spliterator<TestTemplateInvocationContext> spliterator = spliteratorUnknownSize(retryTestTemplateIterator, Spliterator.ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return true;
    }

    private ExtensionContext.Namespace storeNamespace(ExtensionContext context) {
        return ExtensionContext.Namespace.create(context.getRequiredTestClass(), context.getRequiredTestMethod());
    }

    private FlakyTest getRetryAnnotation(ExtensionContext context) {
        return AnnotationUtils.findAnnotation(context.getRequiredTestMethod(), FlakyTest.class)
                .orElseThrow(() -> new IllegalStateException("The test method must be annotated with @Retry"));
    }
}
