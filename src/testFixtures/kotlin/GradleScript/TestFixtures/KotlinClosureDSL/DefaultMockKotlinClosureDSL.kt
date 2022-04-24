package GradleScript.TestFixtures.KotlinClosureDSL

import GradleScript.GroovyKotlinInteroperability.KotlinClosure

typealias DefaultMockKotlinClosureDSLExpression<RESULT> = MockKotlinClosureDSLExpression<DefaultMockKotlinClosureDSL, RESULT>
typealias DefaultMockKotlinClosureDSLGenerator = MockKotlinClosureDSLGenerator<DefaultMockKotlinClosureDSL>
fun DefaultMockKotlinClosureDSLGenerator(): DefaultMockKotlinClosureDSLGenerator {
	return { dsl, closure -> DefaultMockKotlinClosureDSL(closure, dsl.__kotlin_closure_dsl_generator) }
}
open class DefaultMockKotlinClosureDSL(
	__kotlin_closure_dsl_closure: KotlinClosure,
	__kotlin_closure_dsl_generator: DefaultMockKotlinClosureDSLGenerator = DefaultMockKotlinClosureDSLGenerator()
): MockKotlinClosureDSL<DefaultMockKotlinClosureDSL> by MockKotlinClosureDSLImpl(__kotlin_closure_dsl_generator, __kotlin_closure_dsl_closure) { }
