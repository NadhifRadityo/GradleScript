package GradleScript.GroovyKotlinInteroperability

import GradleScript.Common.groovyKotlinCaches
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.Strategies.ClassUtils.metaClassFor
import GradleScript.Strategies.Utils.__invalid_type
import GradleScript.Strategies.Utils.__must_not_happen
import groovy.lang.*
import org.codehaus.groovy.reflection.CachedClass
import org.codehaus.groovy.reflection.ReflectionCache.getCachedClass
import org.codehaus.groovy.runtime.MethodClosure
import org.codehaus.groovy.runtime.metaclass.ClosureMetaClass
import org.codehaus.groovy.runtime.metaclass.MetaMethodIndex
import org.gradle.api.Project
import java.lang.reflect.Modifier
import kotlin.jvm.functions.FunctionN

object GroovyManipulation {
	@JvmStatic private var cache: GroovyKotlinCache<GroovyManipulation>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(GroovyManipulation)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	/*
	console.log(new Array(23).fill(null).map((v, i) => `@ExportGradle @JvmStatic
	fun closureToLambda${i}(closure: Closure<*>): fn${i} {
		return { ${new Array(i).fill(null).map((_v, _i) => `a${_i + 1}`).join(", ")} -> closure.call(${new Array(i).fill(null).map((_v, _i) => `a${_i + 1}`).join(", ")}) }
	}`).join("\n"))
	 */
	@ExportGradle @JvmStatic
	fun closureToLambda0(closure: Closure<*>): fn0 {
		return {  -> closure.call() }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda1(closure: Closure<*>): fn1 {
		return { a1 -> closure.call(a1) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda2(closure: Closure<*>): fn2 {
		return { a1, a2 -> closure.call(a1, a2) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda3(closure: Closure<*>): fn3 {
		return { a1, a2, a3 -> closure.call(a1, a2, a3) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda4(closure: Closure<*>): fn4 {
		return { a1, a2, a3, a4 -> closure.call(a1, a2, a3, a4) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda5(closure: Closure<*>): fn5 {
		return { a1, a2, a3, a4, a5 -> closure.call(a1, a2, a3, a4, a5) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda6(closure: Closure<*>): fn6 {
		return { a1, a2, a3, a4, a5, a6 -> closure.call(a1, a2, a3, a4, a5, a6) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda7(closure: Closure<*>): fn7 {
		return { a1, a2, a3, a4, a5, a6, a7 -> closure.call(a1, a2, a3, a4, a5, a6, a7) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda8(closure: Closure<*>): fn8 {
		return { a1, a2, a3, a4, a5, a6, a7, a8 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda9(closure: Closure<*>): fn9 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda10(closure: Closure<*>): fn10 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda11(closure: Closure<*>): fn11 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda12(closure: Closure<*>): fn12 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda13(closure: Closure<*>): fn13 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda14(closure: Closure<*>): fn14 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda15(closure: Closure<*>): fn15 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda16(closure: Closure<*>): fn16 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda17(closure: Closure<*>): fn17 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda18(closure: Closure<*>): fn18 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda19(closure: Closure<*>): fn19 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda20(closure: Closure<*>): fn20 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda21(closure: Closure<*>): fn21 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambda22(closure: Closure<*>): fn22 {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) }
	}
	@ExportGradle @JvmStatic
	fun closureToLambdaN(closure: Closure<*>, arity: Int): fnn {
		return object: fnn {
			override val arity: Int
				get() = arity

			override fun invoke(vararg args: Any?): Any? {
				return closure.call(*args)
			}
		}
	}

	/*
	console.log(new Array(23).fill().map((v, i) => `@ExportGradle @JvmStatic
	inline fun <${new Array(i).fill().map((_v, _i) => `reified a${_i + 1}`).join(", ")}${i == 0 ? "" : ", "}R> lambda${i}ToClosure(noinline lambda: (${new Array(i).fill().map((_v, _i) => `a${_i + 1}`).join(", ")}) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(${new Array(i).fill().map((_v, _i) => `a${_i + 1}::class.java`).join(", ")}),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(${new Array(i).fill().map((_v, _i) => `args[${_i + 1}] as a${_i + 1}`).join(", ")}) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}`).join("\n"))
	 */
	@ExportGradle @JvmStatic
	inline fun <R> lambda0ToClosure(noinline lambda: () -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda() }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, R> lambda1ToClosure(noinline lambda: (a1) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, R> lambda2ToClosure(noinline lambda: (a1, a2) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, R> lambda3ToClosure(noinline lambda: (a1, a2, a3) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, R> lambda4ToClosure(noinline lambda: (a1, a2, a3, a4) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, R> lambda5ToClosure(noinline lambda: (a1, a2, a3, a4, a5) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, R> lambda6ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, R> lambda7ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, R> lambda8ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, R> lambda9ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, R> lambda10ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, R> lambda11ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, R> lambda12ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, R> lambda13ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, R> lambda14ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, reified a15, R> lambda15ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java, a15::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14, args[15] as a15) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, reified a15, reified a16, R> lambda16ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java, a15::class.java, a16::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14, args[15] as a15, args[16] as a16) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, reified a15, reified a16, reified a17, R> lambda17ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java, a15::class.java, a16::class.java, a17::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14, args[15] as a15, args[16] as a16, args[17] as a17) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, reified a15, reified a16, reified a17, reified a18, R> lambda18ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java, a15::class.java, a16::class.java, a17::class.java, a18::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14, args[15] as a15, args[16] as a16, args[17] as a17, args[18] as a18) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, reified a15, reified a16, reified a17, reified a18, reified a19, R> lambda19ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java, a15::class.java, a16::class.java, a17::class.java, a18::class.java, a19::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14, args[15] as a15, args[16] as a16, args[17] as a17, args[18] as a18, args[19] as a19) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, reified a15, reified a16, reified a17, reified a18, reified a19, reified a20, R> lambda20ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java, a15::class.java, a16::class.java, a17::class.java, a18::class.java, a19::class.java, a20::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14, args[15] as a15, args[16] as a16, args[17] as a17, args[18] as a18, args[19] as a19, args[20] as a20) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, reified a15, reified a16, reified a17, reified a18, reified a19, reified a20, reified a21, R> lambda21ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java, a15::class.java, a16::class.java, a17::class.java, a18::class.java, a19::class.java, a20::class.java, a21::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14, args[15] as a15, args[16] as a16, args[17] as a17, args[18] as a18, args[19] as a19, args[20] as a20, args[21] as a21) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	inline fun <reified a1, reified a2, reified a3, reified a4, reified a5, reified a6, reified a7, reified a8, reified a9, reified a10, reified a11, reified a12, reified a13, reified a14, reified a15, reified a16, reified a17, reified a18, reified a19, reified a20, reified a21, reified a22, R> lambda22ToClosure(noinline lambda: (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) -> R, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += object: KotlinClosure.Overload(arrayOf(a1::class.java, a2::class.java, a3::class.java, a4::class.java, a5::class.java, a6::class.java, a7::class.java, a8::class.java, a9::class.java, a10::class.java, a11::class.java, a12::class.java, a13::class.java, a14::class.java, a15::class.java, a16::class.java, a17::class.java, a18::class.java, a19::class.java, a20::class.java, a21::class.java, a22::class.java),
			{ val args = it[0] as? Array<*> ?: arrayOf<Any?>(); lambda(args[1] as a1, args[2] as a2, args[3] as a3, args[4] as a4, args[5] as a5, args[6] as a6, args[7] as a7, args[8] as a8, args[9] as a9, args[10] as a10, args[11] as a11, args[12] as a12, args[13] as a13, args[14] as a14, args[15] as a15, args[16] as a16, args[17] as a17, args[18] as a18, args[19] as a19, args[20] as a20, args[21] as a21, args[22] as a22) }) {
			override fun toString(): String { return lambda.toString() }
		}
		return result
	}
	@ExportGradle @JvmStatic
	fun lambdaNToClosure(lambda: (Array<out Any?>) -> Any?, name: String = lambda.toString()): Closure<*> {
		val result = KotlinClosure(name)
		result.overloads += KotlinClosure.KLambdaOverload(lambda)
		return result
	}

	@ExportGradle @JvmStatic
	fun closureToLambda(closure: Closure<*>, target: Class<Function<*>>): Function<*> {
		run {
			/*
			console.log(new Array(23).fill(null).map((v, i) => `if(Function${i}::class.java.isAssignableFrom(target)) return closureToLambda${i}<${new Array(i).fill(null).map((_v, _i) => `Any?`).join(", ")}, R>(closure)`).join("\n"))
			 */
			if(Function0::class.java.isAssignableFrom(target)) return closureToLambda0(closure)
			if(Function1::class.java.isAssignableFrom(target)) return closureToLambda1(closure)
			if(Function2::class.java.isAssignableFrom(target)) return closureToLambda2(closure)
			if(Function3::class.java.isAssignableFrom(target)) return closureToLambda3(closure)
			if(Function4::class.java.isAssignableFrom(target)) return closureToLambda4(closure)
			if(Function5::class.java.isAssignableFrom(target)) return closureToLambda5(closure)
			if(Function6::class.java.isAssignableFrom(target)) return closureToLambda6(closure)
			if(Function7::class.java.isAssignableFrom(target)) return closureToLambda7(closure)
			if(Function8::class.java.isAssignableFrom(target)) return closureToLambda8(closure)
			if(Function9::class.java.isAssignableFrom(target)) return closureToLambda9(closure)
			if(Function10::class.java.isAssignableFrom(target)) return closureToLambda10(closure)
			if(Function11::class.java.isAssignableFrom(target)) return closureToLambda11(closure)
			if(Function12::class.java.isAssignableFrom(target)) return closureToLambda12(closure)
			if(Function13::class.java.isAssignableFrom(target)) return closureToLambda13(closure)
			if(Function14::class.java.isAssignableFrom(target)) return closureToLambda14(closure)
			if(Function15::class.java.isAssignableFrom(target)) return closureToLambda15(closure)
			if(Function16::class.java.isAssignableFrom(target)) return closureToLambda16(closure)
			if(Function17::class.java.isAssignableFrom(target)) return closureToLambda17(closure)
			if(Function18::class.java.isAssignableFrom(target)) return closureToLambda18(closure)
			if(Function19::class.java.isAssignableFrom(target)) return closureToLambda19(closure)
			if(Function20::class.java.isAssignableFrom(target)) return closureToLambda20(closure)
			if(Function21::class.java.isAssignableFrom(target)) return closureToLambda21(closure)
			if(Function22::class.java.isAssignableFrom(target)) return closureToLambda22(closure)
			if(FunctionN::class.java.isAssignableFrom(target)) {
				val arity = when(val metaClass = closure.metaClass) {
					is ClosureMetaClass -> metaClass.methods.filter { it.name == "doCall" }
						.minOf { it.parameterTypes.size }
					is MethodClosure -> {
						val methodName = metaClass.method
						val delegate = metaClass.delegate as Class<*>
						delegate.methods.filter { it.name == methodName }
							.minOf { it.parameterTypes.size }
					}
					else -> throw __invalid_type()
				}
				return closureToLambdaN(closure, arity)
			}
		}
		throw __must_not_happen()
	}

	@JvmStatic val FIELD_MetaClassImpl_metaMethodIndex = MetaClassImpl::class.java.getDeclaredField("metaMethodIndex")
	@JvmStatic val FIELD_MetaClassImpl_classPropertyIndex = MetaClassImpl::class.java.getDeclaredField("classPropertyIndex")
	@JvmStatic val FIELD_MetaClassImpl_allMethods = MetaClassImpl::class.java.getDeclaredField("allMethods")
	@JvmStatic val METHOD_MetaClassImpl_addMetaMethodToIndex = MetaClassImpl::class.java.getDeclaredMethod("addMetaMethodToIndex", MetaMethod::class.java, MetaMethodIndex.Header::class.java)
	@JvmStatic val METHOD_MetaClassImpl_reinitialize = MetaClassImpl::class.java.getDeclaredMethod("reinitialize")
	@JvmStatic val FIELD_MetaMethodIndex_size = MetaMethodIndex::class.java.getDeclaredField("size")
	init {
		FIELD_MetaClassImpl_metaMethodIndex.isAccessible = true
		FIELD_MetaClassImpl_classPropertyIndex.isAccessible = true
		FIELD_MetaClassImpl_allMethods.isAccessible = true
		METHOD_MetaClassImpl_addMetaMethodToIndex.isAccessible = true
		METHOD_MetaClassImpl_reinitialize.isAccessible = true
		FIELD_MetaMethodIndex_size.isAccessible = true
	}

	open class DummyGroovyObject: GroovyObjectSupport() {
		@ExportGradle
		fun __start__() {
			metaClass = MetaClassImpl(DummyGroovyObject::class.java)
		}
		@ExportGradle
		fun __end__() {
			METHOD_MetaClassImpl_reinitialize.invoke(metaClass)
		}
		@ExportGradle
		fun __clear__() {
			metaClass = null
		}
	}
	open class KotlinMetaProperty(
		val modifiers0: Int,
		val name0: String,
		val type0: Class<*>,
		var getter: ((Any?) -> Any?)?,
		var setter: ((Any?, Any?) -> Unit)?
	): MetaProperty(name0, type0) {
		override fun getModifiers(): Int {
			return modifiers0
		}
		override fun getName(): String {
			return name0
		}
		override fun getType(): Class<*> {
			return type0
		}
		override fun getProperty(self: Any?): Any? {
			return (getter ?: throw Error("Cannot read write-only property: $name"))(self)
		}
		override fun setProperty(self: Any?, value: Any?) {
			(setter ?: throw Error("Cannot set read-only property: $name"))(self, value)
		}
	}
	@JvmStatic
	fun metaPropertySame(metaProperty: MetaProperty, name: String): Boolean {
		if(metaProperty.name != name) return false
		return true
	}
	@JvmStatic
	fun metaPropertySame(metaProperty1: MetaProperty, metaProperty2: MetaProperty): Boolean {
		return metaPropertySame(metaProperty1, metaProperty2.name)
	}
	@JvmStatic
	fun setMetaProperty0(metaClass: MetaClassImpl, metaProperty: MetaProperty, declaringClass: CachedClass) {
		val classPropertyIndex = FIELD_MetaClassImpl_classPropertyIndex.get(metaClass) as MetaClassImpl.Index
		val propertyMap = classPropertyIndex.getNotNull(declaringClass)

		val oldMetaProperty = propertyMap.get(metaProperty.name) as? MetaProperty
		if(oldMetaProperty != null && metaPropertySame(metaProperty, oldMetaProperty))
			System.err.println("Redefining meta property '${metaProperty.name}' for '${declaringClass}' at '$metaClass' with '$metaProperty'")
		propertyMap.put(metaProperty.name, metaProperty)
	}
	@JvmStatic
	fun deleteMetaProperty0(metaClass: MetaClassImpl, name: String, declaringClass: CachedClass) {
		val classPropertyIndex = FIELD_MetaClassImpl_classPropertyIndex.get(metaClass) as MetaClassImpl.Index
		val propertyMap = classPropertyIndex.getNullable(declaringClass) ?: return

		propertyMap.remove(name)
		if(propertyMap.size() == 0)
			classPropertyIndex.remove(declaringClass)
	}
	fun getMetaProperty0(metaClass: MetaClassImpl, name: String, declaringClass: CachedClass): MetaProperty? {
		val classPropertyIndex = FIELD_MetaClassImpl_classPropertyIndex.get(metaClass) as MetaClassImpl.Index
		val propertyMap = classPropertyIndex.getNullable(declaringClass) ?: return null
		return propertyMap.get(name) as? MetaProperty
	}
	open class KotlinMetaMethod(
		val modifiers0: Int,
		val name0: String,
		val declaringClass0: CachedClass,
		val parameterTypes0: Array<CachedClass>,
		val returnType0: Class<*>,
		var callback: ((Any?, Array<out Any?>) -> Any?)?
	): MetaMethod() {
		override fun getModifiers(): Int {
			return modifiers0
		}
		override fun getName(): String {
			return name0
		}
		override fun getDeclaringClass(): CachedClass {
			return declaringClass0
		}
		override fun getParameterTypes(): Array<CachedClass> {
			return parameterTypes0
		}
		override fun getReturnType(): Class<*> {
			return returnType0
		}
		override fun invoke(self: Any?, args: Array<out Any?>): Any? {
			return (callback ?: throw Error("Unimplemented method: $name"))(self, args)
		}
	}
	@JvmStatic
	fun metaMethodSame(metaMethod: MetaMethod, name: String, declaringClass: CachedClass, parameterTypes: Array<CachedClass>): Boolean {
		if(metaMethod.name != name) return false
		if(!metaMethod.declaringClass.equals(declaringClass)) return false
		val methodParameterTypes = metaMethod.parameterTypes
		if(methodParameterTypes.size != parameterTypes.size) return false
		val size = methodParameterTypes.size
		for(i in 0 until size)
			if(methodParameterTypes[i] != parameterTypes[i])
				return false
		return true
	}
	@JvmStatic
	fun metaMethodSame(metaMethod1: MetaMethod, metaMethod2: MetaMethod): Boolean {
		return metaMethodSame(metaMethod1, metaMethod2.name, metaMethod2.declaringClass, metaMethod2.parameterTypes)
	}
	@JvmStatic
	fun setMetaMethod0(metaClass: MetaClassImpl, metaMethod: MetaMethod) {
		val metaMethodIndex = FIELD_MetaClassImpl_metaMethodIndex.get(metaClass) as MetaMethodIndex
		val allMethods = FIELD_MetaClassImpl_allMethods.get(metaClass) as MutableList<MetaMethod>
		val header = metaMethodIndex.getHeader(metaMethod.declaringClass.theClass)

		for(method in metaClass.methods) {
			if(!metaMethodSame(method, metaMethod))
				continue
			System.err.println("Redefining meta method '${metaMethod.name}' for '${metaMethod.declaringClass}' at '$metaClass' with '$metaMethod'")
			break
		}
		allMethods += metaMethod
		METHOD_MetaClassImpl_addMetaMethodToIndex.invoke(metaClass, metaMethod, header)
	}
	@JvmStatic
	fun deleteMetaMethod0(metaClass: MetaClassImpl, name: String, declaringClass: CachedClass, parameterTypes: Array<CachedClass>) {
		val metaMethodIndex = FIELD_MetaClassImpl_metaMethodIndex.get(metaClass) as MetaMethodIndex
		val allMethods = FIELD_MetaClassImpl_allMethods.get(metaClass) as MutableList<MetaMethod>
		val header = metaMethodIndex.getHeader(declaringClass.theClass)

		if(true) {
			val table = metaMethodIndex.table
			val hash = MetaMethodIndex.hash(31 * declaringClass.theClass.hashCode() + name.hashCode())
			val index = hash and (table.size - 1)
			val size = metaMethodIndex.size()
			var elem = table[index]
			var prev: MetaMethodIndex.Entry? = null
			while(elem != null) {
				val asImpl = elem.methods as? MetaMethod
				if(elem.hash != hash || asImpl == null || !metaMethodSame(asImpl,
						name, declaringClass, parameterTypes)
				) {
					prev = elem
					elem = elem.nextHashEntry
					continue
				}
				if(prev == null)
					table[index] = elem.nextHashEntry
				else prev.nextHashEntry = elem.nextHashEntry
				elem.nextHashEntry = null
				FIELD_MetaMethodIndex_size.set(metaMethodIndex, size - 1)
				break
			}
		}
		if(true) {
			var head = header.head
			var prev: MetaMethodIndex.Entry? = null
			while(head != null) {
				val asImpl = head.methods as? MetaMethod
				if(asImpl == null || !metaMethodSame(asImpl, name,
						declaringClass, parameterTypes)
				) {
					prev = head
					head = head.nextClassEntry
					continue
				}
				if(prev == null)
					header.head = head.nextClassEntry
				else prev.nextClassEntry = head.nextClassEntry
				head.nextClassEntry = null
				break
			}
		}
		if(true) {
			val iterator = allMethods.iterator()
			while(iterator.hasNext()) {
				val elem = iterator.next()
				if(!metaMethodSame(elem, name, declaringClass, parameterTypes))
					continue
				iterator.remove()
				break
			}
		}
		if(header.head == null)
			metaMethodIndex.methodHeaders.remove(declaringClass)
	}
	@JvmStatic
	fun getMetaMethod0(metaClass: MetaClassImpl, name: String, declaringClass: CachedClass, parameterTypes: Array<CachedClass>): MetaMethod? {
		val metaMethodIndex = FIELD_MetaClassImpl_metaMethodIndex.get(metaClass) as MetaMethodIndex
		if(true) {
			val table = metaMethodIndex.table
			val hash = MetaMethodIndex.hash(31 * declaringClass.theClass.hashCode() + name.hashCode())
			val index = hash and (table.size - 1)
			var elem = table[index]
			while(elem != null) {
				val asImpl = elem.methods as? MetaMethod
				if(elem.hash != hash || asImpl == null || !metaMethodSame(asImpl,
						name, declaringClass, parameterTypes)
				) {
					elem = elem.nextHashEntry
					continue
				}
				return asImpl
			}
		}

		val header = metaMethodIndex.getHeader(declaringClass.theClass)
		if(header != null) {
			var head = header.head
			while(head != null) {
				val asImpl = head.methods as? MetaMethod
				if(asImpl == null || !metaMethodSame(asImpl, name,
						declaringClass, parameterTypes)
				) {
					head = head.nextClassEntry
					continue
				}
				return asImpl
			}
		}

		val allMethods = FIELD_MetaClassImpl_allMethods.get(metaClass) as MutableList<MetaMethod>
		if(true) {
			val iterator = allMethods.iterator()
			while(iterator.hasNext()) {
				val elem = iterator.next()
				if(!metaMethodSame(elem, name, declaringClass, parameterTypes))
					continue
				return elem
			}
		}

		return null
	}
	@JvmStatic
	fun setKotlinToGroovy(that: Any?, project: Project?, names: Array<String>, value: Any?) {
		val metaClass = if(that != null) metaClassFor(that) as? MetaClassImpl else null
		val declaringClass = metaClass?.theCachedClass
		val ext = project?.extensions?.extraProperties

		for(name in names) {
			val (isGetter, isGetterBoolean, isSetter) = parseProperty(name)
			val asProperty = isGetter || isGetterBoolean || isSetter
			val asMethod = !asProperty

			if(metaClass != null && declaringClass != null && asProperty) {
				// getTest -> test, getCONFIG -> CONFIG, get ->, getA -> a
				val propertyName0 = if(isGetterBoolean) name.substring(2) else name.substring(3)
				val propertyName = if(propertyName0.length <= 1) propertyName0.lowercase() else
					if(propertyName0.uppercase() == propertyName0) propertyName0 else propertyName0.replaceFirstChar { it.lowercase() }
				if(value == null) {
					val metaProperty = getMetaProperty0(metaClass, propertyName, declaringClass)
					if(metaProperty is KotlinMetaProperty) {
						if(isGetter || isGetterBoolean)
							metaProperty.getter = null
						if(isSetter)
							metaProperty.setter = null
					}
					if(metaProperty !is KotlinMetaProperty || (metaProperty.getter == null && metaProperty.setter == null))
						deleteMetaProperty0(metaClass, propertyName, declaringClass)
				}
				if(value is KotlinClosure) {
					var metaProperty = getMetaProperty0(metaClass, propertyName, declaringClass) as? KotlinMetaProperty
					if(metaProperty == null) {
						val modifiers = Modifier.PUBLIC or Modifier.STATIC
						metaProperty = KotlinMetaProperty(modifiers, propertyName, Any::class.java, null, null)
						setMetaProperty0(metaClass, metaProperty, declaringClass)
					}
					if(isGetter || isGetterBoolean)
						metaProperty.getter = { self -> value.call(self) }
					if(isSetter)
						metaProperty.setter = { self, newVal -> value.call(self, newVal) }
				}
			}
			if(metaClass != null && declaringClass != null && asMethod) {
				val methodName = name
				val parameterTypes = arrayOf(/* GroovyKotlinInteroperability.KotlinClosure */getCachedClass(Array<Any>::class.java))
				if(value == null) {
					deleteMetaMethod0(metaClass, methodName, declaringClass, parameterTypes)
				}
				if(value is KotlinClosure) {
					var metaMethod = getMetaMethod0(metaClass, methodName, declaringClass, parameterTypes) as? KotlinMetaMethod
					if(metaMethod == null) {
						val modifiers = Modifier.PUBLIC or Modifier.STATIC
						metaMethod = KotlinMetaMethod(modifiers, methodName, declaringClass, parameterTypes, /* GroovyKotlinInteroperability.KotlinClosure */Any::class.java, null)
						setMetaMethod0(metaClass, metaMethod)
					}
					metaMethod.callback = { self, args -> value.call(self, args) }
				}
			}
			ext?.set(name, value)
		}
	}
}
