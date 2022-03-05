package GradleScript.IntegrationTest

import GradleScript.GroovyKotlinInteroperability.GroovyManipulation
import GradleScript.Strategies.ClassUtils.classForName
import GradleScript.Strategies.ClassUtils.currentClassFile
import GradleScript.Strategies.ClassUtils.metaClassFor
import GradleScript.Strategies.ClassUtils.overrideFinal
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ClassUtilsTest: BaseIntegrationTest() {

	@Test
	fun `can get class object with valid canonical path`() {
		val result = classForName<BaseIntegrationTest>("GradleScript.IntegrationTest.ClassUtilsTest")
		assertEquals(ClassUtilsTest::class.java, result)
	}

	@Test
	fun `cannot get class object with invalid canonical path`() {
		val result = classForName<BaseIntegrationTest>("GradleScript.IntegrationTest.ClassUtilsTestInvalid")
		assertEquals(null, result)
	}

	@Test
	fun `can get groovy metaclass object with object`() {
		val obj = GroovyManipulation.DummyGroovyObject()
		obj.__start__()
		val result = metaClassFor(obj)
		assertEquals(obj.metaClass, result)
	}

	class ClassWithFinalField {
		// Bypass compile time optimization
		val targetField: Int = run { "___".substring(3).length }
	}
	@Test
	fun `can modify final field`() {
		val instance = ClassWithFinalField()
		val originalValue = instance.targetField
		overrideFinal(instance, ClassWithFinalField::class.java.getDeclaredField("targetField"), 0xBEEF)
		val newValue = instance.targetField
		assertNotEquals(originalValue, newValue)
		assertEquals(0xBEEF, newValue)
	}

	@Test
	fun `can get current class file`() {
		val classFile = currentClassFile(ClassUtilsTest::class.java)
		assertNotNull(classFile)
	}
}
