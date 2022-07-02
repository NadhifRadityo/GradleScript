package GradleScript.IntegrationTest

import GradleScript.TestFixtures.BaseTest
import GradleScript.TestFixtures.FileDSLContext

abstract class BaseIntegrationTest: BaseTest, FileDSLContext {
	override val __test_storage = mutableMapOf<Any?, Any?>()
}
