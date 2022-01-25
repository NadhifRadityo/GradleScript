package GradleScript.Strategies

import GradleScript.DynamicScripting.Scripting.addInjectScript
import GradleScript.DynamicScripting.Scripting.removeInjectScript
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.Strategies.FileUtils.fileString
import GradleScript.Strategies.FileUtils.mkfile
import GradleScript.Strategies.FileUtils.writeFileString
import GradleScript.Strategies.LoggerUtils.linfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonWriter
import java.io.*
import java.nio.charset.StandardCharsets

object JSONUtils {
	@JvmStatic private var cache: GroovyKotlinCache<JSONUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(JSONUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic
	fun <T> fromJson(reader: Reader, clazz: Class<T>): T { return Gson().fromJson(reader, clazz) }
	@ExportGradle @JvmStatic
	fun <T> fromJson(stream: InputStream, clazz: Class<T>): T { return Gson().fromJson(InputStreamReader(stream), clazz) }
	@ExportGradle @JvmStatic
	fun <T> fromJson(string: String, clazz: Class<T>): T { return Gson().fromJson(string, clazz) }
	@ExportGradle @JvmStatic @Throws(IOException::class)
	fun <T> fromJson(file: File, clazz: Class<T>?): T { return Gson().fromJson(fileString(file), clazz) }

	@ExportGradle @JvmStatic @Throws(IOException::class)
	fun <T> toJson(obj: T): String {
		StringWriter().use { stringWriter ->
			JsonWriter(stringWriter).use { jsonWriter ->
				jsonWriter.setIndent("\t")
				GsonBuilder().disableHtmlEscaping().create().toJson(obj, (obj as Any).javaClass, jsonWriter)
				return stringWriter.toString()
			}
		}
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun <T> toJsonFile(obj: T, target: File): String {
		val stringOut = toJson(obj)
		mkfile(target)
		writeFileString(target, stringOut, StandardCharsets.UTF_8)
		linfo("Configurations written to: ${target.path}")
		return stringOut
	}
}
