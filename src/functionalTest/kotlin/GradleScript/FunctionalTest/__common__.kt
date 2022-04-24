package GradleScript.FunctionalTest

import GradleScript.TestFixtures.BaseContextTest
import GradleScript.TestFixtures.FileDSLContext
import GradleScript.TestFixtures.Project
import GradleScript.TestFixtures.ProjectDSLContext
import org.junit.jupiter.api.io.TempDir
import java.io.File

abstract class BaseFunctionalTest: BaseContextTest, FileDSLContext {

}

abstract class BaseFunctionalGradleTest: BaseFunctionalTest(), ProjectDSLContext {
	@TempDir
	internal lateinit var _projectDir: File
	internal var _currentProject: Project? = null

	override var projectDir = _projectDir
	override var currentProject = _currentProject
}
