package GradleScript.TestFixtures.Extensions

import GradleScript.Common

class ContextExtension: BaseExtension() {

	override fun construct() {
		Common.constructNoContext()
	}
	override fun destruct() {
		Common.destructNoContext()
	}
}
