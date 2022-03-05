package GradleScript.IntegrationTest

import GradleScript.Common.lastContext0
import GradleScript.Context
import GradleScript.GroovyKotlinInteroperability.GroovyManipulation.lambdaNToClosure
import GradleScript.Strategies.ClosureUtils.bind
import GradleScript.Strategies.ClosureUtils.lash
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ClosureUtilsTest: BaseIntegrationTest() {

	companion object {
		val returnValue = Any()
	}
	class CallbackData {
		val callArguments = mutableListOf<Pair<Context?, Array<out Any?>>>()
		val callback = { args: Array<out Any?> -> callArguments += Pair(lastContext0(), args); returnValue }
		val closure = lambdaNToClosure(callback)
	}

	@Test
	fun `can lash callback`() {
		val callbackData = CallbackData()
		val that = Any()
		val prependArguments = arrayOf(Any(), "test2", 3)
		val lashed = lash(that, callbackData.closure, *prependArguments)
		val realArguments = arrayOf("realArguments", 429)
		lashed.call(*realArguments)
		lashed.call(*realArguments.reversedArray())
		assertEquals(2, callbackData.callArguments.size)
		// First call
		assertEquals(that, callbackData.callArguments[0].first!!.that)
		assertEquals(prependArguments[0], callbackData.callArguments[0].second[0])
		assertEquals(prependArguments[1], callbackData.callArguments[0].second[1])
		assertEquals(prependArguments[2], callbackData.callArguments[0].second[2])
		assertEquals(realArguments[0], callbackData.callArguments[0].second[3])
		assertEquals(realArguments[1], callbackData.callArguments[0].second[4])
		// Second call
		assertEquals(that, callbackData.callArguments[1].first!!.that)
		assertEquals(prependArguments[0], callbackData.callArguments[1].second[0])
		assertEquals(prependArguments[1], callbackData.callArguments[1].second[1])
		assertEquals(prependArguments[2], callbackData.callArguments[1].second[2])
		assertEquals(realArguments[0], callbackData.callArguments[1].second[4])
		assertEquals(realArguments[1], callbackData.callArguments[1].second[3])
	}

	@Test
	fun `can bind callback`() {
		val callbackData = CallbackData()
		val self = Any()
		val prependArguments = arrayOf(Any(), "test2", 3)
		val bound = bind(self, callbackData.closure, *prependArguments)
		val realArguments = arrayOf("realArguments", 429)
		bound.call(*realArguments)
		bound.call(*realArguments.reversedArray())
		assertEquals(2, callbackData.callArguments.size)
		// First call
		assertNull(callbackData.callArguments[0].first)
		assertEquals(self, callbackData.callArguments[0].second[0])
		assertEquals(prependArguments[0], callbackData.callArguments[0].second[1])
		assertEquals(prependArguments[1], callbackData.callArguments[0].second[2])
		assertEquals(prependArguments[2], callbackData.callArguments[0].second[3])
		assertEquals(realArguments[0], callbackData.callArguments[0].second[4])
		assertEquals(realArguments[1], callbackData.callArguments[0].second[5])
		// Second call
		assertNull(callbackData.callArguments[1].first)
		assertEquals(self, callbackData.callArguments[0].second[0])
		assertEquals(prependArguments[0], callbackData.callArguments[1].second[1])
		assertEquals(prependArguments[1], callbackData.callArguments[1].second[2])
		assertEquals(prependArguments[2], callbackData.callArguments[1].second[3])
		assertEquals(realArguments[0], callbackData.callArguments[1].second[5])
		assertEquals(realArguments[1], callbackData.callArguments[1].second[4])
	}
}
