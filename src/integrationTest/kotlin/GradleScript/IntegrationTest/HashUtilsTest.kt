package GradleScript.IntegrationTest

import GradleScript.Strategies.HashUtils.checksumExeCertutil
import GradleScript.Strategies.HashUtils.checksumExeMdNsum
import GradleScript.Strategies.HashUtils.checksumExeOpenssl
import GradleScript.Strategies.HashUtils.checksumExeShaNsum
import GradleScript.Strategies.HashUtils.checksumJavaNative
import GradleScript.Strategies.ProcessUtils.getCommandOutput
import GradleScript.Strategies.ProcessUtils.searchPath
import GradleScript.Strategies.StringUtils.randomString
import org.gradle.internal.impldep.org.junit.Assert.assertArrayEquals
import org.gradle.internal.impldep.org.junit.Assert.assertEquals
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.Security

class HashUtilsTest: BaseIntegrationTest() {

	companion object {
		// Map<Name, Pair<RegexFilter, ExpectedSizeCallback>>
		// ExpectedSizeCallback returns the number of BYTES not bits
		@JvmStatic
		val supportedHashAlgorithms = mapOf<String, Pair<Regex, (String) -> Int>>(
			"md2" to (Regex("md-?2", RegexOption.IGNORE_CASE) to { 128 / 8 }),
			"md4" to (Regex("md-?4", RegexOption.IGNORE_CASE) to { 128 / 8 }),
			"md5" to (Regex("md-?5", RegexOption.IGNORE_CASE) to { 128 / 8 }),
			"sha1" to (Regex("sha-?1", RegexOption.IGNORE_CASE) to { 160 / 8 }),
			"sha224" to (Regex("sha-?224", RegexOption.IGNORE_CASE) to { 224 / 8 }),
			"sha256" to (Regex("sha-?256", RegexOption.IGNORE_CASE) to { 256 / 8 }),
			"sha384" to (Regex("sha-?384", RegexOption.IGNORE_CASE) to { 384 / 8 }),
			"sha512" to (Regex("sha-?512", RegexOption.IGNORE_CASE) to { 512 / 8 }),
			"sha512/224" to (Regex("sha-?512[/\\-]224", RegexOption.IGNORE_CASE) to { 224 / 8 }),
			"sha512/256" to (Regex("sha-?512[/\\-]256", RegexOption.IGNORE_CASE) to { 256 / 8 }),
			"sha3-224" to (Regex("sha3-224", RegexOption.IGNORE_CASE) to { 224 / 8 }),
			"sha3-256" to (Regex("sha3-256", RegexOption.IGNORE_CASE) to { 256 / 8 }),
			"sha3-384" to (Regex("sha3-384", RegexOption.IGNORE_CASE) to { 384 / 8 }),
			"sha3-512" to (Regex("sha3-512", RegexOption.IGNORE_CASE) to { 512 / 8 }),
			"ripemd" to (Regex("ripemd", RegexOption.IGNORE_CASE) to { 128 / 8 }),
			"ripemd-128" to (Regex("ripemd-?128", RegexOption.IGNORE_CASE) to { 128 / 8 }),
			"ripemd-160" to (Regex("ripemd-?160", RegexOption.IGNORE_CASE) to { 160 / 8 }),
			"ripemd-256" to (Regex("ripemd-?256", RegexOption.IGNORE_CASE) to { 256 / 8 }),
			"ripemd-320" to (Regex("ripemd-?320", RegexOption.IGNORE_CASE) to { 320 / 8 }),
			"tiger" to (Regex("tiger", RegexOption.IGNORE_CASE) to { 192 / 8 }),
			"whirlpool" to (Regex("whirlpool", RegexOption.IGNORE_CASE) to { 512 / 8 }),
			"gost" to (Regex("gost", RegexOption.IGNORE_CASE) to { 256 / 8 }),
			"skein-256-128" to (Regex("skein-?256-128", RegexOption.IGNORE_CASE) to { 128 / 8 }),
			"skein-256-160" to (Regex("skein-?256-160", RegexOption.IGNORE_CASE) to { 160 / 8 }),
			"skein-256-224" to (Regex("skein-?256-224", RegexOption.IGNORE_CASE) to { 224 / 8 }),
			"skein-256-256" to (Regex("skein-?256-256", RegexOption.IGNORE_CASE) to { 256 / 8 }),
			"skein-512-128" to (Regex("skein-?512-128", RegexOption.IGNORE_CASE) to { 128 / 8 }),
			"skein-512-160" to (Regex("skein-?512-160", RegexOption.IGNORE_CASE) to { 160 / 8 }),
			"skein-512-224" to (Regex("skein-?512-224", RegexOption.IGNORE_CASE) to { 224 / 8 }),
			"skein-512-256" to (Regex("skein-?512-256", RegexOption.IGNORE_CASE) to { 256 / 8 }),
			"skein-512-384" to (Regex("skein-?512-384", RegexOption.IGNORE_CASE) to { 384 / 8 }),
			"skein-512-512" to (Regex("skein-?512-512", RegexOption.IGNORE_CASE) to { 512 / 8 }),
			"skein-1024-384" to (Regex("skein-?1024-384", RegexOption.IGNORE_CASE) to { 384 / 8 }),
			"skein-1024-512" to (Regex("skein-?1024-512", RegexOption.IGNORE_CASE) to { 512 / 8 }),
			"skein-1024-102" to (Regex("skein-?1024-1024", RegexOption.IGNORE_CASE) to { 102 / 84 })
		)
		@JvmStatic
		val availableHashAlgorithms = mutableMapOf<String, String>()
		init {
			val providers = Security.getProviders()
			for(provider in providers) {
				val services = provider.services
				for(service in services) {
					if(!service.type.equals(MessageDigest::class.java.simpleName))
						continue
					val algorithm = service.algorithm
					for(entry in supportedHashAlgorithms) {
						if(!entry.value.first.matches(algorithm))
							continue
						availableHashAlgorithms[entry.key] = algorithm
						break
					}
				}
				val aliases = provider.keys()
				for(alias in aliases) {
					val aliasString = alias.toString()
					val prefix = "Alg.Alias.${MessageDigest::class.java.simpleName}."
					if(!aliasString.startsWith(prefix))
						continue
					val algorithm = aliasString.substring(prefix.length)
					for(entry in supportedHashAlgorithms) {
						if(!entry.value.first.matches(algorithm))
							continue
						availableHashAlgorithms[entry.key] = algorithm
						break
					}
				}
			}
		}

		@JvmStatic
		internal var hashPrepared = false
		@JvmStatic
		lateinit var originalContents: Array<String>
		@JvmStatic
		val expectedHashResult = mutableMapOf<String, Array<ByteArray>>()
		@JvmStatic
		fun prepareJvmHash() {
			if(hashPrepared) return
			hashPrepared = true
			originalContents = (1..5).map { randomString(50) }.toTypedArray()
			for(hashAlgorithm in availableHashAlgorithms) {
				expectedHashResult[hashAlgorithm.key] = originalContents.map { content ->
					val hash = checksumJavaNative(content.toByteArray(StandardCharsets.UTF_8), hashAlgorithm.value)
					val expectedSize = supportedHashAlgorithms[hashAlgorithm.key]!!.second.invoke(content)
					assertEquals(expectedSize, hash.size)
					hash
				}.toTypedArray()
			}
		}

		@JvmStatic
		internal val certutilExecutable: File? = searchPath("certutil")
		@JvmStatic
		internal val opensslExecutable: File? = searchPath("openssl")
		@JvmStatic
		internal val md5sumExecutable: File? = searchPath("md5sum")
		@JvmStatic
		internal val sha1sumExecutable: File? = searchPath("sha1sum")
		@JvmStatic
		internal val sha224sumExecutable: File? = searchPath("sha224sum")
		@JvmStatic
		internal val sha256sumExecutable: File? = searchPath("sha256sum")
		@JvmStatic
		internal val sha384sumExecutable: File? = searchPath("sha384sum")
		@JvmStatic
		internal val sha512sumExecutable: File? = searchPath("sha512sum")

		@JvmStatic
		internal var certutilHashPrepared = false
		@JvmStatic
		val certutilAvailableHashAlgorithms = mutableMapOf<String, String>()
		@JvmStatic
		fun prepareCertutilHash() {
			if(certutilHashPrepared) return
			certutilHashPrepared = true
			if(certutilExecutable == null) return
			certutilAvailableHashAlgorithms["md2"] = "MD2"
			certutilAvailableHashAlgorithms["md4"] = "MD4"
			certutilAvailableHashAlgorithms["md5"] = "MD5"
			certutilAvailableHashAlgorithms["sha1"] = "sha1"
			certutilAvailableHashAlgorithms["sha256"] = "sha256"
			certutilAvailableHashAlgorithms["sha384"] = "sha384"
			certutilAvailableHashAlgorithms["sha512"] = "sha512"
			// certutil's help command returns non-zero return code.
//			val pattern = Regex("^Hash algorithms:\\s*(.*?)\\s*\$", setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))
//			val algorithms = pattern.matchEntire(getCommandOutput(certutilExecutable.path, "-hashfile", "-?")!!)!!.groups[1]!!.value.trim().split(Regex("\\s+"))
//			for(algorithm in algorithms) {
//				for(entry in supportedHashAlgorithms) {
//					if(!entry.value.first.matches(algorithm))
//						continue
//					certutilAvailableHashAlgorithms[entry.key] = algorithm
//					break
//				}
//			}
		}

		@JvmStatic
		internal var opensslHashPrepared = false
		@JvmStatic
		val opensslAvailableHashAlgorithms = mutableMapOf<String, String>()
		@JvmStatic
		fun prepareOpensslHash() {
			if(opensslHashPrepared) return
			opensslHashPrepared = true
			if(opensslExecutable == null) return
			val algorithms = getCommandOutput(opensslExecutable.path, "list", "-1", "-digest-commands")!!.split("\n").map { it.trim() }
			for(algorithm in algorithms) {
				for(entry in supportedHashAlgorithms) {
					if(!entry.value.first.matches(algorithm))
						continue
					opensslAvailableHashAlgorithms[entry.key] = algorithm
					break
				}
			}
		}

		@BeforeAll @JvmStatic
		fun prepareHash() {
			prepareJvmHash()
			prepareCertutilHash()
			prepareOpensslHash()
		}
	}

