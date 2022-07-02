package GradleScript.TestFixtures.Extensions

import GradleScript.Common
import org.junit.jupiter.api.extension.ExtensionContext

class CommonExtension: BaseExtension() {

	override fun construct(context: ExtensionContext) {
		Common.constructNoContext()
	}
	override fun destruct(context: ExtensionContext) {
		Common.destructNoContext()
	}
}
