package GradleScript.TestFixtures.FileDSL

import GradleScript.Strategies.FileUtils
import java.io.File

typealias FileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT> = FILE_RECEIVER.() -> RESULT
typealias FileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER> = (FileDSLData<*, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>, File) -> FILE_RECEIVER
@FileDSLMarker
interface FileNodeFileDSL<
	FILE_RECEIVER: FileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	GENERIC_RECEIVER: FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>: FileDSLData<FILE_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER> {
	fun <RESULT> asNodeGeneric(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT
	@Deprecated("Cast to FileNodeGenericDSL(asNodeGeneric) first")
	fun <RESULT> asNodeFile(expression: FileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
	@Deprecated("Cast to FileNodeGenericDSL(asNodeGeneric) first")
	fun <RESULT> asNodeDirectory(expression: FileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
	fun <RESULT> File.files(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, DIRECTORY_RECEIVER, FILE_RECEIVER, RESULT>): RESULT

	operator fun String.not(): File
	operator fun <RESULT> String.invoke(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT

	fun file(vararg name: String): File
	fun fileRelative(fileRel: File): String
	fun mkfile(): File
	fun delfile(): File
	fun readBytes(): ByteArray
	fun readString(): String
	fun writeBytes(content: ByteArray)
	fun writeString(content: String)

	// "test.txt" * { -"replace content" }
	operator fun String.unaryMinus()
	// "test.txt" * { +"append content" }
	operator fun String.unaryPlus()
	// "test.txt" * { -"replace content".encodeToByteArray() }
	operator fun ByteArray.unaryMinus()
	// "test.txt" * { +"append content".encodeToByteArray() }
	operator fun ByteArray.unaryPlus()
}
open class FileNodeFileDSLImpl<
	FILE_RECEIVER: FileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>, 
	DIRECTORY_RECEIVER: FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>, 
	GENERIC_RECEIVER: FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>
>(
	override val __file_dsl_file_generator: FileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	override val __file_dsl_directory_generator: FileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	override val __file_dsl_generic_generator: FileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	override val __file_dsl_file: File,
): FileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER> {
	override val __file_dsl_instance: FILE_RECEIVER
		get() = this as FILE_RECEIVER

	override fun <RESULT> asNodeGeneric(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT = __file_dsl_generic_generator(__file_dsl_instance, __file_dsl_file).expression()
	override fun <RESULT> asNodeFile(expression: FileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT = __file_dsl_file_generator(__file_dsl_instance, __file_dsl_file).expression()
	override fun <RESULT> asNodeDirectory(expression: FileNodeDirectoryDSLExpression<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT = __file_dsl_directory_generator(__file_dsl_instance, __file_dsl_file).expression()
	override fun <RESULT> File.files(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT = __file_dsl_generic_generator(__file_dsl_instance, this).expression()

	override operator fun String.not(): File {
		throw IllegalStateException("Cannot create node under file.")
	}
	override operator fun <RESULT> String.invoke(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT {
		return __file_dsl_generic_generator(__file_dsl_instance, !this).expression()
	}

	override fun file(vararg name: String): File {
		return FileUtils.file(__file_dsl_file, *name)
	}
	override fun fileRelative(fileRel: File): String {
		return FileUtils.fileRelative(__file_dsl_file, fileRel)
	}
	override fun mkfile(): File {
		return FileUtils.mkfile(__file_dsl_file)
	}
	override fun delfile(): File {
		return FileUtils.delfile(__file_dsl_file)
	}
	override fun readBytes(): ByteArray {
		return FileUtils.fileBytes(__file_dsl_file)
	}
	override fun readString(): String {
		return FileUtils.fileString(__file_dsl_file)
	}
	override fun writeBytes(content: ByteArray) {
		FileUtils.writeFileBytes(file(), content)
	}
	override fun writeString(content: String) {
		FileUtils.writeFileString(file(), content)
	}

	override operator fun String.unaryMinus() {
		writeString(this)
	}
	override operator fun String.unaryPlus() {
		val old = readString()
		writeString(old + this)
	}
	override operator fun ByteArray.unaryMinus() {
		writeBytes(this)
	}
	override operator fun ByteArray.unaryPlus() {
		val old = readBytes()
		writeBytes(old + this)
	}
}
