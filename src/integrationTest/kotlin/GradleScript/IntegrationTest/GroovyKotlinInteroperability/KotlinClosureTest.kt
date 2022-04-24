package GradleScript.IntegrationTest.GroovyKotlinInteroperability

import GradleScript.IntegrationTest.BaseIntegrationTest
import GradleScript.TestFixtures.KotlinClosureDSLContext
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KotlinClosureTest: BaseIntegrationTest(), KotlinClosureDSLContext {

	@Test
	fun `can call primitive overload with arg`() {
		// fun test(Int)
		// test(Int)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<Int, Unit>()
			}
			assertEquals(null, invoke(0))
			expectNotError()
			assertEquals(1, mock.callArguments.size)
			assertArrayEquals(arrayOf(0), mock.callArguments[0])
		}
	}

	@Test
	fun `can call primitive overload with widening conversion arg`() {
		// fun test(Double)
		// test(Int)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<Double, Unit>()
			}
			assertEquals(null, invoke(0))
			expectNotError()
			assertEquals(1, mock.callArguments.size)
			assertArrayEquals(arrayOf(0.0), mock.callArguments[0])
		}
	}

	@Test
	fun `can call primitive vararg overload with arg`() {
		// fun test(vararg Byte) || fun test(ByteArray)
		// test(Byte)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<ByteArray, Unit>()
			}
			assertEquals(null, invoke(1.toByte()))
			expectNotError()
			assertEquals(1, mock.callArguments.size)
			assertArrayEquals(arrayOf(byteArrayOf(1.toByte())), mock.callArguments[0])
		}
	}

	@Test
	fun `can call primitive vararg overload with widening conversion arg`() {
		// fun test(vararg Long) || fun test(LongArray)
		// test(Int)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<LongArray, Unit>()
			}
			assertEquals(null, invoke(1))
			expectNotError()
			assertEquals(1, mock.callArguments.size)
			assertArrayEquals(arrayOf(longArrayOf(1L)), mock.callArguments[0])
		}
	}

	@Test
	fun `cannot call primitive overload with null`() {
		// fun test(Int)
		// test(null)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<Int, Unit>()
			}
			assertEquals(null, invoke(null))
			expectError("Cannot cast null to int (primitive)")
			assertEquals(0, mock.callArguments.size)
		}
	}

	@Test
	fun `cannot call primitive overload with invalid type`() {
		// fun test(Short)
		// test(Any)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<Short, Unit>()
			}
			assertEquals(null, invoke(Object()))
			expectError("Cannot cast class java.lang.Object to short (primitive)")
			assertEquals(0, mock.callArguments.size)
		}
	}

	@Test
	fun `cannot call primitive overload with impossible widening conversion arg`() {
		// fun test(Long)
		// test(Double)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<Long, Unit>()
			}
			assertEquals(null, invoke(0.0))
			expectError("Cannot widening conversion class java.lang.Double to long (primitive)")
			assertEquals(0, mock.callArguments.size)
		}
	}

	@Test
	fun `can call primitive vararg overload with null`() {
		// fun test((vararg Byte)?) || fun test(ByteArray?)
		// test(Byte)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<ByteArray?, Unit>()
			}
			assertEquals(null, invoke(null))
			expectNotError()
			assertEquals(1, mock.callArguments.size)
			assertArrayEquals(arrayOf(null), mock.callArguments[0])
		}
	}

	@Test
	fun `cannot call primitive vararg overload with null at non-first element`() {
		// fun test(vararg Float) || fun test(FloatArray)
		// test(Float, null)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<FloatArray, Unit>()
			}
			assertEquals(null, invoke(0.2f, null))
			expectError("Cannot cast null to float (primitive) [vararg 1]")
			assertEquals(0, mock.callArguments.size)
		}
	}

	@Test
	fun `cannot call primitive vararg overload with invalid type`() {
		// fun test(vararg Byte) || fun test(ByteArray)
		// test(Any)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<ByteArray, Unit>()
			}
			assertEquals(null, invoke(Object()))
			expectError("Cannot cast class java.lang.Object to class [B")
			assertEquals(0, mock.callArguments.size)
		}
	}

	@Test
	fun `cannot call primitive vararg overload with impossible widening conversion arg`() {
		// fun test(vararg Long) || fun test(LongArray)
		// test(Double)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<LongArray, Unit>()
			}
			assertEquals(null, invoke(0.0))
			expectError("Cannot cast class java.lang.Double to class [J")
			assertEquals(0, mock.callArguments.size)
		}
	}

	@Test
	fun `can call vararg overload with empty args`() {
		// fun test(vararg Any?) || fun test(Array<out Any?>)
		// test()
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<Array<out Any?>, Unit>()
			}
			assertEquals(null, invoke())
			expectNotError()
			assertEquals(1, mock.callArguments.size)
			assertArrayEquals(arrayOf(arrayOf<Any?>()), mock.callArguments[0])
		}
	}

	@Test
	fun `can call vararg overload at the end with empty args`() {
		// fun test(Int, vararg Any?) || fun test(Int, Array<out Any?>)
		// test(0)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda2ToOverload<Int, Array<out Any?>, Unit>()
			}
			assertEquals(null, invoke(0))
			expectNotError()
			assertEquals(1, mock.callArguments.size)
			assertArrayEquals(arrayOf(0, arrayOf<Any?>()), mock.callArguments[0])
		}
	}

	@Test
	fun `can call vararg overload with non array arguments`() {
		// fun test(vararg Any?) || fun test(Array<out Any?>)
		// test(String, Int, null)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<Array<out Any?>, Unit>()
			}
			assertEquals(null, invoke("test", 6, null))
			expectNotError()
			assertEquals(1, mock.callArguments.size)
			assertArrayEquals(arrayOf(arrayOf("test", 6, null)), mock.callArguments[0])
		}
	}

	@Test
	fun `cannot call boxed vararg overload with different type`() {
		// fun test(vararg String) || fun test(Array<String>)
		// test(String, Any, String)
		withMockKotlinClosure {
			val mock = withMockImpl {
				withMockLambda1ToOverload<Array<String>, Unit>()
			}
			assertEquals(null, invoke("test", Object(), "test"))
			expectError("Cannot cast class java.lang.Object to class java.lang.String [vararg 1]")
			assertEquals(0, mock.callArguments.size)
		}
	}
}
