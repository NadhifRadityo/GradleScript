package GradleScript.TestFixtures

import GradleScript.TestFixtures.FileDSL.DefaultFileNodeGenericDSL
import GradleScript.TestFixtures.FileDSL.DefaultFileNodeGenericDSLExpression
import java.io.File

interface FileDSLContext {
	fun <T> withFile(file: File, expression: DefaultFileNodeGenericDSLExpression<T>): T {
		return DefaultFileNodeGenericDSL(file).expression()
	}
	fun <T> File.dsl(expression: DefaultFileNodeGenericDSLExpression<T>): T {
		return withFile(this, expression)
	}
}
