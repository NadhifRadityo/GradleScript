package GradleScript.FunctionalTest

import GradleScript.TestFixtures.BaseTest
import GradleScript.TestFixtures.FileDSLContext
import GradleScript.TestFixtures.ProjectDSLContext
import org.junit.jupiter.api.io.TempDir
import java.io.File

abstract class BaseFunctionalTest: BaseTest, FileDSLContext {
	override val __test_storage = mutableMapOf<Any?, Any?>()
}

abstract class BaseFunctionalGradleTest: BaseFunctionalTest(), ProjectDSLContext {
	@TempDir
	override lateinit var projectDir: File
}
