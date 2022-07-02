package GradleScript.IntegrationTest

import GradleScript.TestFixtures.Tests.BaseTest
import GradleScript.TestFixtures.FileDSLContext
import GradleScript.TestFixtures.Tests.CommonTest

abstract class BaseIntegrationTest: BaseTest, CommonTest, FileDSLContext {
	override val __test_storage = mutableMapOf<Any?, Any?>()
}
