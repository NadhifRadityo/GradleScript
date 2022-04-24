package GradleScript.TestFixtures.FileDSL

import java.io.File

typealias DefaultFileDSLExpression<RESULT> = FileDSLExpression<DefaultFileDSL, RESULT>
typealias DefaultFileDSLGenerator = FileDSLGenerator<DefaultFileDSL>
fun DefaultFileDSLGenerator(): DefaultFileDSLGenerator {
	return { dsl, file -> DefaultFileDSL(file, dsl.__file_dsl_generator) }
}
open class DefaultFileDSL(
	__file_dsl_file: File,
	__file_dsl_generator: DefaultFileDSLGenerator = DefaultFileDSLGenerator()
): FileDSL<DefaultFileDSL> by FileDSLImpl(__file_dsl_generator, __file_dsl_file) { }
