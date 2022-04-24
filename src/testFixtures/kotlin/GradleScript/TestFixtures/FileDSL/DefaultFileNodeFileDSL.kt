package GradleScript.TestFixtures.FileDSL

import java.io.File

typealias DefaultFileNodeFileDSLExpression<RESULT> = FileNodeFileDSLExpression<DefaultFileNodeDirectoryDSL, DefaultFileNodeFileDSL, DefaultFileNodeGenericDSL, RESULT>
typealias DefaultFileNodeFileDSLGenerator = FileNodeFileDSLGenerator<DefaultFileNodeFileDSL, DefaultFileNodeDirectoryDSL, DefaultFileNodeGenericDSL>
fun DefaultFileNodeFileDSLGenerator(): DefaultFileNodeFileDSLGenerator {
	return { dsl, file -> DefaultFileNodeFileDSL(file, dsl.__file_dsl_file_generator, dsl.__file_dsl_directory_generator, dsl.__file_dsl_generic_generator) }
}
open class DefaultFileNodeFileDSL(
	__file_dsl_file: File,
	__file_dsl_file_generator: DefaultFileNodeFileDSLGenerator = DefaultFileNodeFileDSLGenerator(),
	__file_dsl_directory_generator: DefaultFileNodeDirectoryDSLGenerator = DefaultFileNodeDirectoryDSLGenerator(),
	__file_dsl_generic_generator: DefaultFileNodeGenericDSLGenerator = DefaultFileNodeGenericDSLGenerator()
): FileNodeFileDSL<DefaultFileNodeFileDSL, DefaultFileNodeDirectoryDSL, DefaultFileNodeGenericDSL> by
	FileNodeFileDSLImpl(__file_dsl_file_generator, __file_dsl_directory_generator, __file_dsl_generic_generator, __file_dsl_file) { }
