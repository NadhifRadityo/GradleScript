package GradleScript.FunctionalTest

import GradleScript.TestFixtures.BaseContextTest
import GradleScript.TestFixtures.FileDSLContext
import GradleScript.TestFixtures.ProjectDSL.Project
import GradleScript.TestFixtures.ProjectDSLContext
import org.junit.jupiter.api.io.TempDir
import java.io.File

abstract class BaseFunctionalTest: BaseContextTest, FileDSLContext {

}

abstract class BaseFunctionalGradleTest: BaseFunctionalTest(), ProjectDSLContext {
	@TempDir
	override lateinit var projectDir: File
	override var currentProject: Project? = null
}
