package GradleScript.TestFixtures.KotlinClosureDSL

import GradleScript.GroovyKotlinInteroperability.KotlinClosure

typealias MockKotlinClosureDSLExpression<RECEIVER, RESULT> = KotlinClosureDSLExpression<MockKotlinClosureDSL<RECEIVER>, RESULT>
typealias MockKotlinClosureDSLGenerator<RECEIVER> = KotlinClosureDSLGenerator<MockKotlinClosureDSL<RECEIVER>>
interface MockKotlinClosureDSL<RECEIVER: MockKotlinClosureDSL<RECEIVER>>: KotlinClosureDSL<MockKotlinClosureDSL<RECEIVER>> {
	val exception: Throwable?
	val checked: Boolean

	fun expectError(message: String? = null)
	fun expectNotError()

	fun <RESULT> withMockImpl(expression: MockKotlinClosureDSLImpl<RECEIVER>.() -> RESULT): RESULT
}
//console.log(new Array(23).fill().map((v, i) => `inline fun <${new Array(i).fill().map((_v, _i) => `reified A${_i}`).join(", ")}${i == 0 ? "" : ", "}reified R> MockKotlinClosureDSL<*>.withMockLambda${i}ToOverload(ret: R? = null): CallbackData<(${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}) -> R?> {
//	val callbackData = mockLambda${i}ToOverload<${new Array(i).fill().map((_v, _i) => `A${_i}`).join(", ")}${i == 0 ? "" : ", "}R>(ret)
//	+callbackData.overload
//	return callbackData
//}`).join("\n"))
inline fun <reified R> MockKotlinClosureDSL<*>.withMockLambda0ToOverload(ret: R? = null): CallbackData<() -> R?> {
	val callbackData = mockLambda0ToOverload<R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified R> MockKotlinClosureDSL<*>.withMockLambda1ToOverload(ret: R? = null): CallbackData<(A0) -> R?> {
	val callbackData = mockLambda1ToOverload<A0, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified R> MockKotlinClosureDSL<*>.withMockLambda2ToOverload(ret: R? = null): CallbackData<(A0, A1) -> R?> {
	val callbackData = mockLambda2ToOverload<A0, A1, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified R> MockKotlinClosureDSL<*>.withMockLambda3ToOverload(ret: R? = null): CallbackData<(A0, A1, A2) -> R?> {
	val callbackData = mockLambda3ToOverload<A0, A1, A2, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified R> MockKotlinClosureDSL<*>.withMockLambda4ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3) -> R?> {
	val callbackData = mockLambda4ToOverload<A0, A1, A2, A3, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified R> MockKotlinClosureDSL<*>.withMockLambda5ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4) -> R?> {
	val callbackData = mockLambda5ToOverload<A0, A1, A2, A3, A4, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified R> MockKotlinClosureDSL<*>.withMockLambda6ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5) -> R?> {
	val callbackData = mockLambda6ToOverload<A0, A1, A2, A3, A4, A5, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified R> MockKotlinClosureDSL<*>.withMockLambda7ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6) -> R?> {
	val callbackData = mockLambda7ToOverload<A0, A1, A2, A3, A4, A5, A6, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified R> MockKotlinClosureDSL<*>.withMockLambda8ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7) -> R?> {
	val callbackData = mockLambda8ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified R> MockKotlinClosureDSL<*>.withMockLambda9ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8) -> R?> {
	val callbackData = mockLambda9ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified R> MockKotlinClosureDSL<*>.withMockLambda10ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9) -> R?> {
	val callbackData = mockLambda10ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified R> MockKotlinClosureDSL<*>.withMockLambda11ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10) -> R?> {
	val callbackData = mockLambda11ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified R> MockKotlinClosureDSL<*>.withMockLambda12ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11) -> R?> {
	val callbackData = mockLambda12ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified R> MockKotlinClosureDSL<*>.withMockLambda13ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12) -> R?> {
	val callbackData = mockLambda13ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified R> MockKotlinClosureDSL<*>.withMockLambda14ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13) -> R?> {
	val callbackData = mockLambda14ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified R> MockKotlinClosureDSL<*>.withMockLambda15ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14) -> R?> {
	val callbackData = mockLambda15ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified R> MockKotlinClosureDSL<*>.withMockLambda16ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15) -> R?> {
	val callbackData = mockLambda16ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified R> MockKotlinClosureDSL<*>.withMockLambda17ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16) -> R?> {
	val callbackData = mockLambda17ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified R> MockKotlinClosureDSL<*>.withMockLambda18ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17) -> R?> {
	val callbackData = mockLambda18ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified R> MockKotlinClosureDSL<*>.withMockLambda19ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18) -> R?> {
	val callbackData = mockLambda19ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified R> MockKotlinClosureDSL<*>.withMockLambda20ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19) -> R?> {
	val callbackData = mockLambda20ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified R> MockKotlinClosureDSL<*>.withMockLambda21ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20) -> R?> {
	val callbackData = mockLambda21ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, R>(ret)
	+callbackData.overload
	return callbackData
}
inline fun <reified A0, reified A1, reified A2, reified A3, reified A4, reified A5, reified A6, reified A7, reified A8, reified A9, reified A10, reified A11, reified A12, reified A13, reified A14, reified A15, reified A16, reified A17, reified A18, reified A19, reified A20, reified A21, reified R> MockKotlinClosureDSL<*>.withMockLambda22ToOverload(ret: R? = null): CallbackData<(A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21) -> R?> {
	val callbackData = mockLambda22ToOverload<A0, A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, A13, A14, A15, A16, A17, A18, A19, A20, A21, R>(ret)
	+callbackData.overload
	return callbackData
}
open class MockKotlinClosureDSLImpl<RECEIVER: MockKotlinClosureDSL<RECEIVER>>(
	__kotlin_closure_dsl_generator: MockKotlinClosureDSLGenerator<RECEIVER>,
	__kotlin_closure_dsl_closure: KotlinClosure
): MockKotlinClosureDSL<RECEIVER>, KotlinClosureDSL<MockKotlinClosureDSL<RECEIVER>> by
	KotlinClosureDSLImpl(__kotlin_closure_dsl_generator, __kotlin_closure_dsl_closure) {
	override val __kotlin_closure_dsl_instance: RECEIVER
		get() = this as RECEIVER

	internal var _exception: Throwable? = null
	internal var _checked: Boolean = true
	override val exception: Throwable?
		get() = _exception
	override val checked: Boolean
		get() = _checked

	override fun invoke(vararg arguments: Any?): Any? {
		if(!_checked)
			throw IllegalStateException("Last call is not being checked")
		return try {
			val result = __kotlin_closure_dsl_closure.call(*arguments)
			this._checked = false
			result
		} catch(e: Throwable) {
			_exception = e
			null
		}
	}
	override fun expectError(message: String?) {
		val exception = _exception
		_exception = null
		_checked = true
		if(exception != null) {
			if(message == null) return
			if(exception.message!!.contains(message)) return
			throw AssertionError("KotlinClosure error is not valid\nExpecting: ${message}\nGot: ${exception.message}", exception)
		}
		throw AssertionError("KotlinClosure expecting to error")
	}
	override fun expectNotError() {
		val exception = _exception
		_exception = null
		_checked = true
		if(exception == null) return
		throw AssertionError("KotlinClosure expecting to not error\nError: ${exception.message}", exception)
	}

	override fun <RESULT> withMockImpl(expression: MockKotlinClosureDSLImpl<RECEIVER>.() -> RESULT): RESULT {
		return this.expression()
	}

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
