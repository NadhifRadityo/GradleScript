package GradleScript.FunctionalTest

import GradleScript.TestFixtures.Tests.BaseTest
import GradleScript.TestFixtures.FileDSLContext
import GradleScript.TestFixtures.ProjectDSLContext
import GradleScript.TestFixtures.Tests.CommonTest
import org.junit.jupiter.api.io.TempDir
import java.io.File

abstract class BaseFunctionalTest: BaseTest, CommonTest, FileDSLContext {
	override val __test_storage = mutableMapOf<Any?, Any?>()
}

abstract class BaseFunctionalGradleTest: BaseFunctionalTest(), ProjectDSLContext {

}
