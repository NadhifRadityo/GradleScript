package GradleScript.TestFixtures.FileDSL

import java.io.File

typealias DefaultFileNodeDirectoryDSLExpression<RESULT> = FileNodeDirectoryDSLExpression<DefaultFileNodeDirectoryDSL, DefaultFileNodeFileDSL, DefaultFileNodeGenericDSL, RESULT>
typealias DefaultFileNodeDirectoryDSLGenerator = FileNodeDirectoryDSLGenerator<DefaultFileNodeDirectoryDSL, DefaultFileNodeFileDSL, DefaultFileNodeGenericDSL>
fun DefaultFileNodeDirectoryDSLGenerator(): DefaultFileNodeDirectoryDSLGenerator {
	return { dsl, file -> DefaultFileNodeDirectoryDSL(file, dsl.__file_dsl_directory_generator, dsl.__file_dsl_file_generator, dsl.__file_dsl_generic_generator) }
}
open class DefaultFileNodeDirectoryDSL(
	__file_dsl_file: File,
	__file_dsl_directory_generator: DefaultFileNodeDirectoryDSLGenerator = DefaultFileNodeDirectoryDSLGenerator(),
	__file_dsl_file_generator: DefaultFileNodeFileDSLGenerator = DefaultFileNodeFileDSLGenerator(),
	__file_dsl_generic_generator: DefaultFileNodeGenericDSLGenerator = DefaultFileNodeGenericDSLGenerator()
): FileNodeDirectoryDSL<DefaultFileNodeDirectoryDSL, DefaultFileNodeFileDSL, DefaultFileNodeGenericDSL> by
	FileNodeDirectoryDSLImpl(__file_dsl_file_generator, __file_dsl_directory_generator, __file_dsl_generic_generator, __file_dsl_file) { }
