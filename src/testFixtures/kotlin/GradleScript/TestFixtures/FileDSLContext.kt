package GradleScript.TestFixtures

import GradleScript.TestFixtures.FileDSL.DefaultFileDSL
import GradleScript.TestFixtures.FileDSL.DefaultFileDSLExpression
import java.io.File

interface FileDSLContext {
	fun <T> withFile(file: File, expression: DefaultFileDSLExpression<T>): T {
		return DefaultFileDSL(file).expression()
	}
	fun <T> File.dsl(expression: DefaultFileDSLExpression<T>): T {
		return withFile(this, expression)
	}
}
