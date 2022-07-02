package GradleScript.Strategies

import GradleScript.DynamicScripting.Scripting.addInjectScript
import GradleScript.DynamicScripting.Scripting.removeInjectScript
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.Strategies.ExceptionUtils.exception
import GradleScript.Strategies.FileUtils.file
import GradleScript.Strategies.RuntimeUtils.JAVA_DETECTION_VERSION
import GradleScript.Strategies.UnsafeUtils.unsafe
import java.io.File
import java.lang.ref.WeakReference
import java.util.function.Function
import java.util.jar.Attributes

object AttributesUtils {
	@JvmStatic private var cache: GroovyKotlinCache<AttributesUtils>? = null
	@JvmStatic internal var localAttributesKey = ThreadLocal<WeakReference<Attributes.Name?>?>()
	@JvmStatic internal val AFIELD_Attributes_Name_name: Long
	@JvmStatic internal val AFIELD_Attributes_Name_hashCode: Long

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(AttributesUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	init {
		val FIELD_Attributes_Name_name = Attributes.Name::class.java.getDeclaredField("name")
		val FIELD_Attributes_Name_hashCode = Attributes.Name::class.java.getDeclaredField("hashCode")
		AFIELD_Attributes_Name_name = unsafe.objectFieldOffset(FIELD_Attributes_Name_name)
		AFIELD_Attributes_Name_hashCode = unsafe.objectFieldOffset(FIELD_Attributes_Name_hashCode)
	}

	// Java 17 has implementation which calculates the hash code
	// insensitively on uppercase characters, this should be the correct behaviour
	@ExportGradle @JvmStatic
	fun attributeKeyHash(key: String): Int {
		val length = key.length
		if(length > 70 || length == 0)
			throw IllegalArgumentException(key)
		if(JAVA_DETECTION_VERSION <= 8) {
			val char1 = key.get(0);
			if(!(char1 in 'a'..'z' || char1 in 'A'..'Z' || char1 in '0'..'9'))
				throw IllegalArgumentException("First character must be alphanum");
			for(i in 1 until length) {
				val char = key.get(i)
				if(!(char in 'a'..'z' || char in 'A'..'Z' ||
							char in '0'..'9' || char == '-' || char == '_'))
					throw IllegalArgumentException("Characters must be alphanums, '-' or '_'")
			}
			return key.lowercase().hashCode()
		}
		var hash = 0
		for(i in 0 until length) {
			val char = key.get(i)
			hash = when(char) {
				in 'a'..'z' -> hash * 31 + (char.code - 0x20)
				in 'A'..'Z', in '0'..'9', '_', '-' -> hash * 31 + char.code
				else -> throw IllegalArgumentException(key)
			}
		}
		return hash
	}
	@ExportGradle @JvmStatic
	fun attributeKey(key: String): Attributes.Name {
		var result = localAttributesKey.get()?.get()
		if(result == null) {
			result = try { unsafe.allocateInstance(Attributes.Name::class.java) as Attributes.Name }
				catch(e: InstantiationException) { throw Error(exception(e)) }
			localAttributesKey.set(WeakReference(result))
		}
		unsafe.putObject(result, AFIELD_Attributes_Name_name, key)
		unsafe.putInt(result, AFIELD_Attributes_Name_hashCode, attributeKeyHash(key))
		return result!!
	}

	inline fun <reified T> __a_get(attributes: Attributes, key: String, converter: (String) -> T, defaultValue: T): T {
		val value = attributes[attributeKey(key)] as String?
		return if(value != null) converter(value) else defaultValue
	}
	@ExportGradle @JvmStatic inline fun <reified T> a_getObject(attributes: Attributes, key: String, converter: (String) -> T, defaultValue: T): T { return __a_get(attributes, key, converter, defaultValue) }
	@ExportGradle @JvmStatic inline fun <reified T> a_getObject(attributes: Attributes, key: String, converter: (String) -> T): T? { return __a_get(attributes, key, converter, null) }
	@ExportGradle @JvmStatic fun a_getString(attributes: Attributes, key: String, defaultValue: String): String { return __a_get(attributes, key, { it }, defaultValue) }
	@ExportGradle @JvmStatic fun a_getString(attributes: Attributes, key: String): String? { return __a_get(attributes, key, { it }, null) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getByte(attributes: Attributes, key: String, defaultValue: Byte = 0.toByte()): Byte { return __a_get(attributes, key, { java.lang.Byte.valueOf(it) }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getBoolean(attributes: Attributes, key: String, defaultValue: Boolean = false): Boolean { return __a_get(attributes, key, { java.lang.Boolean.valueOf(it) }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getChar(attributes: Attributes, key: String, defaultValue: Char = 0.toChar()): Char { return __a_get(attributes, key, { it[0] }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getShort(attributes: Attributes, key: String, defaultValue: Short = 0.toShort()): Short { return __a_get(attributes, key, { java.lang.Short.valueOf(it) }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getInt(attributes: Attributes, key: String, defaultValue: Int = 0): Int { return __a_get(attributes, key, { java.lang.Integer.valueOf(it) }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getLong(attributes: Attributes, key: String, defaultValue: Long = 0): Long { return __a_get(attributes, key, { java.lang.Long.valueOf(it) }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getFloat(attributes: Attributes, key: String, defaultValue: Float = 0f): Float { return __a_get(attributes, key, { java.lang.Float.valueOf(it) }, defaultValue) }
	@ExportGradle @JvmStatic @JvmOverloads fun a_getDouble(attributes: Attributes, key: String, defaultValue: Double = 0.0): Double { return __a_get(attributes, key, { java.lang.Double.valueOf(it) }, defaultValue) }

	inline fun <reified T> __a_set(attributes: Attributes, key: String, value: T, converter: (T) -> String?) {
		attributes[attributeKey(key)] = converter(value)
	}
	@ExportGradle @JvmStatic inline fun <reified T> a_setObject(attributes: Attributes, key: String, value: T, converter: (T) -> String?) { __a_set(attributes, key, value, converter) }
	@ExportGradle @JvmStatic fun a_setString(attributes: Attributes, key: String, value: String) { __a_set(attributes, key, value, { it }) }
	@ExportGradle @JvmStatic fun a_setByte(attributes: Attributes, key: String, value: Byte) { __a_set(attributes, key, value, { it.toString() }) }
	@ExportGradle @JvmStatic fun a_setBoolean(attributes: Attributes, key: String, value: Boolean) { __a_set(attributes, key, value, { it.toString() }) }
	@ExportGradle @JvmStatic fun a_setChar(attributes: Attributes, key: String, value: Char) { __a_set(attributes, key, value, { it.toString() }) }
	@ExportGradle @JvmStatic fun a_setShort(attributes: Attributes, key: String, value: Short) { __a_set(attributes, key, value, { it.toString() }) }
	@ExportGradle @JvmStatic fun a_setInt(attributes: Attributes, key: String, value: Int) { __a_set(attributes, key, value, { it.toString() }) }
	@ExportGradle @JvmStatic fun a_setLong(attributes: Attributes, key: String, value: Long) { __a_set(attributes, key, value, { it.toString() }) }
	@ExportGradle @JvmStatic fun a_setFloat(attributes: Attributes, key: String, value: Float) { __a_set(attributes, key, value, { it.toString() }) }
	@ExportGradle @JvmStatic fun a_setDouble(attributes: Attributes, key: String, value: Double) { __a_set(attributes, key, value, { it.toString() }) }

	@ExportGradle @JvmStatic val file_to_string = { it: File? -> it?.canonicalPath }
	@ExportGradle @JvmStatic val files_to_string = { it: Iterable<File?>? -> it?.filterNotNull()?.joinToString(";") { it.canonicalPath } }
	@ExportGradle @JvmStatic val string_to_file = { it: String -> file(it) }
	@ExportGradle @JvmStatic val string_to_files = { it: String -> it.split(";").map { file(it) } }
}
