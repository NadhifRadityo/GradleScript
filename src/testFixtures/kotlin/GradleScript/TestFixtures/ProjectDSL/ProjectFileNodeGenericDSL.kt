package GradleScript.TestFixtures.ProjectDSL

import GradleScript.TestFixtures.FileDSL.*
import java.io.File

typealias ProjectFileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT> = FileNodeGenericDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>
typealias ProjectFileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER> = FileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
interface ProjectFileNodeGenericDSL<
	GENERIC_RECEIVER: ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>
>: FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER> {

}
open class ProjectFileNodeGenericDSLImpl<
	GENERIC_RECEIVER: ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>
>(
	__file_dsl_file_generator: ProjectFileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	__file_dsl_directory_generator: ProjectFileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	__file_dsl_generic_generator: ProjectFileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	__file_dsl_file: File,
):
	ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
		by FileNodeGenericDSLImpl(__file_dsl_file_generator, __file_dsl_directory_generator, __file_dsl_generic_generator, __file_dsl_file) {

}
