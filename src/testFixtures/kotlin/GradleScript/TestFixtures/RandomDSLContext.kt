package GradleScript.TestFixtures

import GradleScript.Strategies.StringUtils.ALPHANUMERIC
import GradleScript.TestFixtures.Extensions.BaseExtension
import GradleScript.TestFixtures.Tests.BaseTest
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestInstancePostProcessor
import org.junit.jupiter.api.extension.TestInstancePreDestroyCallback
import java.util.*
import kotlin.random.asKotlinRandom

class RandomDSLContextExtension: BaseExtension(), TestInstancePostProcessor, TestInstancePreDestroyCallback {
	override fun construct(context: ExtensionContext) { }
	override fun destruct(context: ExtensionContext) { }

	override fun postProcessTestInstance(instance: Any, context: ExtensionContext) {
		if(instance !is RandomDSLContext || instance.__test_storage_get<Random>("random") != null)
			return
		val random = Random()
		instance.__test_storage_set("random", random)
		instance.__test_storage_set("krandom", random.asKotlinRandom())
	}
	override fun preDestroyTestInstance(context: ExtensionContext) {
		val instance = context.requiredTestInstance
		if(instance !is RandomDSLContext)
			return
		instance.__test_storage_set("random", null)
		instance.__test_storage_set("krandom", null)
	}
}

@ExtendWith(RandomDSLContextExtension::class)
interface RandomDSLContext: BaseTest {
	val random: Random
		get() = __test_storage_get_late_init("random")
	val krandom: kotlin.random.Random
		get() = __test_storage_get_late_init("krandom")

	fun <R> withRandom(seed: Long, expression: () -> R): R {
		val oldRandom = random
		val oldKrandom = krandom
		val random = Random(seed)
		__test_storage_set("random", random)
		__test_storage_set("krandom", random.asKotlinRandom())
		try {
			return expression()
		} finally {
			__test_storage_set("random", oldRandom)
			__test_storage_set("krandom", oldKrandom)
		}
	}

	fun nextBoolean(): Boolean { return random.nextBoolean() }
	fun nextBytes(bytes: ByteArray) { random.nextBytes(bytes) }
	fun nextDouble(): Double { return random.nextDouble() }
	fun nextFloat(): Float { return random.nextFloat() }
	fun nextInt(): Int { return random.nextInt() }
	fun nextInt(bound: Int): Int { return random.nextInt(bound) }
	fun nextLong(): Long { return random.nextLong() }
	fun randomString(length: Int = 8, charset: CharArray = ALPHANUMERIC.toCharArray()): String {
		return (0 until length).map { charset.random(krandom) }.joinToString("")
	}
}
