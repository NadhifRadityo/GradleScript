package GradleScript.TestFixtures

import GradleScript.Common.constructNoContext
import GradleScript.Common.destructNoContext
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll

interface BaseContextTest {
	companion object {
		private var nestedCount = 0;

		@BeforeAll @JvmStatic
		fun beforeAll() {
			if(nestedCount == 0)
				constructNoContext()
			nestedCount++
		}

		@AfterAll @JvmStatic
		fun afterAll() {
			nestedCount--
			if(nestedCount == 0)
				destructNoContext()
		}
	}
}
