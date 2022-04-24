package GradleScript.TestFixtures.KotlinClosureDSL

import GradleScript.GroovyKotlinInteroperability.KotlinClosure

typealias DefaultKotlinClosureDSLExpression<RESULT> = KotlinClosureDSLExpression<DefaultKotlinClosureDSL, RESULT>
typealias DefaultKotlinClosureDSLGenerator = KotlinClosureDSLGenerator<DefaultKotlinClosureDSL>
fun DefaultKotlinClosureDSLGenerator(): DefaultKotlinClosureDSLGenerator {
	return { dsl, closure -> DefaultKotlinClosureDSL(closure, dsl.__kotlin_closure_dsl_generator) }
}
open class DefaultKotlinClosureDSL(
	__kotlin_closure_dsl_closure: KotlinClosure,
	__kotlin_closure_dsl_generator: DefaultKotlinClosureDSLGenerator = DefaultKotlinClosureDSLGenerator()
): KotlinClosureDSL<DefaultKotlinClosureDSL> by KotlinClosureDSLImpl(__kotlin_closure_dsl_generator, __kotlin_closure_dsl_closure) { }
