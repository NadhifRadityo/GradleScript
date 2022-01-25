package GradleScript.TestFixtures

import GradleScript.Common.constructNoContext
import GradleScript.Common.destructNoContext
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

interface BaseContextTest {
	companion object {
		@BeforeAll @JvmStatic
		fun beforeAll() {
			constructNoContext()
		}

		@AfterAll @JvmStatic
		fun afterAll() {
			destructNoContext()
		}
	}
}
