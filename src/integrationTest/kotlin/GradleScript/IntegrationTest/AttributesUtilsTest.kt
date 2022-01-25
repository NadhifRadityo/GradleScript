package GradleScript.IntegrationTest

import GradleScript.Strategies.AttributesUtils.a_getBoolean
import GradleScript.Strategies.AttributesUtils.a_getByte
import GradleScript.Strategies.AttributesUtils.a_getChar
import GradleScript.Strategies.AttributesUtils.a_getDouble
import GradleScript.Strategies.AttributesUtils.a_getFile
import GradleScript.Strategies.AttributesUtils.a_getFiles
import GradleScript.Strategies.AttributesUtils.a_getFloat
import GradleScript.Strategies.AttributesUtils.a_getInt
import GradleScript.Strategies.AttributesUtils.a_getLong
import GradleScript.Strategies.AttributesUtils.a_getObject
import GradleScript.Strategies.AttributesUtils.a_getString
import GradleScript.Strategies.AttributesUtils.attributeKey
import GradleScript.Strategies.AttributesUtils.attributeKeyHash
import GradleScript.Strategies.FileUtils.file
import GradleScript.Strategies.JSONUtils.fromJson
import GradleScript.Strategies.JSONUtils.toJson
import GradleScript.Strategies.RuntimeUtils.JAVA_DETECTION_VERSION
import GradleScript.Strategies.StringUtils.randomString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.jar.Attributes

class AttributesUtilsTest: BaseIntegrationTest() {

	@Test
	fun `can compute key hashcode insensitively`() {
		val testKey = "ExAMplE-AtTRiBuTe-KeY"
		val testKey2 = "AnOtHEr-KEY"
		if(JAVA_DETECTION_VERSION <= 8) {
			assertEquals(attributeKeyHash(testKey), testKey.lowercase().hashCode())
			assertEquals(attributeKeyHash(testKey2), testKey2.lowercase().hashCode())
		} else {
			assertEquals(attributeKeyHash(testKey), testKey.uppercase().hashCode())
			assertEquals(attributeKeyHash(testKey2), testKey2.uppercase().hashCode())
		}
	}

	@Test
	fun `can construct attribute key object`() {
		val FIELD_Attributes_Name_name = Attributes.Name::class.java.getDeclaredField("name")
		FIELD_Attributes_Name_name.isAccessible = true
		val randomString = randomString(50)
		val key = attributeKey(randomString)
		assertEquals(FIELD_Attributes_Name_name.get(key), randomString)
	}

	class DefaultObjectJSONRoot {
		lateinit var array: Array<String>
		var obj: Int = 0
	}
	fun prepareAttribute(): Attributes {
		val attribute = Attributes()
		val jsonObject = DefaultObjectJSONRoot()
		jsonObject.array = arrayOf("string1", "string2")
		jsonObject.obj = 2
		attribute.putValue("object", toJson(jsonObject))
		attribute.putValue("string", "string")
		attribute.putValue("file", file("test/testDir/testFile.txt").canonicalPath)
		attribute.putValue("files", listOf(file("test/testDir/testFile.txt"), file("test/testDir2/testFile2.txt")).map { it.canonicalPath }.joinToString(";"))
		attribute.putValue("byte", Byte.MAX_VALUE.toString())
		attribute.putValue("boolean", "true")
		attribute.putValue("char", Char.MAX_VALUE.toString())
		attribute.putValue("int", Int.MAX_VALUE.toString())
		attribute.putValue("long", Long.MAX_VALUE.toString())
		attribute.putValue("float", Float.MAX_VALUE.toString())
		attribute.putValue("double", Double.MAX_VALUE.toString())
		return attribute
	}

	@Test
	fun `can get attribute object`() {
		val attribute = prepareAttribute()
		val obj = a_getObject(attribute, "object", { fromJson(it, DefaultObjectJSONRoot::class.java) })!!
		assertEquals("string1", obj.array[0])
		assertEquals("string2", obj.array[1])
		assertEquals(2, obj.obj)
	}
	
	@Test
	fun `can get attribute string`() {
		val attribute = prepareAttribute()
		val obj = a_getString(attribute, "string")!!
		assertEquals("string", obj)
	}
	
	@Test
	fun `can get attribute file`() {
		val attribute = prepareAttribute()
		val obj = a_getFile(attribute, "file")!!
		assertEquals(file("test/testDir/testFile.txt").canonicalFile, obj)
	}
	
	@Test
	fun `can get attribute files`() {
		val attribute = prepareAttribute()
		val obj = a_getFiles(attribute, "files")!!
		assertEquals(file("test/testDir/testFile.txt").canonicalFile, obj[0])
		assertEquals(file("test/testDir2/testFile2.txt").canonicalFile, obj[1])
	}
	
	@Test
	fun `can get attribute byte`() {
		val attribute = prepareAttribute()
		val obj = a_getByte(attribute, "byte")
		assertEquals(Byte.MAX_VALUE, obj)
	}
	
	@Test
	fun `can get attribute boolean`() {
		val attribute = prepareAttribute()
		val obj = a_getBoolean(attribute, "boolean")
		assertEquals(true, obj)
	}

	@Test
	fun `can get attribute char`() {
		val attribute = prepareAttribute()
		val obj = a_getChar(attribute, "char")
		assertEquals(Char.MAX_VALUE, obj)
	}

	@Test
	fun `can get attribute int`() {
		val attribute = prepareAttribute()
		val obj = a_getInt(attribute, "int")
		assertEquals(Int.MAX_VALUE, obj)
	}

	@Test
	fun `can get attribute long`() {
		val attribute = prepareAttribute()
		val obj = a_getLong(attribute, "long")
		assertEquals(Long.MAX_VALUE, obj)
	}

	@Test
	fun `can get attribute float`() {
		val attribute = prepareAttribute()
		val obj = a_getFloat(attribute, "float")
		assertEquals(Float.MAX_VALUE, obj)
	}

	@Test
	fun `can get attribute double`() {
		val attribute = prepareAttribute()
		val obj = a_getDouble(attribute, "double")
		assertEquals(Double.MAX_VALUE, obj)
	}
}
