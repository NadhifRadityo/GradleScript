package GradleScript.TestFixtures

import GradleScript.GroovyKotlinInteroperability.KotlinClosure
import GradleScript.Strategies.StringUtils
import GradleScript.TestFixtures.KotlinClosureDSL.DefaultKotlinClosureDSL
import GradleScript.TestFixtures.KotlinClosureDSL.DefaultKotlinClosureDSLExpression
import GradleScript.TestFixtures.KotlinClosureDSL.DefaultMockKotlinClosureDSL
import GradleScript.TestFixtures.KotlinClosureDSL.DefaultMockKotlinClosureDSLExpression

interface KotlinClosureDSLContext: BaseTest {
	fun <T> withKotlinClosure(closure: KotlinClosure, expression: DefaultKotlinClosureDSLExpression<T>): T {
		return DefaultKotlinClosureDSL(closure).expression()
	}
	fun <T> withKotlinClosure(name: String = "KotlinClosureDSL#${StringUtils.randomString()}", expression: DefaultKotlinClosureDSLExpression<T>): T {
		return withKotlinClosure(KotlinClosure(name), expression)
	}
	fun <T> KotlinClosure.dsl(expression: DefaultKotlinClosureDSLExpression<T>): T {
		return withKotlinClosure(this, expression)
	}

	fun <T> withMockKotlinClosure(closure: KotlinClosure, expression: DefaultMockKotlinClosureDSLExpression<T>): T {
		return DefaultMockKotlinClosureDSL(closure).expression()
	}
	fun <T> withMockKotlinClosure(name: String = "KotlinClosureDSL#${StringUtils.randomString()}", expression: DefaultMockKotlinClosureDSLExpression<T>): T {
		return withMockKotlinClosure(KotlinClosure(name), expression)
	}
}
