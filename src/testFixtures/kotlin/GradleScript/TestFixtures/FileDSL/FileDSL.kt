package GradleScript.TestFixtures.FileDSL

import GradleScript.Strategies.FileUtils
import java.io.File
import java.nio.file.StandardCopyOption

typealias FileDSLExpression<RECEIVER, RESULT> = RECEIVER.() -> RESULT
typealias FileDSLGenerator<RECEIVER> = (FileDSL<RECEIVER>, File) -> RECEIVER
interface FileDSL<RECEIVER: FileDSL<RECEIVER>> {
	val __file_dsl_generator: FileDSLGenerator<RECEIVER>
	val __file_dsl_file: File
	val __file_dsl_instance: RECEIVER

	fun <RESULT> files(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT
	fun <RESULT> File.files(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT

	operator fun String.not(): File
	operator fun <RESULT> String.times(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT
	// Technically same as `"/testDir" * `
	operator fun <RESULT> String.div(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT

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
open class FileDSLImpl<RECEIVER: FileDSL<RECEIVER>>(
	override val __file_dsl_generator: FileDSLGenerator<RECEIVER>,
	override val __file_dsl_file: File
): FileDSL<RECEIVER> {
	override val __file_dsl_instance: RECEIVER
		get() = this as RECEIVER

	override fun <RESULT> files(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT =
		__file_dsl_generator(__file_dsl_instance, __file_dsl_file).expression()
	override fun <RESULT> File.files(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT =
		__file_dsl_generator(__file_dsl_instance, this).expression()

	override operator fun String.not(): File {
		return if(!this.startsWith("/")) mkfile(this)
		else mkdir(this)
	}
	override operator fun <RESULT> String.times(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT {
		return __file_dsl_generator(__file_dsl_instance, !this).expression()
	}
	override operator fun <RESULT> String.div(expression: FileDSLExpression<RECEIVER, RESULT>): RESULT {
		return __file_dsl_generator(__file_dsl_instance, !"/$this").expression()
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
