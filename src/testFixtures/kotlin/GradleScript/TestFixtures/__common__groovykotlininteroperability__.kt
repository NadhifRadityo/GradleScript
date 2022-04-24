package GradleScript.TestFixtures

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

typealias KotlinClosureDSLExpression<RECEIVER, RESULT> = RECEIVER.() -> RESULT
typealias KotlinClosureDSLGenerator<RECEIVER> = (KotlinClosureDSL<RECEIVER>, KotlinClosure) -> RECEIVER
open class KotlinClosureDSL<RECEIVER: KotlinClosureDSL<RECEIVER>>(val generator: KotlinClosureDSLGenerator<RECEIVER>, val closure: KotlinClosure) {
	internal val _instance: RECEIVER
		get() = this as RECEIVER
	var exception: Throwable? = null
		internal set

	open fun <RESULT> dsl(expression: KotlinClosureDSLExpression<RECEIVER, RESULT>): RESULT =
		generator(_instance, closure).expression()
	open fun <RESULT> KotlinClosure.dsl(expression: KotlinClosureDSLExpression<RECEIVER, RESULT>): RESULT =
		generator(_instance, this).expression()

	open fun invoke(vararg arguments: Any?): Any? {
		try {
			return closure.call(*arguments)
		} catch(e: Throwable) {
			exception = e
			return null
		}
	}
	open fun expectError(message: String? = null) {
		val exception = exception
		this.exception = null
		if(exception != null) {
			if(message == null) return
			if(exception.message!!.contains(message)) return
			throw AssertionError("KotlinClosure error is not valid\nExpecting: ${message}\nGot: ${exception.message}", exception)
		}
		throw AssertionError("KotlinClosure expecting to error")
	}
	open fun expectNotError() {
		val exception = exception
		this.exception = null
		if(exception == null) return
		throw AssertionError("KotlinClosure expecting to not error\nError: ${exception.message}", exception)
	}

	open fun withOverload(vararg overload: Overload) {
		closure.overloads += overload
	}
	open operator fun Overload.unaryPlus(): Overload {
		withOverload(this)
		return this
	}
	open operator fun Collection<Overload>.unaryPlus(): Collection<Overload> {
		withOverload(*this.toTypedArray())
		return this
	}
	open operator fun Array<Overload>.unaryPlus(): Array<Overload> {
		withOverload(*this)
		return this
	}

	open fun <R> withKFunctionOverloads(owners: Array<Any?>, function: KFunction<R>): Array<Overload> {
		return +getKFunctionOverloads(owners, function)
	}
	open fun <V> withKProperty0Overloads(property: KProperty0<V>): Array<Overload> {
		return +getKProperty0Overloads(property)
	}
	open fun <T, V> withKProperty1Overloads(owner: T, property: KProperty1<T, V>): Array<Overload> {
		return +getKProperty1Overloads(owner, property)
	}
	open fun <D, E, V> withKProperty2Overloads(owner1: D, owner2: E, property: KProperty2<D, E, V>): Array<Overload> {
		return +getKProperty2Overloads(owner1, owner2, property)
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

typealias DefaultKotlinClosureDSLExpression<RESULT> = KotlinClosureDSLExpression<DefaultKotlinClosureDSL, RESULT>
typealias DefaultKotlinClosureDSLGenerator = KotlinClosureDSLGenerator<DefaultKotlinClosureDSL>
fun DefaultKotlinClosureDSLGenerator(): DefaultKotlinClosureDSLGenerator { lateinit var generator: DefaultKotlinClosureDSLGenerator; generator = { _, closure -> DefaultKotlinClosureDSL(closure, generator) }; return generator }
open class DefaultKotlinClosureDSL(closure: KotlinClosure, generator: DefaultKotlinClosureDSLGenerator = DefaultKotlinClosureDSLGenerator()): KotlinClosureDSL<DefaultKotlinClosureDSL>(generator, closure) { }


open class CallbackData<L>(
	val lambda: L,
	val overload: Overload,
	val callArguments: MutableList<Array<out Any?>>) {
}
/*
console.log(new Array(23).fill().map((v, i) => `inline fun <${new Array(i).fill().map((_v, _i) => `reified A${_i}`).join(", ")}${i == 0 ? "" : ", "}reified R> mockLambda${i}ToOverload(ret: R? = null): CallbackData<(${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { ${new Array(i).fill().map((_v, _i) => `a${_i}: A${_i}`).join(", ")} -> callArguments.add(arrayOf(${new Array(i).fill().map((_v, _i) => `a${_i}`).join(", ")})); ret }
	val overload = lambda${i}ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}`).join("\n"))
 */
inline fun <reified R> mockLambda0ToOverload(ret: R? = null): CallbackData<() -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = {  -> callArguments.add(arrayOf()); ret }
	val overload = lambda0ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified R> mockLambda1ToOverload(ret: R? = null): CallbackData<(A0) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0 -> callArguments.add(arrayOf(a0)); ret }
	val overload = lambda1ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified R> mockLambda2ToOverload(ret: R? = null): CallbackData<(A0, A1) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1 -> callArguments.add(arrayOf(a0, a1)); ret }
	val overload = lambda2ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified R> mockLambda3ToOverload(ret: R? = null): CallbackData<(A0, A1, A2) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2 -> callArguments.add(arrayOf(a0, a1, a2)); ret }
	val overload = lambda3ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified R> mockLambda4ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3 -> callArguments.add(arrayOf(a0, a1, a2, a3)); ret }
	val overload = lambda4ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified R> mockLambda5ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4)); ret }
	val overload = lambda5ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified R> mockLambda6ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5)); ret }
	val overload = lambda6ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified R> mockLambda7ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6)); ret }
	val overload = lambda7ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified R> mockLambda8ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7)); ret }
	val overload = lambda8ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified R> mockLambda9ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8)); ret }
	val overload = lambda9ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified R> mockLambda10ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9)); ret }
	val overload = lambda10ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified R> mockLambda11ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10)); ret }
	val overload = lambda11ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified R> mockLambda12ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11)); ret }
	val overload = lambda12ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified R> mockLambda13ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12)); ret }
	val overload = lambda13ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified R> mockLambda14ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13)); ret }
	val overload = lambda14ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified R> mockLambda15ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14)); ret }
	val overload = lambda15ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified R> mockLambda16ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15)); ret }
	val overload = lambda16ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified R> mockLambda17ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)); ret }
	val overload = lambda17ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified R> mockLambda18ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17)); ret }
	val overload = lambda18ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified R> mockLambda19ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18)); ret }
	val overload = lambda19ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified R> mockLambda20ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19)); ret }
	val overload = lambda20ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified R> mockLambda21ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20)); ret }
	val overload = lambda21ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified A21, reified R> mockLambda22ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) -> R?> {
	val callArguments = mutableListOf<Array<out Any?>>()
	val lambda = { a0: A0, a1: A1, a2: A2, a3: A3, a4: A4, a5: A5, a6: A6, a7: A7, a8: A8, a9: A9, a10: A10, a11: A11, a12: A12, a13: A13, a14: A14, a15: A15, a16: A16, a17: A17, a18: A18, a19: A19, a20: A20, a21: A21 -> callArguments.add(arrayOf(a0, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17, a18, a19, a20, a21)); ret }
	val overload = lambda22ToOverload(lambda)
	return CallbackData(lambda, overload, callArguments)
}

