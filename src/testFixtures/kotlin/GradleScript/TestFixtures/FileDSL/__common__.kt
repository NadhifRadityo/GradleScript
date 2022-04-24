package GradleScript.TestFixtures.FileDSL

import java.io.File

interface FileDSLData<
	SELF,
	FILE_RECEIVER: FileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
> {
	val __file_dsl_file_generator: FileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>
	val __file_dsl_directory_generator: FileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>
	val __file_dsl_generic_generator: FileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
	val __file_dsl_file: File
	val __file_dsl_instance: SELF
}
