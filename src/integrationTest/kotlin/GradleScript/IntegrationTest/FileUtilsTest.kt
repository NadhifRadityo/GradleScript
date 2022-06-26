package GradleScript.IntegrationTest

import GradleScript.Strategies.FileUtils
import GradleScript.TestFixtures.FileDSL.FileNodeFileDSL
import GradleScript.TestFixtures.FileDSL.FileNodeDirectoryDSL
import GradleScript.TestFixtures.FileDSL.FileNodeGenericDSL
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.IOException

class FileUtilsTest: BaseIntegrationTest() {

	/**
	 * Note:
	 * Use `file("test").test {...}` instead of `"test" { ... }` or `"test" * {...}` or `"test" / {...}`
	 * to bypass [FileUtils.mkfile] or [FileUtils.mkdir], see [FileNodeFileDSL.not], [FileNodeDirectoryDSL.not], [FileNodeGenericDSL.not]
	 */

	@Test
	fun `can make, write, read, and delete file`(@TempDir testDir: File) {
		val string = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		withFile(testDir) {
			file("test").files {
				mkfile()
				-string
				assertEquals(string, readString())
				delfile()
			}
		}
	}
	@Test
	fun `cannot make file within a file`(@TempDir testDir: File) {
		withFile(testDir) {
			file("test").files {
				mkfile()
				assertThrows<IOException> {
					!"test"
				}
			}
		}
	}
	@Test
	fun `can make file in uncreated directory`(@TempDir testDir: File) {
		withFile(testDir) {
			file("test").files {
				!"test"
			}
		}
	}
	@Test
	fun `can delete file recursively`(@TempDir testDir: File) {
		withFile(testDir) {
			"test" / {
				"test2" / {
					"test3" / {
						"testFile" * {
							-"test"
						}
					}
					"test4" / { }
				}
				"testFile2" * {
					-"aaa"
				}
				"testDir" / {
					"testF" * { }
				}
			}
			delfile("test")
		}
	}
	@Test
	fun `cannot read or write file content to directory`(@TempDir testDir: File) {
		withFile(testDir) {
			file("test").files {
				mkdir()
				assertThrows<IOException> {
					-"AAAA"
				}
				assertThrows<IOException> {
					readString()
				}
			}
		}
	}
}
