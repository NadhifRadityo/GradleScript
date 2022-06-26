package GradleScript.TestFixtures.ProjectDSL

import GradleScript.TestFixtures.FileDSL.*
import java.io.File

typealias ProjectFileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT> = FileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT>
typealias ProjectFileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER> = FileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>
interface ProjectFileNodeDirectoryDSL<
	DIRECTORY_RECEIVER: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>: FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER> {

}
open class ProjectFileNodeDirectoryDSLImpl<
	DIRECTORY_RECEIVER: ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	FILE_RECEIVER: ProjectFileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: ProjectFileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>(
	__file_dsl_file_generator: ProjectFileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	__file_dsl_directory_generator: ProjectFileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	__file_dsl_generic_generator: ProjectFileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	__file_dsl_file: File,
):
	ProjectFileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>
		by FileNodeDirectoryDSLImpl(__file_dsl_file_generator, __file_dsl_directory_generator, __file_dsl_generic_generator, __file_dsl_file) {

}
