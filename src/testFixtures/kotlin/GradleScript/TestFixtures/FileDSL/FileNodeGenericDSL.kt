package GradleScript.TestFixtures.FileDSL

import GradleScript.Strategies.FileUtils
import java.io.File
import java.nio.file.StandardCopyOption

typealias FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT> = GENERIC_RECEIVER.() -> RESULT
typealias FileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER> = (FileDSLData<*, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>, File) -> GENERIC_RECEIVER
@FileDSLMarker
interface FileNodeGenericDSL<
	GENERIC_RECEIVER: FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	FILE_RECEIVER: FileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>
>: FileDSLData<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER> {
	fun <RESULT> asNodeGeneric(expression: FileNodeGenericDSLExpression<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER, RESULT>): RESULT
	fun <RESULT> asNodeFile(expression: FileNodeFileDSLExpression<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER, RESULT>): RESULT
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
	fun mkfile(): File
	fun mkdir(vararg name: String): File
	fun mkdir(): File
	fun delfile(vararg name: String): File
	fun delfile(): File
	fun readBytes(vararg name: String): ByteArray
	fun readBytes(): ByteArray
	fun readString(vararg name: String): String
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
	// "test.txt" -= "replace content"
	operator fun String.minusAssign(content: String)
	// "test.txt" += "append content"
	operator fun String.plusAssign(content: String)
	// "test.txt" % "replace content".encodeToByteArray()
	operator fun String.rem(content: ByteArray)
	// "test.txt" .. "append content".encodeToByteArray()
	operator fun String.rangeTo(content: ByteArray)
}
open class FileNodeGenericDSLImpl<
	GENERIC_RECEIVER: FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	FILE_RECEIVER: FileNodeFileDSL<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	DIRECTORY_RECEIVER: FileNodeDirectoryDSL<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>
>(
	override val __file_dsl_file_generator: FileNodeFileDSLGenerator<FILE_RECEIVER, DIRECTORY_RECEIVER, GENERIC_RECEIVER>,
	override val __file_dsl_directory_generator: FileNodeDirectoryDSLGenerator<DIRECTORY_RECEIVER, FILE_RECEIVER, GENERIC_RECEIVER>,
	override val __file_dsl_generic_generator: FileNodeGenericDSLGenerator<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER>,
	override val __file_dsl_file: File,
): FileNodeGenericDSL<GENERIC_RECEIVER, FILE_RECEIVER, DIRECTORY_RECEIVER> {
	override val __file_dsl_instance: GENERIC_RECEIVER
		get() = this as GENERIC_RECEIVER

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
	override fun mkfile(): File {
		return FileUtils.mkfile(__file_dsl_file)
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
	override fun readBytes(): ByteArray {
		return FileUtils.fileBytes(__file_dsl_file)
	}
	override fun readString(vararg name: String): String {
		return FileUtils.fileString(file(*name))
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
