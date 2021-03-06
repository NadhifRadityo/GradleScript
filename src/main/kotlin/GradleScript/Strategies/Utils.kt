package GradleScript.Strategies

import GradleScript.Common.groovyKotlinCaches
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache

object Utils {
	@JvmStatic private var cache: GroovyKotlinCache<Utils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(Utils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	@JvmStatic
	internal fun __invalid_type(): Throwable {
		throw Error("Invalid type")
	}
	@JvmStatic
	internal fun __must_not_happen(): Throwable {
		throw Error("Must not happen")
	}
	@JvmStatic
	internal fun <T> __unimplemented(): T {
		throw Error("Unimplemented")
	}
}
