package GradleScript.GroovyKotlinInteroperability

import GradleScript.Common.groovyKotlinCaches
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.KotlinClosure.KotlinClosureUtils.kotlinReifiedType
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
import kotlin.reflect.*

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
	fun <R> closureToLambda${i}(closure: Closure<R>): fn${i}<R> {
		return { ${new Array(i).fill(null).map((_v, _i) => `a${_i + 1}`).join(", ")} -> closure.call(${new Array(i).fill(null).map((_v, _i) => `a${_i + 1}`).join(", ")}) }
	}`).join("\n"))
	 */
	@ExportGradle @JvmStatic
	fun <R> closureToLambda0(closure: Closure<R>): fn0<R> {
		return {  -> closure.call() }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda1(closure: Closure<R>): fn1<R> {
		return { a1 -> closure.call(a1) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda2(closure: Closure<R>): fn2<R> {
		return { a1, a2 -> closure.call(a1, a2) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda3(closure: Closure<R>): fn3<R> {
		return { a1, a2, a3 -> closure.call(a1, a2, a3) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda4(closure: Closure<R>): fn4<R> {
		return { a1, a2, a3, a4 -> closure.call(a1, a2, a3, a4) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda5(closure: Closure<R>): fn5<R> {
		return { a1, a2, a3, a4, a5 -> closure.call(a1, a2, a3, a4, a5) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda6(closure: Closure<R>): fn6<R> {
		return { a1, a2, a3, a4, a5, a6 -> closure.call(a1, a2, a3, a4, a5, a6) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda7(closure: Closure<R>): fn7<R> {
		return { a1, a2, a3, a4, a5, a6, a7 -> closure.call(a1, a2, a3, a4, a5, a6, a7) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda8(closure: Closure<R>): fn8<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda9(closure: Closure<R>): fn9<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda10(closure: Closure<R>): fn10<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda11(closure: Closure<R>): fn11<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda12(closure: Closure<R>): fn12<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda13(closure: Closure<R>): fn13<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda14(closure: Closure<R>): fn14<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda15(closure: Closure<R>): fn15<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda16(closure: Closure<R>): fn16<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda17(closure: Closure<R>): fn17<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda18(closure: Closure<R>): fn18<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda19(closure: Closure<R>): fn19<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda20(closure: Closure<R>): fn20<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda21(closure: Closure<R>): fn21<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambda22(closure: Closure<R>): fn22<R> {
		return { a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22 -> closure.call(a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21, a22) }
	}
	@ExportGradle @JvmStatic
	fun <R> closureToLambdaN(closure: Closure<R>, arity: Int): fnn<R> {
		return object: fnn<R> {
			override val arity: Int
				get() = arity

			override fun invoke(vararg args: Any?): R {
				return closure.call(*args)
			}
		}
	}

	inline fun <reified T> krt(type: KClass<*>): Class<*> {
		return kotlinReifiedType<T>(type)
	}
	/*
	console.log(new Array(23).fill().map((v, i) => `@ExportGradle @JvmStatic
	inline fun <${new Array(i).fill().map((_v, _i) => `reified A${_i}`).join(", ")}${i == 0 ? "" : ", "}reified R> lambda${i}ToOverload(noinline lambda: (${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
            arrayOf(${new Array(i).fill().map((_v, _i) => `krt<A${_i}>(A${_i}::class)`).join(", ")}), R::class.java,
			{ lambda(${new Array(i).fill().map((_v, _i) => `it[${_i}] as A${_i}`).join(", ")}) }
        ) { override fun toString(): String { return "(${new Array(i).fill().map((_v, _i) => `\${A${_i}::class}`).join(", ")}) -> \${R::class}" } }
	}`).join("\n"))
	 */
	@ExportGradle @JvmStatic
	inline fun <reified R> lambda0ToOverload(noinline lambda: () -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(), R::class.java,
			{ lambda() }
		) { override fun toString(): String { return "() -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified R> lambda1ToOverload(noinline lambda: (A0) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class)), R::class.java,
			{ lambda(it[0] as A0) }
		) { override fun toString(): String { return "(${A0::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified R> lambda2ToOverload(noinline lambda: (A0, A1) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified R> lambda3ToOverload(noinline lambda: (A0, A1, A2) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified R> lambda4ToOverload(noinline lambda: (A0, A1, A2, A3) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified R> lambda5ToOverload(noinline lambda: (A0, A1, A2, A3, A4) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified R> lambda6ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified R> lambda7ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified R> lambda8ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified R> lambda9ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified R> lambda10ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified R> lambda11ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified R> lambda12ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified R> lambda13ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified R> lambda14ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified R> lambda15ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class), krt<A14>(A14::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13, it[14] as A14) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}, ${A14::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified R> lambda16ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class), krt<A14>(A14::class), krt<A15>(A15::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13, it[14] as A14, it[15] as A15) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}, ${A14::class}, ${A15::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified R> lambda17ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class), krt<A14>(A14::class), krt<A15>(A15::class), krt<A16>(A16::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13, it[14] as A14, it[15] as A15, it[16] as A16) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}, ${A14::class}, ${A15::class}, ${A16::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified R> lambda18ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class), krt<A14>(A14::class), krt<A15>(A15::class), krt<A16>(A16::class), krt<A17>(A17::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13, it[14] as A14, it[15] as A15, it[16] as A16, it[17] as A17) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}, ${A14::class}, ${A15::class}, ${A16::class}, ${A17::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified R> lambda19ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class), krt<A14>(A14::class), krt<A15>(A15::class), krt<A16>(A16::class), krt<A17>(A17::class), krt<A18>(A18::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13, it[14] as A14, it[15] as A15, it[16] as A16, it[17] as A17, it[18] as A18) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}, ${A14::class}, ${A15::class}, ${A16::class}, ${A17::class}, ${A18::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified R> lambda20ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class), krt<A14>(A14::class), krt<A15>(A15::class), krt<A16>(A16::class), krt<A17>(A17::class), krt<A18>(A18::class), krt<A19>(A19::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13, it[14] as A14, it[15] as A15, it[16] as A16, it[17] as A17, it[18] as A18, it[19] as A19) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}, ${A14::class}, ${A15::class}, ${A16::class}, ${A17::class}, ${A18::class}, ${A19::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified R> lambda21ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class), krt<A14>(A14::class), krt<A15>(A15::class), krt<A16>(A16::class), krt<A17>(A17::class), krt<A18>(A18::class), krt<A19>(A19::class), krt<A20>(A20::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13, it[14] as A14, it[15] as A15, it[16] as A16, it[17] as A17, it[18] as A18, it[19] as A19, it[20] as A20) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}, ${A14::class}, ${A15::class}, ${A16::class}, ${A17::class}, ${A18::class}, ${A19::class}, ${A20::class}) -> ${R::class}" } }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified A21, reified R> lambda22ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) -> R): KotlinClosure.Overload {
		return object: KotlinClosure.Overload(
			arrayOf(krt<A0>(A0::class), krt<A1>(A1::class), krt<A2>(A2::class), krt<A3>(A3::class), krt<A4>(A4::class), krt<A5>(A5::class), krt<A6>(A6::class), krt<A7>(A7::class), krt<A8>(A8::class), krt<A9>(A9::class), krt<A10>(A10::class), krt<A11>(A11::class), krt<A12>(A12::class), krt<A13>(A13::class), krt<A14>(A14::class), krt<A15>(A15::class), krt<A16>(A16::class), krt<A17>(A17::class), krt<A18>(A18::class), krt<A19>(A19::class), krt<A20>(A20::class), krt<A21>(A21::class)), R::class.java,
			{ lambda(it[0] as A0, it[1] as A1, it[2] as A2, it[3] as A3, it[4] as A4, it[5] as A5, it[6] as A6, it[7] as A7, it[8] as A8, it[9] as A9, it[10] as A10, it[11] as A11, it[12] as A12, it[13] as A13, it[14] as A14, it[15] as A15, it[16] as A16, it[17] as A17, it[18] as A18, it[19] as A19, it[20] as A20, it[21] as A21) }
		) { override fun toString(): String { return "(${A0::class}, ${A1::class}, ${A2::class}, ${A3::class}, ${A4::class}, ${A5::class}, ${A6::class}, ${A7::class}, ${A8::class}, ${A9::class}, ${A10::class}, ${A11::class}, ${A12::class}, ${A13::class}, ${A14::class}, ${A15::class}, ${A16::class}, ${A17::class}, ${A18::class}, ${A19::class}, ${A20::class}, ${A21::class}) -> ${R::class}" } }
	}

	/*
	console.log(new Array(23).fill().map((v, i) => `@ExportGradle @JvmStatic
	inline fun <${new Array(i).fill().map((_v, _i) => `reified A${_i}`).join(", ")}${i == 0 ? "" : ", "}reified R> lambda${i}ToClosure(noinline lambda: (${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda${i}ToOverload(lambda) }
	}`).join("\n"))
	 */
	@ExportGradle @JvmStatic
	inline fun <reified R> lambda0ToClosure(noinline lambda: () -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda0ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified R> lambda1ToClosure(noinline lambda: (A0) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda1ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified R> lambda2ToClosure(noinline lambda: (A0, A1) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda2ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified R> lambda3ToClosure(noinline lambda: (A0, A1, A2) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda3ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified R> lambda4ToClosure(noinline lambda: (A0, A1, A2, A3) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda4ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified R> lambda5ToClosure(noinline lambda: (A0, A1, A2, A3, A4) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda5ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified R> lambda6ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda6ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified R> lambda7ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda7ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified R> lambda8ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda8ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified R> lambda9ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda9ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified R> lambda10ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda10ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified R> lambda11ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda11ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified R> lambda12ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda12ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified R> lambda13ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda13ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified R> lambda14ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda14ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified R> lambda15ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda15ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified R> lambda16ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda16ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified R> lambda17ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda17ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified R> lambda18ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda18ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified R> lambda19ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda19ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified R> lambda20ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda20ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified R> lambda21ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda21ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified A21, reified R> lambda22ToClosure(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) -> R, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += lambda22ToOverload(lambda) }
	}
	@ExportGradle @JvmStatic
	fun lambdaNToClosure(lambda: (Array<out Any?>) -> Any?, name: String = lambda.toString()): Closure<*> {
		return KotlinClosure(name).apply { overloads += KotlinClosure.KLambdaOverload(lambda) }
	}

	@ExportGradle @JvmStatic
	fun <R> closureToLambda(closure: Closure<R>, target: Class<Function<*>>): Function<R> {
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
		open fun __start__() {
			metaClass = MetaClassImpl(DummyGroovyObject::class.java)
		}
		@ExportGradle
		open fun __end__() {
			METHOD_MetaClassImpl_reinitialize.invoke(metaClass)
		}
		@ExportGradle
		open fun __clear__() {
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
		init {
			setParametersTypes(parameterTypes0)
		}
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
	@Deprecated("Implement your own logic")
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
