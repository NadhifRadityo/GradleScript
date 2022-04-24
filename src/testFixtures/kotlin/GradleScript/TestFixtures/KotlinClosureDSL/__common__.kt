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

open class CallbackData<L>(
	val lambda: L,
	val overload: KotlinClosure.Overload,
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
