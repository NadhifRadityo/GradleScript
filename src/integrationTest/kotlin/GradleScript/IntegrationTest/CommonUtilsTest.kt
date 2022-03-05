package GradleScript.IntegrationTest

import GradleScript.Strategies.CommonUtils.bytesToHexString
import GradleScript.Strategies.CommonUtils.formattedUrl
import GradleScript.Strategies.CommonUtils.hexStringToBytes
import GradleScript.Strategies.CommonUtils.humanReadableByteCount
import GradleScript.Strategies.CommonUtils.purgeThreadLocal
import GradleScript.Strategies.CommonUtils.urlToUri
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.URL

class CommonUtilsTest: BaseIntegrationTest() {

	@Test
	fun `can convert url to uri`() {
		val value = "http://user:password@localhost:8080/websocket test?query#ref"
		val expected = "http://user:password@localhost:8080/websocket%20test?query#ref"
		val url = URL(value)
		val uri = urlToUri(url)
		assertEquals(expected, uri.toString())
	}

	@Test
	fun `can format url to standard url`() {
		val value = "http://user:password@localhost:8080/websocket test?query#ref"
		val expected = "http://user:password@localhost:8080/websocket%20test?query#ref"
		val result = formattedUrl(value)
		assertEquals(expected, result)
	}

	@Test
	fun `can convert bytes to hex string`() {
		val hexString = "ABADCAFEBAADF00DBAAAAAADBBADBEEFCAFED00D"
		val bytes = intArrayOf(0xAB, 0xAD, 0xCA, 0xFE, 0xBA, 0xAD, 0xF0, 0x0D, 0xBA, 0xAA, 0xAA, 0xAD, 0xBB, 0xAD, 0xBE, 0xEF, 0xCA, 0xFE, 0xD0, 0x0D).map { it.toByte() }.toByteArray()
		val result = hexStringToBytes(hexString)
		assertArrayEquals(bytes, result)
	}

	@Test
	fun `can convert hex string to bytes`() {
		val hexString = "ABADCAFEBAADF00DBAAAAAADBBADBEEFCAFED00D"
		val bytes = intArrayOf(0xAB, 0xAD, 0xCA, 0xFE, 0xBA, 0xAD, 0xF0, 0x0D, 0xBA, 0xAA, 0xAA, 0xAD, 0xBB, 0xAD, 0xBE, 0xEF, 0xCA, 0xFE, 0xD0, 0x0D).map { it.toByte() }.toByteArray()
		val result = bytesToHexString(bytes).uppercase()
		assertEquals(hexString, result)
	}

	@Test
	fun `can purge thread locals`() {
		val threadLocal = ThreadLocal<String?>()
		threadLocal.set("test purge local")
		assertEquals(threadLocal.get(), "test purge local")
		purgeThreadLocal(threadLocal)
		assertNull(threadLocal.get())
	}

	@Test
	fun `can generate human readable byte count`() {
		assertEquals("100 B", humanReadableByteCount(100))
		assertEquals("10.1 kB", humanReadableByteCount(10100))
		assertEquals("1.0 GB", humanReadableByteCount(999950000))
		assertEquals("999.9 GB", humanReadableByteCount(999850000000))
		assertEquals("999.8 TB", humanReadableByteCount(999750000000000))
		assertEquals("999.7 PB", humanReadableByteCount(999650000000000000))
		assertEquals("1.7 EB", humanReadableByteCount(1650000000000000000))
	}
}
