package GradleScript.TestFixtures.FileDSL

import GradleScript.Strategies.FileUtils
import java.io.File
import java.nio.file.StandardCopyOption

typealias FileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT> = DIRECTORY_RECEIVER.() -> RESULT
typealias FileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER> = (FileDSLData<*, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>, File) -> DIRECTORY_RECEIVER
@FileDSLMarker
interface FileNodeDirectoryDSL<
	DIRECTORY_RECEIVER: FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	FILE_RECEIVER: FileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>: FileDSLData<DIRECTORY_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER> {
	fun <RESULT> asNodeGeneric(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT
	@Deprecated("Cast to FileNodeGenericDSL(asNodeGeneric) first")
	fun <RESULT> asNodeFile(expression: FileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
	@Deprecated("Cast to FileNodeGenericDSL(asNodeGeneric) first")
	fun <RESULT> asNodeDirectory(expression: FileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
	fun <RESULT> File.files(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, DIRECTORY_RECEIVER, FILE_RECEIVER, RESULT>): RESULT

	operator fun String.not(): File
	operator fun <RESULT> String.invoke(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT
	operator fun <RESULT> String.times(expression: FileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
	operator fun <RESULT> String.div(expression: FileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT

	fun file(vararg name: String): File
	fun fileRelative(fileRel: File): String
	fun fileCopy(from: File, vararg name: String): File
	fun fileCopyDir(from: File, dir: String, vararg what: String): Array<File>
	fun mkfile(vararg name: String): File
	fun mkdir(vararg name: String): File
	fun mkdir(): File
	fun delfile(vararg name: String): File
	fun delfile(): File
	fun readBytes(vararg name: String): ByteArray
	fun readString(vararg name: String): String

	// "test.txt" -= "replace content"
	operator fun String.minusAssign(content: String)
	// "test.txt" += "append content"
	operator fun String.plusAssign(content: String)
	// "test.txt" % "replace content".encodeToByteArray()
	operator fun String.rem(content: ByteArray)
	// "test.txt" .. "append content".encodeToByteArray()
	operator fun String.rangeTo(content: ByteArray)
}
open class FileNodeDirectoryDSLImpl<
	DIRECTORY_RECEIVER: FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	FILE_RECEIVER: FileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>(
	override val __file_dsl_file_generator: FileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	override val __file_dsl_directory_generator: FileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	override val __file_dsl_generic_generator: FileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	override val __file_dsl_file: File,
): FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER> {
	override val __file_dsl_instance: DIRECTORY_RECEIVER
		get() = this as DIRECTORY_RECEIVER

	override fun <RESULT> asNodeGeneric(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT = __file_dsl_generic_generator(__file_dsl_instance, __file_dsl_file).expression()
	override fun <RESULT> asNodeFile(expression: FileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT = __file_dsl_file_generator(__file_dsl_instance, __file_dsl_file).expression()
	override fun <RESULT> asNodeDirectory(expression: FileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT = __file_dsl_directory_generator(__file_dsl_instance, __file_dsl_file).expression()
	override fun <RESULT> File.files(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT = __file_dsl_generic_generator(__file_dsl_instance, this).expression()

	override operator fun String.not(): File {
		if(this.startsWith("/") || this.startsWith("\\"))
			return mkdir(this)
		return mkfile(this)
	}
	override operator fun <RESULT> String.invoke(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT {
		return __file_dsl_generic_generator(__file_dsl_instance, !this).expression()
	}
	override operator fun <RESULT> String.times(expression: FileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT {
		if(this.startsWith("/") || this.startsWith("\\"))
			throw IllegalStateException("File name should not begin with '/' or '\\'")
		return __file_dsl_file_generator(__file_dsl_instance, !this).expression()
	}
	override operator fun <RESULT> String.div(expression: FileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT {
		return __file_dsl_directory_generator(__file_dsl_instance, !"/$this").expression()
	}

	override fun file(vararg name: String): File {
		return FileUtils.file(__file_dsl_file, *name)
	}
	override fun fileRelative(fileRel: File): String {
		return FileUtils.fileRelative(__file_dsl_file, fileRel)
	}
	override fun fileCopy(from: File, vararg name: String): File {
		return FileUtils.fileCopy(from, file(*name), StandardCopyOption.REPLACE_EXISTING)
	}
	override fun fileCopyDir(from: File, dir: String, vararg what: String): Array<File> {
		return FileUtils.fileCopyDir(from, file(dir), what, StandardCopyOption.REPLACE_EXISTING)
	}
	override fun mkfile(vararg name: String): File {
		return FileUtils.mkfile(file(*name))
	}
	override fun mkdir(vararg name: String): File {
		return FileUtils.mkdir(file(*name))
	}
	override fun mkdir(): File {
		return FileUtils.mkdir(__file_dsl_file)
	}
	override fun delfile(vararg name: String): File {
		return FileUtils.delfile(file(*name))
	}
	override fun delfile(): File {
		return FileUtils.delfile(__file_dsl_file)
	}
	override fun readBytes(vararg name: String): ByteArray {
		return FileUtils.fileBytes(file(*name))
	}
	override fun readString(vararg name: String): String {
		return FileUtils.fileString(file(*name))
	}

	override operator fun String.minusAssign(content: String) {
		this * { -content }
	}
	override operator fun String.plusAssign(content: String) {
		this * { +content }
	}
	override operator fun String.rem(content: ByteArray) {
		this * { -content }
	}
	override operator fun String.rangeTo(content: ByteArray) {
		this * { +content }
	}
}
