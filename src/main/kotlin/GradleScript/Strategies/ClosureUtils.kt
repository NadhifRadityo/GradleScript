package GradleScript.Strategies

import GradleScript.Common.context
import GradleScript.Common.groovyKotlinCaches
import GradleScript.Common.lastContext
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.klambdaNToClosure
import GradleScript.GroovyKotlinInteroperability.KotlinClosure
import groovy.lang.Closure

object ClosureUtils {
	@JvmStatic private var cache: GroovyKotlinCache<ClosureUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(ClosureUtils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	@ExportGradle @JvmStatic
	fun lash(that: Any, closure: KotlinClosure, vararg prependArgs: Any?): Closure<*> {
		return klambdaNToClosure(lambda@{ args ->
			val finalArgs = arrayOfNulls<Any?>(prependArgs.size + args.size)
			System.arraycopy(prependArgs, 0, finalArgs, 0, prependArgs.size)
			System.arraycopy(args, 0, finalArgs, prependArgs.size, args.size)
			return@lambda context(that) lambda1@{ return@lambda1 closure.call(*finalArgs) }
		}, "lash ${closure.name}")
	}
	@ExportGradle @JvmStatic
	fun lash(closure: KotlinClosure): Closure<*> {
		return lash(lastContext().that, closure)
	}

	@ExportGradle @JvmStatic
	fun bind(self: Any?, closure: KotlinClosure, vararg prependArgs: Any?): Closure<*> {
		return klambdaNToClosure(lambda@{ args ->
			val finalArgs = arrayOfNulls<Any?>(1 + prependArgs.size + args.size)
			finalArgs[0] = self
			System.arraycopy(prependArgs, 0, finalArgs, 1, prependArgs.size)
			System.arraycopy(args, 0, finalArgs, prependArgs.size + 1, args.size)
			return@lambda closure.call(*finalArgs)
		}, "bind ${closure.name}")
	}
}
