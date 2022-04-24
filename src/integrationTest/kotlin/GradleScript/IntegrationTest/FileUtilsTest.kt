package GradleScript.IntegrationTest

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class FileUtilsTest: BaseIntegrationTest() {
	@TempDir
	lateinit var testDir: File

	@Test
	fun `can make, write, read, and delete file`() {
		withFile(testDir) {
			"test" * {
				mkfile()

			}
		}
	}
}