typealias TestKotlinClosureDSLExpression<RESULT> = KotlinClosureDSLExpression<TestKotlinClosureDSL, RESULT>
typealias TestKotlinClosureDSLGenerator = KotlinClosureDSLGenerator<TestKotlinClosureDSL>
fun TestKotlinClosureDSLGenerator(): TestKotlinClosureDSLGenerator { lateinit var generator: TestKotlinClosureDSLGenerator; generator = { _, closure -> TestKotlinClosureDSL(closure, generator) }; return generator }
open class TestKotlinClosureDSL(closure: KotlinClosure, generator: TestKotlinClosureDSLGenerator = TestKotlinClosureDSLGenerator()): KotlinClosureDSL<TestKotlinClosureDSL>(generator, closure) {
	/*
	console.log(new Array(23).fill().map((v, i) => `inline fun <${new Array(i).fill().map((_v, _i) => `reified A${_i}`).join(", ")}${i == 0 ? "" : ", "}reified R> withMockLambda${i}ToOverload(ret: R? = null): CallbackData<(${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}) -> R?> {
		val callbackData = mockLambda${i}ToOverload<${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}${i == 0 ? "" : ", "}R>(ret)
        +callbackData.overload
        return callbackData
	}`).join("\n"))
	 */
	inline fun <reified R> withMockLambda0ToOverload(ret: R? = null): CallbackData<() -> R?> {
		val callbackData = mockLambda0ToOverload<R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified R> withMockLambda1ToOverload(ret: R? = null): CallbackData<(A0) -> R?> {
		val callbackData = mockLambda1ToOverload<A0, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified R> withMockLambda2ToOverload(ret: R? = null): CallbackData<(A0, A1) -> R?> {
		val callbackData = mockLambda2ToOverload<A0, A1, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified R> withMockLambda3ToOverload(ret: R? = null): CallbackData<(A0, A1, A2) -> R?> {
		val callbackData = mockLambda3ToOverload<A0, A1, A2, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified R> withMockLambda4ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3) -> R?> {
		val callbackData = mockLambda4ToOverload<A0, A1, A2, A3, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified R> withMockLambda5ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4) -> R?> {
		val callbackData = mockLambda5ToOverload<A0, A1, A2, A3, A4, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified R> withMockLambda6ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5) -> R?> {
		val callbackData = mockLambda6ToOverload<A0, A1, A2, A3, A4, A5, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified R> withMockLambda7ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6) -> R?> {
		val callbackData = mockLambda7ToOverload<A0, A1, A2, A3, A4, A5, A6, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified R> withMockLambda8ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7) -> R?> {
		val callbackData = mockLambda8ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified R> withMockLambda9ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8) -> R?> {
		val callbackData = mockLambda9ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified R> withMockLambda10ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) -> R?> {
		val callbackData = mockLambda10ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified R> withMockLambda11ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> R?> {
		val callbackData = mockLambda11ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified R> withMockLambda12ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> R?> {
		val callbackData = mockLambda12ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified R> withMockLambda13ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> R?> {
		val callbackData = mockLambda13ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified R> withMockLambda14ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> R?> {
		val callbackData = mockLambda14ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified R> withMockLambda15ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> R?> {
		val callbackData = mockLambda15ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified R> withMockLambda16ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) -> R?> {
		val callbackData = mockLambda16ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified R> withMockLambda17ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) -> R?> {
		val callbackData = mockLambda17ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified R> withMockLambda18ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) -> R?> {
		val callbackData = mockLambda18ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified R> withMockLambda19ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) -> R?> {
		val callbackData = mockLambda19ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified R> withMockLambda20ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) -> R?> {
		val callbackData = mockLambda20ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified R> withMockLambda21ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> R?> {
		val callbackData = mockLambda21ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, R>(ret)
		+callbackData.overload
		return callbackData
	}
	inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified A21, reified R> withMockLambda22ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) -> R?> {
		val callbackData = mockLambda22ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, R>(ret)
		+callbackData.overload
		return callbackData
	}
}
