package GradleScript.Strategies

import GradleScript.DynamicScripting.Scripting.addInjectScript
import GradleScript.DynamicScripting.Scripting.removeInjectScript
import GradleScript.GroovyKotlinInteroperability.ExportGradle
import GradleScript.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GradleScript.GroovyKotlinInteroperability.GroovyKotlinCache
import GradleScript.Strategies.UnsafeUtils.unsafe
import java.util.*

object PropertiesUtils {
    @JvmStatic private var cache: GroovyKotlinCache<PropertiesUtils>? = null
    @JvmStatic internal val AFIELD_Properties_defaults: Long

    @JvmStatic
    fun construct() {
        cache = prepareGroovyKotlinCache(PropertiesUtils)
        addInjectScript(cache!!)
    }
    @JvmStatic
    fun destruct() {
        removeInjectScript(cache!!)
        cache = null
    }

    init {
        val FIELD_Properties_defaults = Properties::class.java.getDeclaredField("defaults")
        AFIELD_Properties_defaults = unsafe.objectFieldOffset(FIELD_Properties_defaults)
    }

    @ExportGradle @JvmStatic
    fun __get_defaults_properties(properties: Properties): Properties? {
        return unsafe.getObject(properties, AFIELD_Properties_defaults) as Properties?
    }
    @ExportGradle @JvmStatic
    fun extendProperties(original: Properties, extend: Properties): Properties {
        val properties = Properties(extend)
        properties.putAll(original)
        return properties
    }
    @ExportGradle @JvmStatic
    fun copyAllProperties(properties: Properties): Properties {
        val result = Properties()
        enumerateAllProperties(properties, result)
        return result
    }
    @ExportGradle @JvmStatic
    fun copyNonDefaultProperties(properties: Properties): Properties {
        val result = Properties()
        enumerateNonDefaultProperties(properties, result)
        return result
    }
    @ExportGradle @JvmStatic
    fun enumerateAllProperties(properties: Properties, hashtable: Hashtable<Any?, Any?>) {
        val defaults = __get_defaults_properties(properties)
        if(defaults != null) enumerateAllProperties(defaults, hashtable)
        enumerateNonDefaultProperties(properties, hashtable)
    }
    @ExportGradle @JvmStatic
    fun enumerateNonDefaultProperties(properties: Properties, hashtable: Hashtable<Any?, Any?>) {
        val enumeration = properties.keys()
        while(enumeration.hasMoreElements()) {
            val key = enumeration.nextElement()
            val value = properties[key]
            hashtable[key] = value
        }
    }
    @ExportGradle @JvmStatic
    fun sizeAllProperties(properties: Properties): Int {
        val defaults = __get_defaults_properties(properties)
        return (if(defaults != null) sizeAllProperties(defaults) else 0) + sizeNonDefaultProperties(properties)
    }
    @ExportGradle @JvmStatic
    fun sizeNonDefaultProperties(properties: Properties): Int {
        return properties.size
    }

    inline fun <reified T> __pn_get(properties: Properties, key: String, defaultValue: T, type: Class<T> = T::class.java): T {
        val obj = properties[key]
        return if(type.isInstance(obj)) obj as T else defaultValue
    }
    @ExportGradle @JvmStatic @JvmOverloads inline fun <reified T> pn_getObject(properties: Properties, key: String, type: Class<T> = T::class.java, defaultValue: T): T { return __pn_get(properties, key, defaultValue, type) }
    @ExportGradle @JvmStatic @JvmOverloads inline fun <reified T> pn_getObject(properties: Properties, key: String, type: Class<T> = T::class.java): T? { return __pn_get(properties, key, null, type as Class<T?>) }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getByte(properties: Properties, key: String, defaultValue: Byte = 0.toByte()): Byte { return __pn_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getBoolean(properties: Properties, key: String, defaultValue: Boolean = false): Boolean { return __pn_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getChar(properties: Properties, key: String, defaultValue: Char = 0.toChar()): Char { return __pn_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getShort(properties: Properties, key: String, defaultValue: Short = 0.toShort()): Short { return __pn_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getInt(properties: Properties, key: String, defaultValue: Int = 0): Int { return __pn_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getLong(properties: Properties, key: String, defaultValue: Long = 0): Long { return __pn_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getFloat(properties: Properties, key: String, defaultValue: Float = 0f): Float { return __pn_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun pn_getDouble(properties: Properties, key: String, defaultValue: Double = 0.0): Double { return __pn_get(properties, key, defaultValue) }

    inline fun <reified T> __p_get(properties: Properties, key: String, defaultValue: T, type: Class<T> = T::class.java): T {
        var currentProperties: Properties? = properties
        while(currentProperties != null) {
            val obj = properties[key]
            val castedObject = if(type.isInstance(obj)) obj as T else null
            if(castedObject != null) return castedObject
            currentProperties = __get_defaults_properties(currentProperties)
        }
        return defaultValue
    }
    @ExportGradle @JvmStatic @JvmOverloads inline fun <reified T> p_getObject(properties: Properties, key: String, type: Class<T> = T::class.java, defaultValue: T): T { return __p_get(properties, key, defaultValue, type) }
    @ExportGradle @JvmStatic @JvmOverloads inline fun <reified T> p_getObject(properties: Properties, key: String, type: Class<T> = T::class.java): T? { return __p_get(properties, key, null, type as Class<T?>) }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getByte(properties: Properties, key: String, defaultValue: Byte = 0.toByte()): Byte { return __p_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getBoolean(properties: Properties, key: String, defaultValue: Boolean = false): Boolean { return __p_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getChar(properties: Properties, key: String, defaultValue: Char = 0.toChar()): Char { return __p_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getShort(properties: Properties, key: String, defaultValue: Short = 0.toShort()): Short { return __p_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getInt(properties: Properties, key: String, defaultValue: Int = 0): Int { return __p_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getLong(properties: Properties, key: String, defaultValue: Long = 0): Long { return __p_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getFloat(properties: Properties, key: String, defaultValue: Float = 0f): Float { return __p_get(properties, key, defaultValue) }
    @ExportGradle @JvmStatic @JvmOverloads fun p_getDouble(properties: Properties, key: String, defaultValue: Double = 0.0): Double { return __p_get(properties, key, defaultValue) }

    inline fun <reified T> __p_set(properties: Properties, key: String, value: T) {
        if(value == null) return
        properties[key] = value
    }
    @ExportGradle @JvmStatic inline fun <reified T> p_setObject(properties: Properties, key: String, value: T) { __p_set(properties, key, value) }
    @ExportGradle @JvmStatic fun p_setByte(properties: Properties, key: String, value: Byte) { __p_set(properties, key, value) }
    @ExportGradle @JvmStatic fun p_setBoolean(properties: Properties, key: String, value: Boolean) { __p_set(properties, key, value) }
    @ExportGradle @JvmStatic fun p_setChar(properties: Properties, key: String, value: Char) { __p_set(properties, key, value) }
    @ExportGradle @JvmStatic fun p_setShort(properties: Properties, key: String, value: Short) { __p_set(properties, key, value) }
    @ExportGradle @JvmStatic fun p_setInt(properties: Properties, key: String, value: Int) { __p_set(properties, key, value) }
    @ExportGradle @JvmStatic fun p_setLong(properties: Properties, key: String, value: Long) { __p_set(properties, key, value) }
    @ExportGradle @JvmStatic fun p_setFloat(properties: Properties, key: String, value: Float) { __p_set(properties, key, value) }
    @ExportGradle @JvmStatic fun p_setDouble(properties: Properties, key: String, value: Double) { __p_set(properties, key, value) }
}
