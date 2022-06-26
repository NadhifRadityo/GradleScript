package GradleScript.Strategies

import GradleScript.DynamicScripting.Scripting.addInjectScript
import GradleScript.DynamicScripting.Scripting.removeInjectScript
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.Strategies.CommonUtils.bytesToHexString
import GradleScript.Strategies.CommonUtils.hexStringToBytes
import GradleScript.Strategies.FileUtils.fileString
import GradleScript.Strategies.LoggerUtils.ldebug
import GradleScript.Strategies.ProcessUtils.getCommandOutput
import GradleScript.Strategies.ProgressUtils.prog
import GradleScript.Strategies.UnsafeUtils.tempByteArray
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern

object HashUtils {
	@JvmStatic private var cache: GroovyKotlinCache<HashUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(HashUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun checksumJavaNative(file: File, digest: String): ByteArray {
		prog(arrayOf(file, digest), HashUtils::class.java, "Creating java checksum", true).use { prog0 ->
			prog0.pdo("Checksum Java ($digest) ${file.path}")
			ldebug("Creating java checksum $digest: ${file.path}")
			FileInputStream(file).use { fileInputStream ->
				val messageDigest = MessageDigest.getInstance(digest)
				val buffer: ByteArray = tempByteArray(8192)
				var read: Int
				while(fileInputStream.read(buffer).also { read = it } != -1) messageDigest.update(buffer, 0, read)
				return messageDigest.digest()
			}
		}
	}
	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun checksumJavaNative(bytes: ByteArray, digest: String): ByteArray {
		prog(arrayOf(bytes, digest), HashUtils::class.java, "Creating java checksum", true).use { prog0 ->
			prog0.pdo("Checksum Java ($digest) [Array]")
			ldebug("Creating java checksum $digest: [Array]")
			val messageDigest = MessageDigest.getInstance(digest)
			messageDigest.update(bytes, 0, bytes.size)
			return messageDigest.digest()
		}
	}
	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun checksumExeCertutil(exe: File, file: File, digest: String): ByteArray {
		prog(arrayOf(exe, file, digest), HashUtils::class.java, "Creating certutil checksum", true).use { prog0 ->
			prog0.pdo("Checksum certutil ($digest) ${file.path}")
			ldebug("Creating exe certutil (${exe.path}) checksum $digest: ${file.path}")
			val commandOutput = getCommandOutput(exe.canonicalPath, "-hashfile", file.canonicalPath, digest)
				?: throw Error("Error occurred when running command")
			return hexStringToBytes(commandOutput.split("\n")[1].trim())
		}
	}
	@JvmStatic
	internal val lastHashPattern = Pattern.compile("\\s+([a-zA-Z0-9]+)\\s*$")
	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun checksumExeOpenssl(exe: File, file: File, digest: String): ByteArray {
		prog(arrayOf(exe, file, digest), HashUtils::class.java, "Creating openssl checksum", true).use { prog0 ->
			prog0.pdo("Checksum openssl ($digest) ${file.path}")
			ldebug("Creating exe openssl (${exe.path}) checksum $digest: ${file.path}")
			val commandOutput = getCommandOutput(exe.canonicalPath, digest, file.canonicalPath)
				?: throw Error("Error occurred when running command")
			val matcher = lastHashPattern.matcher(commandOutput)
			matcher.find()
			return hexStringToBytes(matcher.group(1))
		}
	}
	@JvmStatic
	internal val firstHashPattern = Pattern.compile("^\\s*[/\\\\]?([a-zA-Z0-9]+)\\s+")
	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun checksumExeMdNsum(exe: File, file: File, N: Int): ByteArray {
		prog(arrayOf(exe, file, N), HashUtils::class.java, "Creating md${N}sum checksum", true).use { prog0 ->
			prog0.pdo("Checksum md${N}sum ${file.path}")
			ldebug("Creating exe md${N}sum (${exe.path}) checksum: ${file.path}")
			val commandOutput = getCommandOutput(exe.canonicalPath, file.canonicalPath)
				?: throw Error("Error occurred when running command")
			val matcher = firstHashPattern.matcher(commandOutput)
			matcher.find()
			return hexStringToBytes(matcher.group(1))
		}
	}
	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun checksumExeShaNsum(exe: File, file: File, N: Int): ByteArray {
		prog(arrayOf(exe, file, N), HashUtils::class.java, "Creating sha${N}sum checksum", true).use { prog0 ->
			prog0.pdo("Checksum sha${N}sum ${file.path}")
			ldebug("Creating exe sha${N}sum (${exe.path}) checksum: ${file.path}")
			val commandOutput = getCommandOutput(exe.canonicalPath, file.canonicalPath)
				?: throw Error("Error occurred when running command")
			val matcher = firstHashPattern.matcher(commandOutput)
			matcher.find()
			return hexStringToBytes(matcher.group(1))
		}
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun getHashExpected(obj: Any?): String {
		return when(obj) {
			is String -> obj
			is ByteArray -> bytesToHexString(obj)
			is File -> fileString(obj)
			else -> throw IllegalStateException()
		}
	}
}
