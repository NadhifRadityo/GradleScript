package GradleScript.TestFixtures.FileDSL

import java.io.File

typealias DefaultFileNodeGenericDSLExpression<RESULT> = FileNodeGenericDSLExpression<DefaultFileNodeGenericDSL, DefaultFileNodeFileDSL, DefaultFileNodeDirectoryDSL, RESULT>
typealias DefaultFileNodeGenericDSLGenerator = FileNodeGenericDSLGenerator<DefaultFileNodeGenericDSL, DefaultFileNodeFileDSL, DefaultFileNodeDirectoryDSL>
fun DefaultFileNodeGenericDSLGenerator(): DefaultFileNodeGenericDSLGenerator {
	return { dsl, file -> DefaultFileNodeGenericDSL(file, dsl.__file_dsl_generic_generator, dsl.__file_dsl_file_generator, dsl.__file_dsl_directory_generator) }
}
open class DefaultFileNodeGenericDSL(
	__file_dsl_file: File,
	__file_dsl_generic_generator: DefaultFileNodeGenericDSLGenerator = DefaultFileNodeGenericDSLGenerator(),
	__file_dsl_file_generator: DefaultFileNodeFileDSLGenerator = DefaultFileNodeFileDSLGenerator(),
	__file_dsl_directory_generator: DefaultFileNodeDirectoryDSLGenerator = DefaultFileNodeDirectoryDSLGenerator()
): FileNodeGenericDSL<DefaultFileNodeGenericDSL, DefaultFileNodeFileDSL, DefaultFileNodeDirectoryDSL> by
	FileNodeGenericDSLImpl(__file_dsl_file_generator, __file_dsl_directory_generator, __file_dsl_generic_generator, __file_dsl_file) { }
