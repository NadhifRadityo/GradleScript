package GradleScript.TestFixtures

import GradleScript.GroovyKotlinInteroperability.KotlinClosure
import GradleScript.Strategies.StringUtils

interface KotlinClosureDSLContext {
	fun <T> withKotlinClosure(closure: KotlinClosure, expression: DefaultKotlinClosureDSLExpression<T>): T {
		return DefaultKotlinClosureDSL(closure).expression()
	}
	fun <T> withKotlinClosure(name: String = "KotlinClosureDSL#${StringUtils.randomString()}", expression: DefaultKotlinClosureDSLExpression<T>): T {
		return withKotlinClosure(KotlinClosure(name), expression)
	}
	fun <T> KotlinClosure.dsl(expression: DefaultKotlinClosureDSLExpression<T>): T {
		return withKotlinClosure(this, expression)
	}
}
