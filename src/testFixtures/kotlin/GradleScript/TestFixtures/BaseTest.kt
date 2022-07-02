package GradleScript.TestFixtures

import GradleScript.TestFixtures.Extensions.ContextExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ContextExtension::class)
interface BaseTest {
	val __test_storage: MutableMap<Any?, Any?>

	fun <T> __test_storage_get(key: Any?): T? {
		return __test_storage[key] as T?
	}
	fun <T> __test_storage_get(key: Any?, defaultValue: T): T {
		val result = __test_storage[key] ?: return defaultValue
		return result as T
	}
	fun <T> __test_storage_get_late_init(key: Any?): T {
		return __test_storage[key] as T ?: throw IllegalStateException("Late init variable")
	}
	fun <T> __test_storage_set(key: Any?, value: T?) {
		__test_storage[key] = value
	}
}
