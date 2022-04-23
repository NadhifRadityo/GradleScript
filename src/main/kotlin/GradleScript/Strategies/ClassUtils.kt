package GradleScript.Strategies

import GradleScript.DynamicScripting.Scripting.addInjectScript
import GradleScript.DynamicScripting.Scripting.removeInjectScript
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.Strategies.ExceptionUtils.exception
import GradleScript.Strategies.RuntimeUtils.JAVA_DETECTION_VERSION
import GradleScript.Strategies.UnsafeUtils.unsafe
import groovy.lang.MetaClass
import org.codehaus.groovy.runtime.InvokerHelper.getMetaClass
import java.io.File
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

object ClassUtils {
	@JvmStatic private var cache: GroovyKotlinCache<ClassUtils>? = null
	@ExportGradle @JvmStatic
	val defaultClassLoader = ClassUtils::class.java.classLoader
	@ExportGradle @JvmStatic
	val boxedToPrimitive = mapOf<Class<*>, Class<*>>(
		Void::class.javaObjectType to Void::class.javaPrimitiveType!!,
		Int::class.javaObjectType to Int::class.javaPrimitiveType!!,
		Long::class.javaObjectType to Long::class.javaPrimitiveType!!,
		Short::class.javaObjectType to Short::class.javaPrimitiveType!!,
		Float::class.javaObjectType to Float::class.javaPrimitiveType!!,
		Double::class.javaObjectType to Double::class.javaPrimitiveType!!,
		Char::class.javaObjectType to Char::class.javaPrimitiveType!!,
		Byte::class.javaObjectType to Byte::class.javaPrimitiveType!!,
		Boolean::class.javaObjectType to Boolean::class.javaPrimitiveType!!)
	@ExportGradle @JvmStatic
	val primitiveToBoxed = mapOf<Class<*>, Class<*>>(
		Void::class.javaPrimitiveType!! to Void::class.javaObjectType,
		Int::class.javaPrimitiveType!! to Int::class.javaObjectType,
		Long::class.javaPrimitiveType!! to Long::class.javaObjectType,
		Short::class.javaPrimitiveType!! to Short::class.javaObjectType,
		Float::class.javaPrimitiveType!! to Float::class.javaObjectType,
		Double::class.javaPrimitiveType!! to Double::class.javaObjectType,
		Char::class.javaPrimitiveType!! to Char::class.javaObjectType,
		Byte::class.javaPrimitiveType!! to Byte::class.javaObjectType,
		Boolean::class.javaPrimitiveType!! to Boolean::class.javaObjectType)
	@ExportGradle @JvmStatic
	val primitiveWideningConversions = mapOf<Class<*>, Array<Class<*>>>(
		Byte::class.javaPrimitiveType!! to arrayOf(Byte::class.javaPrimitiveType!!, Short::class.javaPrimitiveType!!, Int::class.javaPrimitiveType!!, Long::class.javaPrimitiveType!!, Float::class.javaPrimitiveType!!, Double::class.javaPrimitiveType!!),
		Short::class.javaPrimitiveType!! to arrayOf(Short::class.javaPrimitiveType!!, Int::class.javaPrimitiveType!!, Long::class.javaPrimitiveType!!, Float::class.javaPrimitiveType!!, Double::class.javaPrimitiveType!!),
		Char::class.javaPrimitiveType!! to arrayOf(Char::class.javaPrimitiveType!!, Int::class.javaPrimitiveType!!, Long::class.javaPrimitiveType!!, Float::class.javaPrimitiveType!!, Double::class.javaPrimitiveType!!),
		Int::class.javaPrimitiveType!! to arrayOf(Int::class.javaPrimitiveType!!, Long::class.javaPrimitiveType!!, Float::class.javaPrimitiveType!!, Double::class.javaPrimitiveType!!),
		Long::class.javaPrimitiveType!! to arrayOf(Long::class.javaPrimitiveType!!, Float::class.javaPrimitiveType!!, Double::class.javaPrimitiveType!!),
		Float::class.javaPrimitiveType!! to arrayOf(Float::class.javaPrimitiveType!!, Double::class.javaPrimitiveType!!),
		Double::class.javaPrimitiveType!! to arrayOf(Double::class.javaPrimitiveType!!))

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(ClassUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic
	fun <T> classForName(name: String?, initialize: Boolean = true, loader: ClassLoader? = defaultClassLoader): Class<out T>? {
		return try { Class.forName(name, initialize, loader) as Class<out T> } catch(e: Exception) { exception(e); null } }
	@ExportGradle @JvmStatic @Throws(ClassNotFoundException::class)
	fun <T> classForName0(name: String?, initialize: Boolean = true, loader: ClassLoader? = defaultClassLoader): Class<out T> {
		return Class.forName(name, initialize, loader) as Class<out T> }
	@ExportGradle @JvmStatic
	fun metaClassFor(clazz: Class<*>): MetaClass? { return getMetaClass(clazz) }
	@ExportGradle @JvmStatic
	fun metaClassFor(obj: Any): MetaClass? { return getMetaClass(obj) }
	@ExportGradle @JvmStatic
	fun metaClassFor0(clazz: Class<*>): MetaClass { return getMetaClass(clazz)!! }
	@ExportGradle @JvmStatic
	fun metaClassFor0(obj: Any): MetaClass { return getMetaClass(obj)!! }

	@ExportGradle @JvmStatic
	fun overrideFinal(obj: Any?, field: Field, newValue: Any?) {
		val useUnsafe: Boolean = JAVA_DETECTION_VERSION > 12
		var exception: Throwable? = null
		try {
			if(!useUnsafe) {
				val modifiersField = Field::class.java.getDeclaredField("modifiers")
				modifiersField.isAccessible = true
				modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
				field.isAccessible = true
				if(field.type == Byte::class.java && newValue is Byte)
					field.setByte(obj, newValue)
				else if(field.type == Boolean::class.java && newValue is Boolean)
					field.setBoolean(obj, newValue)
				else if(field.type == Char::class.java && newValue is Char)
					field.setChar(obj, newValue)
				else if(field.type == Short::class.java && newValue is Short)
					field.setShort(obj, newValue)
				else if(field.type == Int::class.java && newValue is Int)
					field.setInt(obj, newValue)
				else if(field.type == Long::class.java && newValue is Long)
					field.setLong(obj, newValue)
				else if(field.type == Float::class.java && newValue is Float)
					field.setFloat(obj, newValue)
				else if(field.type == Double::class.java && newValue is Double)
					field.setDouble(obj, newValue)
				else if(field.type.isPrimitive)
					throw IllegalArgumentException("Cannot cast object to primitive")
				else
					field.set(obj, newValue)
			}
		} catch(e: Throwable) { exception = exception(e) }
		if(!useUnsafe && exception == null) return
		try {
			val fieldObject = obj ?: unsafe.staticFieldBase(field)
			val fieldOffset = if(obj != null) unsafe.objectFieldOffset(field) else unsafe.staticFieldOffset(field)
			if(field.type == Byte::class.java && newValue is Byte)
				unsafe.putByte(fieldObject, fieldOffset, newValue)
			else if(field.type == Boolean::class.java && newValue is Boolean)
				unsafe.putBoolean(fieldObject, fieldOffset, newValue)
			else if(field.type == Char::class.java && newValue is Char)
				unsafe.putChar(fieldObject, fieldOffset, newValue)
			else if(field.type == Short::class.java && newValue is Short)
				unsafe.putShort(fieldObject, fieldOffset, newValue)
			else if(field.type == Int::class.java && newValue is Int)
				unsafe.putInt(fieldObject, fieldOffset, newValue)
			else if(field.type == Long::class.java && newValue is Long)
				unsafe.putLong(fieldObject, fieldOffset, newValue)
			else if(field.type == Float::class.java && newValue is Float)
				unsafe.putFloat(fieldObject, fieldOffset, newValue)
			else if(field.type == Double::class.java && newValue is Double)
				unsafe.putDouble(fieldObject, fieldOffset, newValue)
			else if(field.type.isPrimitive)
				throw IllegalArgumentException("Cannot cast object to primitive")
			else
				unsafe.putObject(fieldObject, fieldOffset, newValue)
		} catch(e: Throwable) {
			if(exception != null) e.addSuppressed(exception)
			throw Error(exception(e))
		}
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun currentClassFile(clazz: Class<*>): File {
		var result = File(URLDecoder.decode(clazz.protectionDomain.codeSource.location.path, StandardCharsets.UTF_8.name()))
		if(result.isDirectory) result = File(result, clazz.simpleName + ".class")
		return result
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun currentClassFile(vararg notFilter: String): File? {
		val currentClass = classForName<Any>(callerUserImplementedClass(*notFilter))
		return if(currentClass == null) null else currentClassFile(currentClass)
	}

	val defaultNotPackageFilter = listOf("java", "jdk", "sun", "kotlin", "groovy", "org.gradle", "org.apache", "org.codehaus", "Gradle", "DUMMY$")
	@ExportGradle @JvmStatic
	fun callerUserImplementedClass(vararg notFilter: String): String? {
		return Thread.currentThread().stackTrace.first { s ->
			val className = s.className
			defaultNotPackageFilter.find { className.startsWith(it) } == null &&
					notFilter.find { className.startsWith(it) } == null
		}?.className
	}

	@ExportGradle @JvmStatic
	fun boxedWideningConversion(value: Any, type: Class<*>): Any {
		if(value is Byte) {
			if(type == Byte::class.java) return value.toByte()
			if(type == Short::class.java) return value.toShort()
			if(type == Int::class.java) return value.toInt()
			if(type == Long::class.java) return value.toLong()
			if(type == Float::class.java) return value.toFloat()
			if(type == Double::class.java) return value.toDouble()
		}
		if(value is Short) {
			if(type == Short::class.java) return value.toShort()
			if(type == Int::class.java) return value.toInt()
			if(type == Long::class.java) return value.toLong()
			if(type == Float::class.java) return value.toFloat()
			if(type == Double::class.java) return value.toDouble()
		}
		if(value is Char) {
			if(type == Char::class.java) return value.toChar()
			if(type == Int::class.java) return value.toInt()
			if(type == Long::class.java) return value.toLong()
			if(type == Float::class.java) return value.toFloat()
			if(type == Double::class.java) return value.toDouble()
		}
		if(value is Int) {
			if(type == Int::class.java) return value.toInt()
			if(type == Long::class.java) return value.toLong()
			if(type == Float::class.java) return value.toFloat()
			if(type == Double::class.java) return value.toDouble()
		}
		if(value is Long) {
			if(type == Long::class.java) return value.toLong()
			if(type == Float::class.java) return value.toFloat()
			if(type == Double::class.java) return value.toDouble()
		}
		if(value is Float) {
			if(type == Float::class.java) return value.toFloat()
			if(type == Double::class.java) return value.toDouble()
		}
		if(value is Double) {
			if(type == Double::class.java) return value.toDouble()
		}
		throw ClassCastException("Cannot widening conversion from ${value.javaClass} to $type")
	}
}
