package GradleScript.TestFixtures.Extensions

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.*
import java.lang.reflect.Method

data class TestContext(
	internal val __extension_context: ExtensionContext
) {
	val uniqueId: String
		get() = __extension_context.uniqueId
	val displayName: String
		get() = __extension_context.displayName
	val tags: Set<String>
		get() = __extension_context.tags
	val testClass: Class<*>?
		get() = __extension_context.testClass.orElse(null)
	val testMethod: Method
		get() = __extension_context.testMethod.orElse(null)
	val testInstance: Any?
		get() = __extension_context.testInstance.orElse(null)
	val testInstances: TestInstances?
		get() = __extension_context.testInstances.orElse(null)
	val testLifecycle: TestInstance.Lifecycle?
		get() = __extension_context.testInstanceLifecycle.orElse(null)
	val executionException: Throwable?
		get() = __extension_context.executionException.orElse(null)
}

class ContextRetrieverExtension: Extension, ParameterResolver {
	override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
		return parameterContext.parameter.type == TestContext::class.java
	}
	override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
		return TestContext(extensionContext)
	}
}
