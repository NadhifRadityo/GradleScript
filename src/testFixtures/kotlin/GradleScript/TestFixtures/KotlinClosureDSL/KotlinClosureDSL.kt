package GradleScript.TestFixtures.KotlinClosureDSL

import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda0ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda10ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda11ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda12ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda13ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda14ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda15ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda16ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda17ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda18ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda19ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda1ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda20ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda21ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda22ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda2ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda3ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda4ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda5ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda6ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda7ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda8ToOverload
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambda9ToOverload
import GradleScript.GroovyKotlinInteroperability.KotlinClosure
import GradleScript.GroovyKotlinInteroperability.KotlinClosure.KotlinClosureUtils.getKFunctionOverloads
import GradleScript.GroovyKotlinInteroperability.KotlinClosure.KotlinClosureUtils.getKProperty0Overloads
import GradleScript.GroovyKotlinInteroperability.KotlinClosure.KotlinClosureUtils.getKProperty1Overloads
import GradleScript.GroovyKotlinInteroperability.KotlinClosure.KotlinClosureUtils.getKProperty2Overloads
import GradleScript.GroovyKotlinInteroperability.KotlinClosure.Overload
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1
import kotlin.reflect.KProperty2

@DslMarker
annotation class KotlinClosureDSLMarker

typealias KotlinClosureDSLExpression<RECEIVER, RESULT> = RECEIVER.() -> RESULT
typealias KotlinClosureDSLGenerator<RECEIVER> = (KotlinClosureDSL<RECEIVER>, KotlinClosure) -> RECEIVER
@KotlinClosureDSLMarker
interface KotlinClosureDSL<RECEIVER: KotlinClosureDSL<RECEIVER>> {
	val __kotlin_closure_dsl_generator: KotlinClosureDSLGenerator<RECEIVER>
	val __kotlin_closure_dsl_closure: KotlinClosure
	val __kotlin_closure_dsl_instance: RECEIVER

	fun <RESULT> closures(expression: KotlinClosureDSLExpression<RECEIVER, RESULT>): RESULT
	fun <RESULT> KotlinClosure.closures(expression: KotlinClosureDSLExpression<RECEIVER, RESULT>): RESULT

	fun invoke(vararg arguments: Any?): Any?

	fun withOverload(vararg overload: Overload)
	operator fun Overload.unaryPlus(): Overload
	operator fun Collection<Overload>.unaryPlus(): Collection<Overload>
	operator fun Array<Overload>.unaryPlus(): Array<Overload>

	fun <R> withKFunctionOverloads(owners: Array<Any?>, function: KFunction<R>): Array<Overload>
	fun <V> withKProperty0Overloads(property: KProperty0<V>): Array<Overload>
	fun <T, V> withKProperty1Overloads(owner: T, property: KProperty1<T, V>): Array<Overload>
	fun <D, E, V> withKProperty2Overloads(owner1: D, owner2: E, property: KProperty2<D, E, V>): Array<Overload>

