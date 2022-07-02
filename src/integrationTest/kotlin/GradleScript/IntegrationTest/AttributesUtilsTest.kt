package GradleScript.IntegrationTest

import GradleScript.Strategies.AttributesUtils
import GradleScript.Strategies.AttributesUtils.a_getBoolean
import GradleScript.Strategies.AttributesUtils.a_getByte
import GradleScript.Strategies.AttributesUtils.a_getChar
import GradleScript.Strategies.AttributesUtils.a_getDouble
import GradleScript.Strategies.AttributesUtils.a_getFloat
import GradleScript.Strategies.AttributesUtils.a_getInt
import GradleScript.Strategies.AttributesUtils.a_getLong
import GradleScript.Strategies.AttributesUtils.a_getObject
import GradleScript.Strategies.AttributesUtils.a_getString
import GradleScript.Strategies.AttributesUtils.a_setBoolean
import GradleScript.Strategies.AttributesUtils.a_setByte
import GradleScript.Strategies.AttributesUtils.a_setChar
import GradleScript.Strategies.AttributesUtils.a_setDouble
import GradleScript.Strategies.AttributesUtils.a_setFloat
import GradleScript.Strategies.AttributesUtils.a_setInt
import GradleScript.Strategies.AttributesUtils.a_setLong
import GradleScript.Strategies.AttributesUtils.a_setObject
import GradleScript.Strategies.AttributesUtils.a_setString
import GradleScript.Strategies.AttributesUtils.attributeKey
import GradleScript.Strategies.AttributesUtils.attributeKeyHash
import GradleScript.Strategies.FileUtils.file
import GradleScript.Strategies.JSONUtils.fromJson
import GradleScript.Strategies.JSONUtils.toJson
import GradleScript.Strategies.RuntimeUtils.JAVA_DETECTION_VERSION
import GradleScript.TestFixtures.RandomDSLContext
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.jar.Attributes

class AttributesUtilsTest: BaseIntegrationTest(), RandomDSLContext {

	@Test
	fun `can compute key hashcode insensitively`() {
		val testKey = "ExAMplE-AtTRiBuTe-KeY-${randomString()}"
		val testKey2 = "AnOtHEr-KEY-${randomString(10)}"
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

	@Test
	fun `can set and get attribute object`() {
		val attribute = Attributes()
		val jsonObject = DefaultObjectJSONRoot()
		jsonObject.array = (0..5).map { randomString() }.toTypedArray()
		jsonObject.obj = nextInt()
		a_setObject(attribute, "object", jsonObject, { toJson(it) })

		val obj = a_getObject(attribute, "object", { fromJson(it, DefaultObjectJSONRoot::class.java) })!!
		assertArrayEquals(jsonObject.array, obj.array)
		assertEquals(jsonObject.obj, obj.obj)
	}
	
	@Test
	fun `can set and get attribute string`() {
		val attribute = Attributes()
		val string = randomString(20)
		a_setString(attribute, "string", string)

		val obj = a_getString(attribute, "string")
		assertEquals(string, obj)
	}
	
	@Test
	fun `can get attribute file`() {
		val attribute = Attributes()
		val file = file("test/testDir${randomString(5)}/testFile${randomString(10)}.txt")
		a_setObject(attribute, "file", file, AttributesUtils.file_to_string)
		
		val obj = a_getObject(attribute, "file", AttributesUtils.string_to_file)!!
		assertEquals(file.canonicalFile, obj.canonicalFile)
	}
	
	@Test
	fun `can get attribute files`() {
		val attribute = Attributes()
		val files = listOf(
			file("test/testDir${randomString(5)}/testFile${randomString(10)}.txt"),
			file("test/testDir${randomString(5)}/testFile${randomString(10)}.txt")
		)
		a_setObject(attribute, "files", files, AttributesUtils.files_to_string)
		
		val obj = a_getObject(attribute, "files", AttributesUtils.string_to_files)!!
		assertEquals(files[0].canonicalFile, obj[0].canonicalFile)
		assertEquals(files[1].canonicalFile, obj[1].canonicalFile)
	}
	
	@Test
	fun `can get attribute byte`() {
		val attribute = Attributes()
		val byte = nextInt().toByte()
		a_setByte(attribute, "byte", byte)

		val obj = a_getByte(attribute, "byte")
		assertEquals(byte, obj)
	}
	
	@Test
	fun `can get attribute boolean`() {
		val attribute = Attributes()
		val boolean = nextBoolean()
		a_setBoolean(attribute, "boolean", boolean)
		
		val obj = a_getBoolean(attribute, "boolean")
		assertEquals(boolean, obj)
	}

	@Test
	fun `can get attribute char`() {
		val attribute = Attributes()
		val char = nextInt().toChar()
		a_setChar(attribute, "char", char)
		
		val obj = a_getChar(attribute, "char")
		assertEquals(char, obj)
	}

	@Test
	fun `can get attribute int`() {
		val attribute = Attributes()
		val int = nextInt()
		a_setInt(attribute, "int", int)
		
		val obj = a_getInt(attribute, "int")
		assertEquals(int, obj)
	}

	@Test
	fun `can get attribute long`() {
		val attribute = Attributes()
		val long = nextLong()
		a_setLong(attribute, "long", long)
		
		val obj = a_getLong(attribute, "long")
		assertEquals(long, obj)
	}

	@Test
	fun `can get attribute float`() {
		val attribute = Attributes()
		val float = nextFloat()
		a_setFloat(attribute, "float", float)
		
		val obj = a_getFloat(attribute, "float")
		assertEquals(float, obj)
	}

	@Test
	fun `can get attribute double`() {
		val attribute = Attributes()
		val double = nextDouble()
		a_setDouble(attribute, "double", double)
		
		val obj = a_getDouble(attribute, "double")
		assertEquals(double, obj)
	}
}
