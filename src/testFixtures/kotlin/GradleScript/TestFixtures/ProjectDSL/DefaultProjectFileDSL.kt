package GradleScript.TestFixtures.ProjectDSL

import java.io.File

typealias DefaultProjectFileDSLExpression<RESULT> = ProjectFileDSLExpression<DefaultProjectFileDSL, RESULT>
typealias DefaultProjectFileDSLGenerator = ProjectFileDSLGenerator<DefaultProjectFileDSL>
fun DefaultProjectFileDSLGenerator(): DefaultProjectFileDSLGenerator {
	return { dsl, file -> DefaultProjectFileDSL(file, dsl.__file_dsl_generator) }
}
open class DefaultProjectFileDSL(
	__file_dsl_file: File,
	__file_dsl_generator: DefaultProjectFileDSLGenerator = DefaultProjectFileDSLGenerator()
): ProjectFileDSL<DefaultProjectFileDSL> by ProjectFileDSLImpl(__file_dsl_generator, __file_dsl_file) { }