	@Test
	fun `can generate digest using java hash algorithm`(@TempDir testDir: File) {
		withFile(testDir) {
			for(hashAlgorithm in availableHashAlgorithms) {
				for(i in originalContents.indices) {
					val content = originalContents[i]
					val expected = expectedHashResult[hashAlgorithm.key]?.get(i)
					"hash$i" * { -content }
					val hash = checksumJavaNative(file("hash$i"), hashAlgorithm.value)
					if(expected != null) assertArrayEquals(expected, hash)
					else assertEquals(supportedHashAlgorithms[hashAlgorithm.key]!!.second.invoke(content), hash.size)
				}
			}
		}
	}
	@Test
	fun `can generate digest using certutil executable`(@TempDir testDir: File) {
		assumeTrue(certutilExecutable != null)
		withFile(testDir) {
			for(hashAlgorithm in certutilAvailableHashAlgorithms) {
				for(i in originalContents.indices) {
					val content = originalContents[i]
					val expected = expectedHashResult[hashAlgorithm.key]?.get(i)
					"hash$i" * { -content }
					val hash = checksumExeCertutil(certutilExecutable!!, file("hash$i"), hashAlgorithm.value)
					if(expected != null) assertArrayEquals(expected, hash)
					else assertEquals(supportedHashAlgorithms[hashAlgorithm.key]!!.second.invoke(content), hash.size)
				}
			}
		}
	}
	@Test
	fun `can generate digest using openssl executable`(@TempDir testDir: File) {
		assumeTrue(opensslExecutable != null)
		withFile(testDir) {
			for(hashAlgorithm in opensslAvailableHashAlgorithms) {
				for(i in originalContents.indices) {
					val content = originalContents[i]
					val expected = expectedHashResult[hashAlgorithm.key]?.get(i)
					"hash$i" * { -content }
					val hash = checksumExeOpenssl(opensslExecutable!!, file("hash$i"), hashAlgorithm.value)
					if(expected != null) assertArrayEquals(expected, hash)
					else assertEquals(supportedHashAlgorithms[hashAlgorithm.key]!!.second.invoke(content), hash.size)
				}
			}
		}
	}
	@Test
	fun `can generate digest using md5sum executable`(@TempDir testDir: File) {
		assumeTrue(md5sumExecutable != null)
		withFile(testDir) {
			for(i in originalContents.indices) {
				val content = originalContents[i]
				val expected = expectedHashResult["md5"]?.get(i)
				"hash$i" * { -content }
				val hash = checksumExeMdNsum(md5sumExecutable!!, file("hash$i"), 5)
				if(expected != null) assertArrayEquals(expected, hash)
				else assertEquals(supportedHashAlgorithms["md5"]!!.second.invoke(content), hash.size)
			}
		}
	}
	@Test
	fun `can generate digest using sha1 executable`(@TempDir testDir: File) {
		assumeTrue(sha1sumExecutable != null)
		withFile(testDir) {
			for(i in originalContents.indices) {
				val content = originalContents[i]
				val expected = expectedHashResult["sha1"]?.get(i)
				"hash$i" * { -content }
				val hash = checksumExeShaNsum(sha1sumExecutable!!, file("hash$i"), 1)
				if(expected != null) assertArrayEquals(expected, hash)
				else assertEquals(supportedHashAlgorithms["sha1"]!!.second.invoke(content), hash.size)
			}
		}
	}
	@Test
	fun `can generate digest using sha224 executable`(@TempDir testDir: File) {
		assumeTrue(sha224sumExecutable != null)
		withFile(testDir) {
			for(i in originalContents.indices) {
				val content = originalContents[i]
				val expected = expectedHashResult["sha224"]?.get(i)
				"hash$i" * { -content }
				val hash = checksumExeShaNsum(sha224sumExecutable!!, file("hash$i"), 224)
				if(expected != null) assertArrayEquals(expected, hash)
				else assertEquals(supportedHashAlgorithms["sha224"]!!.second.invoke(content), hash.size)
			}
		}
	}
	@Test
	fun `can generate digest using sha256 executable`(@TempDir testDir: File) {
		assumeTrue(sha256sumExecutable != null)
		withFile(testDir) {
			for(i in originalContents.indices) {
				val content = originalContents[i]
				val expected = expectedHashResult["sha256"]?.get(i)
				"hash$i" * { -content }
				val hash = checksumExeShaNsum(sha256sumExecutable!!, file("hash$i"), 256)
				if(expected != null) assertArrayEquals(expected, hash)
				else assertEquals(supportedHashAlgorithms["sha256"]!!.second.invoke(content), hash.size)
			}
		}
	}
	@Test
	fun `can generate digest using sha384 executable`(@TempDir testDir: File) {
		assumeTrue(sha384sumExecutable != null)
		withFile(testDir) {
			for(i in originalContents.indices) {
				val content = originalContents[i]
				val expected = expectedHashResult["sha384"]?.get(i)
				"hash$i" * { -content }
				val hash = checksumExeShaNsum(sha384sumExecutable!!, file("hash$i"), 384)
				if(expected != null) assertArrayEquals(expected, hash)
				else assertEquals(supportedHashAlgorithms["sha384"]!!.second.invoke(content), hash.size)
			}
		}
	}
	@Test
	fun `can generate digest using sha512 executable`(@TempDir testDir: File) {
		assumeTrue(sha512sumExecutable != null)
		withFile(testDir) {
			for(i in originalContents.indices) {
				val content = originalContents[i]
				val expected = expectedHashResult["sha512"]?.get(i)
				"hash$i" * { -content }
				val hash = checksumExeShaNsum(sha512sumExecutable!!, file("hash$i"), 512)
				if(expected != null) assertArrayEquals(expected, hash)
				else assertEquals(supportedHashAlgorithms["sha512"]!!.second.invoke(content), hash.size)
			}
		}
	}
}