	fun <RESULT> withImpl(expression: KotlinClosureDSLImpl<RECEIVER>.() -> RESULT): RESULT
}
// console.log(new Array(23).fill().map((v, i) => `inline fun <${new Array(i).fill().map((_v, _i) => `reified A${_i}`).join(", ")}${i == 0 ? "" : ", "}reified R> KotlinClosureDSL<*>.withLambda${i}ToOverload(noinline lambda: (${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}) -> R): Overload = +lambda${i}ToOverload(lambda)`).join("\n"))
inline fun <reified R> KotlinClosureDSL<*>.withLambda0ToOverload(noinline lambda: () -> R): Overload = +lambda0ToOverload(lambda)
inline fun <reified A0, reified R> KotlinClosureDSL<*>.withLambda1ToOverload(noinline lambda: (A0) -> R): Overload = +lambda1ToOverload(lambda)
inline fun <reified A0, reified A1, reified R> KotlinClosureDSL<*>.withLambda2ToOverload(noinline lambda: (A0, A1) -> R): Overload = +lambda2ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified R> KotlinClosureDSL<*>.withLambda3ToOverload(noinline lambda: (A0, A1, A2) -> R): Overload = +lambda3ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified R> KotlinClosureDSL<*>.withLambda4ToOverload(noinline lambda: (A0, A1, A2, A3) -> R): Overload = +lambda4ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified R> KotlinClosureDSL<*>.withLambda5ToOverload(noinline lambda: (A0, A1, A2, A3, A4) -> R): Overload = +lambda5ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified R> KotlinClosureDSL<*>.withLambda6ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5) -> R): Overload = +lambda6ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified R> KotlinClosureDSL<*>.withLambda7ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6) -> R): Overload = +lambda7ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified R> KotlinClosureDSL<*>.withLambda8ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7) -> R): Overload = +lambda8ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified R> KotlinClosureDSL<*>.withLambda9ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8) -> R): Overload = +lambda9ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified R> KotlinClosureDSL<*>.withLambda10ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) -> R): Overload = +lambda10ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified R> KotlinClosureDSL<*>.withLambda11ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> R): Overload = +lambda11ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified R> KotlinClosureDSL<*>.withLambda12ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> R): Overload = +lambda12ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified R> KotlinClosureDSL<*>.withLambda13ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> R): Overload = +lambda13ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified R> KotlinClosureDSL<*>.withLambda14ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> R): Overload = +lambda14ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified R> KotlinClosureDSL<*>.withLambda15ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> R): Overload = +lambda15ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified R> KotlinClosureDSL<*>.withLambda16ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) -> R): Overload = +lambda16ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified R> KotlinClosureDSL<*>.withLambda17ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) -> R): Overload = +lambda17ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified R> KotlinClosureDSL<*>.withLambda18ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) -> R): Overload = +lambda18ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified R> KotlinClosureDSL<*>.withLambda19ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) -> R): Overload = +lambda19ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified R> KotlinClosureDSL<*>.withLambda20ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) -> R): Overload = +lambda20ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified R> KotlinClosureDSL<*>.withLambda21ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> R): Overload = +lambda21ToOverload(lambda)
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified A21, reified R> KotlinClosureDSL<*>.withLambda22ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) -> R): Overload = +lambda22ToOverload(lambda)
open class KotlinClosureDSLImpl<RECEIVER: KotlinClosureDSL<RECEIVER>>(
	override val __kotlin_closure_dsl_generator: KotlinClosureDSLGenerator<RECEIVER>,
	override val __kotlin_closure_dsl_closure: KotlinClosure
): KotlinClosureDSL<RECEIVER> {
	override val __kotlin_closure_dsl_instance: RECEIVER
		get() = this as RECEIVER

	override fun <RESULT> closures(expression: KotlinClosureDSLExpression<RECEIVER, RESULT>): RESULT =
		__kotlin_closure_dsl_generator(__kotlin_closure_dsl_instance, __kotlin_closure_dsl_closure).expression()
	override fun <RESULT> KotlinClosure.closures(expression: KotlinClosureDSLExpression<RECEIVER, RESULT>): RESULT =
		__kotlin_closure_dsl_generator(__kotlin_closure_dsl_instance, this).expression()

	override fun invoke(vararg arguments: Any?): Any? {
		return __kotlin_closure_dsl_closure.call(*arguments)
	}

	override fun withOverload(vararg overload: Overload) {
		__kotlin_closure_dsl_closure.overloads += overload
	}
	override operator fun Overload.unaryPlus(): Overload {
		withOverload(this)
		return this
	}
	override operator fun Collection<Overload>.unaryPlus(): Collection<Overload> {
		withOverload(*this.toTypedArray())
		return this
	}
	override operator fun Array<Overload>.unaryPlus(): Array<Overload> {
		withOverload(*this)
		return this
	}

	override fun <R> withKFunctionOverloads(owners: Array<Any?>, function: KFunction<R>): Array<Overload> {
		return +getKFunctionOverloads(owners, function)
	}
	override fun <V> withKProperty0Overloads(property: KProperty0<V>): Array<Overload> {
		return +getKProperty0Overloads(property)
	}
	override fun <T, V> withKProperty1Overloads(owner: T, property: KProperty1<T, V>): Array<Overload> {
		return +getKProperty1Overloads(owner, property)
	}
	override fun <D, E, V> withKProperty2Overloads(owner1: D, owner2: E, property: KProperty2<D, E, V>): Array<Overload> {
		return +getKProperty2Overloads(owner1, owner2, property)
	}

	override fun <RESULT> withImpl(expression: KotlinClosureDSLImpl<RECEIVER>.() -> RESULT): RESULT {
		return this.expression()
	}

	/*
	console.log(new Array(23).fill().map((v, i) => `inline fun <${new Array(i).fill().map((_v, _i) => `reified A${_i}`).join(", ")}${i == 0 ? "" : ", "}reified R> withLambda${i}ToOverload(noinline lambda: (${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}) -> R): Overload {
		return +lambda${i}ToOverload(lambda)
	}`).join("\n"))
	 */
	inline fun <reified R> withLambda0ToOverload(noinline lambda: () -> R): Overload {
		return +lambda0ToOverload(lambda)
	}
	inline fun <reified A0, reified R> withLambda1ToOverload(noinline lambda: (A0) -> R): Overload {
		return +lambda1ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified R> withLambda2ToOverload(noinline lambda: (A0, A1) -> R): Overload {
		return +lambda2ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified R> withLambda3ToOverload(noinline lambda: (A0, A1, A2) -> R): Overload {
		return +lambda3ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified R> withLambda4ToOverload(noinline lambda: (A0, A1, A2, A3) -> R): Overload {
		return +lambda4ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified R> withLambda5ToOverload(noinline lambda: (A0, A1, A2, A3, A4) -> R): Overload {
		return +lambda5ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified R> withLambda6ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5) -> R): Overload {
		return +lambda6ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified R> withLambda7ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6) -> R): Overload {
		return +lambda7ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified R> withLambda8ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7) -> R): Overload {
		return +lambda8ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified R> withLambda9ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8) -> R): Overload {
		return +lambda9ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified R> withLambda10ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) -> R): Overload {
		return +lambda10ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified R> withLambda11ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> R): Overload {
		return +lambda11ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified R> withLambda12ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> R): Overload {
		return +lambda12ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified R> withLambda13ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> R): Overload {
		return +lambda13ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified R> withLambda14ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> R): Overload {
		return +lambda14ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified R> withLambda15ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> R): Overload {
		return +lambda15ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified R> withLambda16ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) -> R): Overload {
		return +lambda16ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified R> withLambda17ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) -> R): Overload {
		return +lambda17ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified R> withLambda18ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) -> R): Overload {
		return +lambda18ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified R> withLambda19ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) -> R): Overload {
		return +lambda19ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified R> withLambda20ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) -> R): Overload {
		return +lambda20ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified R> withLambda21ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> R): Overload {
		return +lambda21ToOverload(lambda)
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified A21, reified R> withLambda22ToOverload(noinline lambda: (A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) -> R): Overload {
		return +lambda22ToOverload(lambda)
	}
}
