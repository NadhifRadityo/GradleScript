package GradleScript.TestFixtures.Extensions

import GradleScript.Strategies.ClassUtils.classForName0
import org.gradle.internal.impldep.org.junit.platform.commons.util.ReflectionUtils.makeAccessible
import org.junit.jupiter.api.extension.*
import org.junit.jupiter.api.io.TempDir
import org.junit.platform.commons.support.AnnotationSupport.findAnnotatedMethods
import org.junit.platform.commons.support.HierarchyTraversalMode
import org.junit.platform.commons.util.ExceptionUtils.throwAsUncheckedException
import org.junit.platform.commons.util.ReflectionUtils
import java.io.File
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method
import java.nio.file.Path

annotation class TempDirSetter

class TempDirectorySetterMethodExtension: Extension, BeforeAllCallback, BeforeEachCallback {
	companion object {
		private val CLASS_TempDirectory: Class<*> = classForName0<Any>("org.junit.jupiter.engine.extension.TempDirectory")
		private val METHOD_TempDirectory_getPathOrFile: Method = CLASS_TempDirectory.getDeclaredMethod("getPathOrFile", AnnotatedElement::class.java, Class::class.java, ExtensionContext::class.java)
		private val INSTANCE: Any

		init {
			METHOD_TempDirectory_getPathOrFile.trySetAccessible()
			val CONSTRUCTOR_TempDirectory = CLASS_TempDirectory.getDeclaredConstructor()
			CONSTRUCTOR_TempDirectory.trySetAccessible()
			INSTANCE = CONSTRUCTOR_TempDirectory.newInstance()
		}
	}

	override fun beforeAll(context: ExtensionContext) {
		injectStaticMethods(context, context.requiredTestClass)
	}
	override fun beforeEach(context: ExtensionContext) {
		context.requiredTestInstances.allInstances.forEach { injectInstanceMethods(context, it) }
	}

	private fun injectStaticMethods(context: ExtensionContext, testClass: Class<*>) {
		injectMethods(context, null, testClass, ReflectionUtils::isStatic)
	}
	private fun injectInstanceMethods(context: ExtensionContext, instance: Any) {
		injectMethods(context, instance, instance.javaClass, ReflectionUtils::isNotStatic)
	}

	private fun injectMethods(context: ExtensionContext, testInstance: Any?, testClass: Class<*>, predicate: (Method) -> Boolean) {
		findAnnotatedMethods(testClass, TempDirSetter::class.java, HierarchyTraversalMode.BOTTOM_UP).filter(predicate).forEach { method ->
			assertSupportedType("method", method.parameterTypes)
			try {
				// Apparently `getPathOrFile` is not static. Duh!
				val pathOrFile = METHOD_TempDirectory_getPathOrFile.invoke(INSTANCE, method, method.parameterTypes[0], context)
				makeAccessible(method).invoke(testInstance, pathOrFile)
			} catch(t: Throwable) {
				throwAsUncheckedException(t)
			}
		}
	}

	private fun assertSupportedType(target: String, parameters: Array<Class<*>>) {
		if(parameters.size != 1)
			throw ExtensionConfigurationException("Can only resolve @TempDirSetter $target with only 1 parameter but was: ${parameters.size}")
		if(parameters[0] != Path::class.java && parameters[0] != File::class.java)
			throw ExtensionConfigurationException("Can only resolve @TempDirSetter $target of type ${Path::class.java} or ${File::class.java} but was: ${parameters[0].name}")
	}
}
