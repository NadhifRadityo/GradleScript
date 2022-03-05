package GradleScript.Strategies

import GradleScript.Common.context
import GradleScript.Common.groovyKotlinCaches
import GradleScript.Common.lastContext
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambdaNToClosure
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
	fun lash(that: Any, closure: Closure<*>, vararg prependArgs: Any?): Closure<*> {
		return lambdaNToClosure(lambda@{ args ->
			val finalArgs = arrayOfNulls<Any?>(prependArgs.size + args.size)
			System.arraycopy(prependArgs, 0, finalArgs, 0, prependArgs.size)
			System.arraycopy(args, 0, finalArgs, prependArgs.size, args.size)
			return@lambda context(that) lambda1@{ return@lambda1 closure.call(*finalArgs) }
		}, "lash ${if(closure is KotlinClosure) closure.name else closure.toString()}")
	}
	@ExportGradle @JvmStatic
	fun lash(closure: Closure<*>): Closure<*> {
		return lash(lastContext().that, closure)
	}

	@ExportGradle @JvmStatic
	fun bind(self: Any?, closure: Closure<*>, vararg prependArgs: Any?): Closure<*> {
		return lambdaNToClosure(lambda@{ args ->
			val finalArgs = arrayOfNulls<Any?>(1 + prependArgs.size + args.size)
			finalArgs[0] = self
			System.arraycopy(prependArgs, 0, finalArgs, 1, prependArgs.size)
			System.arraycopy(args, 0, finalArgs, prependArgs.size + 1, args.size)
			return@lambda closure.call(*finalArgs)
		}, "bind ${if(closure is KotlinClosure) closure.name else closure.toString()}")
	}
}
